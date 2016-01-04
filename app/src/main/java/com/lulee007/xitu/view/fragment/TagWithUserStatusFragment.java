package com.lulee007.xitu.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.lulee007.xitu.EntriesByTagActivity;
import com.lulee007.xitu.R;
import com.lulee007.xitu.adapter.TagFollowAdapter;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.presenter.TagWithUserStatusPresenter;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.util.XTConstant;
import com.lulee007.xitu.view.ITagWithUserStatsView;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-19
 * Time: 21:48
 */
public class TagWithUserStatusFragment extends BaseListFragment<Tag> implements ITagWithUserStatsView, TagFollowAdapter.TagItemListener {

    private boolean showHeader = false;
    private boolean showConfirm = false;
    public static final String BUNDLE_KEY_SHOW_HEADER = "show_header";
    private FloatingActionButton subscribeDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        containerId = R.layout.fragment_tags_container;
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            showHeader = arguments.getBoolean(BUNDLE_KEY_SHOW_HEADER);
        }

        mListAdapter = new TagFollowAdapter();
        mListAdapter.setItemListener(this);
        ((TagFollowAdapter) mListAdapter).setTagItemListener(this);

        mUltimateRecyclerView.setAdapter(mListAdapter);
        mUltimateRecyclerView.addItemDividerDecoration(getContext());
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mListAdapter);
        mUltimateRecyclerView.addItemDecoration(headersDecor);
        mUltimateRecyclerView.enableDefaultSwipeRefresh(false);
        if (showHeader) {
            showConfirm = true;
            View header = getActivity().getLayoutInflater().inflate(R.layout.tag_follow_view_header, mUltimateRecyclerView.mRecyclerView, false);
            mUltimateRecyclerView.setNormalHeader(header);

        }
        subscribeDone = (FloatingActionButton) view.findViewById(R.id.subscribe_done);
        if (subscribeDone != null && showConfirm) {
            RxView.clicks(subscribeDone).throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        }
                    });
            subscribeDone.setVisibility(View.VISIBLE);
        }

        mPresenter = new TagWithUserStatusPresenter(this);
        mPresenter.loadNew();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //第一次不进此，在oncreate view中进行第一次，这里相当于切换页面刷新
        if (getUserVisibleHint()) {
            if (mPresenter != null) {
                mPresenter.loadNew();
            }
        }
    }

    @Override
    public void onItemClick(Object item) {
        Tag tag = (Tag) item;
        Intent intent = new Intent(getContext(), EntriesByTagActivity.class);
        intent.putExtra(EntriesByTagActivity.BUNDLE_KEY_TAG_TITLE,
                tag.getTitle());
        startActivity(intent);
    }


    /**
     * TagItemListener
     *
     * @param tag
     * @param position
     */
    @Override
    public void onFollowClick(Tag tag, int position) {
        ((TagWithUserStatusPresenter) mPresenter).subscribeTag(tag.getObjectId(), position);

    }

    /**
     * TagItemListener
     *
     * @param tag
     * @param position
     */
    @Override
    public void onUnSubscribeClick(Tag tag, int position) {
        ((TagWithUserStatusPresenter) mPresenter).unSubscribeTag(tag.getSubscribedId(), position);

    }

    /**
     * I_VIEW
     */

    @Override
    public void showConfirm() {

    }


    @Override
    public void onUnSubscribeTagError() {
        Toast.makeText(getContext(), "取消关注出错了。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUnSubscribeTag(int position) {
        ((TagFollowAdapter) mListAdapter).onSubscribeDataChanged(null, position, false);
    }

    @Override
    public void onSubscribeTagError() {
        Toast.makeText(getContext(), "关注出错了。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubscribeTag(String objectId, int position) {
        ((TagFollowAdapter) mListAdapter).onSubscribeDataChanged(objectId, position, true);
    }

    @Override
    public void showNeedLoginDialog() {
        AuthUserHelper.getInstance().showNeedLoginDialog(getActivity());
    }

    public void notifyUserLoggedIn() {
        mPresenter.loadNew();
    }
}
