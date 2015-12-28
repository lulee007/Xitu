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
import com.lulee007.xitu.presenter.SubscribedTagsPresenter;
import com.lulee007.xitu.view.ITagFollowGuideView;

/**
 * User: lulee007@live.com
 * Date: 2015-12-19
 * Time: 21:46
 */
public class SubscribedTagsFragment extends BaseListFragment<Tag> implements ITagFollowGuideView, TagFollowAdapter.TagItemListener {

    private static  final String BUNDLE_KEY_AUTHOR_ID="author_id";
    private static final String BUNDLE_KEY_REFRESH_FLAG = "refresh_flag";
    private static final String BUNDLE_KEY_NOT_SHOW_SUBSCRIBE_FLAG = "not_show_subscribe_flag";

    private  boolean refreshFlag=false;
    private  boolean notShowSubscribeFlag=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mListAdapter = new TagFollowAdapter();
        mListAdapter.setItemListener(this);
        ((TagFollowAdapter)mListAdapter).setTagItemListener(this);

        mUltimateRecyclerView.setAdapter(mListAdapter);
        mUltimateRecyclerView.addItemDividerDecoration(this.getContext());
        mUltimateRecyclerView.enableDefaultSwipeRefresh(false);

        mPresenter = new SubscribedTagsPresenter(this);

        Bundle args=getArguments();
        String authorId=null;
        if(args!=null){
            authorId=args.getString(BUNDLE_KEY_AUTHOR_ID);
            refreshFlag=args.getBoolean(BUNDLE_KEY_REFRESH_FLAG);
            notShowSubscribeFlag=args.getBoolean(BUNDLE_KEY_NOT_SHOW_SUBSCRIBE_FLAG);
        }
        if(authorId!=null)
            ((SubscribedTagsPresenter)mPresenter).setUserId(authorId);
        if(notShowSubscribeFlag)
            ((TagFollowAdapter)mListAdapter).hideSubscribeBtn();
        mPresenter.loadNew();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //第一次不进此，在oncreate view中进行第一次，这里相当于切换页面刷新
        if(getUserVisibleHint() && !refreshFlag){
            if(mPresenter!=null){
                mPresenter.loadNew();
            }
        }
    }

    public  static  SubscribedTagsFragment newInstanceForAuthor(String authorId){
        Bundle args=new Bundle();
        args.putString(BUNDLE_KEY_AUTHOR_ID, authorId);
        args.putBoolean(BUNDLE_KEY_REFRESH_FLAG, true);
        args.putBoolean(BUNDLE_KEY_NOT_SHOW_SUBSCRIBE_FLAG, true);
        SubscribedTagsFragment subscribedTagsFragment=new SubscribedTagsFragment();
        subscribedTagsFragment.setArguments(args);
        return subscribedTagsFragment;
    }

    @Override
    public void showConfirm() {

    }

    @Override
    public void onUnSubscribeTag(int position) {
        ((TagFollowAdapter) mListAdapter).remove(position);
    }

    @Override
    public void onUnSubscribeTagError() {
        Toast.makeText(getContext(), "关注失败。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubscribeTagError() {

    }

    @Override
    public void onSubscribeTag(String objectId, int position) {

    }

    @Override
    public void onItemClick(Object item) {
        Tag tag = (Tag) item;
        Intent intent = new Intent(getContext(), EntriesByTagActivity.class);
        intent.putExtra(EntriesByTagActivity.BUNDLE_KEY_TAG_TITLE,
                tag.getTitle());
        startActivity(intent);
    }

    @Override
    public void onFollowClick(Tag tag, int position) {
    }

    @Override
    public void onUnSubscribeClick(Tag tag, int position) {
        ((SubscribedTagsPresenter) mPresenter).unSubscribeTag(tag.getSubscribedId(), position);

    }
}
