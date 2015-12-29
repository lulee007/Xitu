package com.lulee007.xitu.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lulee007.xitu.AuthorHomeActivity;
import com.lulee007.xitu.EntriesByTagActivity;
import com.lulee007.xitu.EntryWebPageActivity;
import com.lulee007.xitu.R;
import com.lulee007.xitu.TagFollowGuideActivity;
import com.lulee007.xitu.adapter.EntryCardItemAdapter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.presenter.TaggedEntriesPresenter;
import com.lulee007.xitu.util.DataStateViewHelper;
import com.lulee007.xitu.view.IEntriesView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaggedCardEntriesFragment extends BaseListFragment<Entry> implements IEntriesView, EntryCardItemAdapter.EntryCardItemListener {

    private static final int ON_TAG_FOLLOW_ACTIVITY_REQUEST = 1;

    public TaggedCardEntriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = super.onCreateView(inflater, container, savedInstanceState);
        mListAdapter = new EntryCardItemAdapter();
        mListAdapter.setItemListener(this);
        ((EntryCardItemAdapter) mListAdapter).setEntryCardItemListener(this);
        mUltimateRecyclerView.setAdapter(mListAdapter);
        mPresenter = new TaggedEntriesPresenter(this);
        mPresenter.loadNew();
        mDataStateViewHelper.setNoDataViewRsid(R.layout.no_entries);
        mDataStateViewHelper.setNoDataBtnId(R.id.add_tag_btn);
        return view;
    }


    @Override
    public void onNoDataButtonClick() {
        startActivityForResult(new Intent(getContext(), TagFollowGuideActivity.class), ON_TAG_FOLLOW_ACTIVITY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ON_TAG_FOLLOW_ACTIVITY_REQUEST) {
            mDataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING);
            mPresenter.refresh();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onItemClick(Object item) {
        Intent intent = EntryWebPageActivity.buildEntryWebPageParams(
                getContext(),
                ((Entry) item).getUrl(),
                ((Entry) item).getUser().getUsername(),
                ((Entry) item).getUser().getAvatar_large(),
                ((Entry) item).getUser().getObjectId(),
                ((Entry) item).getCommentsCount(),
                ((Entry) item).getCollectionCount()

        );
        startActivity(intent);
    }

    @Override
    public void onAuthorIconClick(Entry entry, ImageView icon) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                // The view which starts the transition
                icon,
                // The transitionName of the view we’re transitioning to
                getString(R.string.transition_name_author_icon)
        );
        Intent intent = AuthorHomeActivity.buildIntent(getContext(), entry.getUser().getAvatar_large(), entry.getUser().getObjectId());
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    @Override
    public void onTagClick(Entry entry) {
        Intent intent = new Intent(getContext(), EntriesByTagActivity.class);
        intent.putExtra(EntriesByTagActivity.BUNDLE_KEY_TAG_TITLE,
                entry.getTagsTitleArray().get(0));
        startActivity(intent);
    }

    @Override
    public void onToCollectClick(Entry entry, int position) {
        ((TaggedEntriesPresenter) mPresenter).onCollectViewClick(entry, position);
    }

    @Override
    public void onCollected(int position) {
        showToast("已收藏");
        ((EntryCardItemAdapter)mListAdapter).notifyItemChanged(position);
    }

    @Override
    public void onCollectError() {
        showToast("收藏时发生了错误");
    }

    @Override
    public void onUnCollect(int position) {
        showToast("取消了收藏");
        ((EntryCardItemAdapter)mListAdapter).notifyItemChanged(position);
    }

    @Override
    public void onUnCollectError() {
        showToast("取消收藏出错了");
    }
}
