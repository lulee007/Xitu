package com.lulee007.xitu.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lulee007.xitu.EntriesByTagActivity;
import com.lulee007.xitu.adapter.TagFollowAdapter;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.presenter.TagWithUserStatusPresenter;
import com.lulee007.xitu.view.ITagWithUserStatsView;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

/**
 * User: lulee007@live.com
 * Date: 2015-12-19
 * Time: 21:48
 */
public class TagWithUserStatusFragment extends BaseListFragment<Tag> implements ITagWithUserStatsView, TagFollowAdapter.TagItemListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mListAdapter = new TagFollowAdapter();
        mListAdapter.setItemListener(this);
        ((TagFollowAdapter)mListAdapter).setTagItemListener(this);

        mUltimateRecyclerView.setAdapter(mListAdapter);
        mUltimateRecyclerView.addItemDividerDecoration(this.getContext());
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mListAdapter);
        mUltimateRecyclerView.addItemDecoration(headersDecor);
        mUltimateRecyclerView.enableDefaultSwipeRefresh(false);
        mPresenter = new TagWithUserStatusPresenter(this);
        mPresenter.loadNew();

        return view;
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
     * @param tag
     * @param position
     */
    @Override
    public void onFollowClick(Tag tag, int position) {
        ((TagWithUserStatusPresenter)mPresenter).subscribeTag(tag.getSubscribedId(),position);

    }

    /**
     * TagItemListener
     * @param tag
     * @param position
     */
    @Override
    public void onUnSubscribeClick(Tag tag, int position) {
        ((TagWithUserStatusPresenter)mPresenter).unSubscribeTag(tag.getSubscribedId(),position);

    }


    /**
     * I_VIEW
     */

    @Override
    public void onUnSubscribeTagError() {
        Toast.makeText(getContext(), "取消关注出错了。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUnSubscribeTag(int position) {
        ((TagFollowAdapter)mListAdapter).onSubscribeDataChanged(position,false);
    }

    @Override
    public void onSubscribeTagError() {
        Toast.makeText(getContext(), "关注出错了。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubscribeTag(int position) {
        ((TagFollowAdapter)mListAdapter).onSubscribeDataChanged(position,true);
    }
}
