package com.lulee007.xitu.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulee007.xitu.adapter.TagFollowAdapter;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.presenter.SubscribedTagsPresenter;
import com.lulee007.xitu.view.ITagFollowGuideView;
import com.lulee007.xitu.view.ITagWithUserStatsView;

/**
 * User: lulee007@live.com
 * Date: 2015-12-19
 * Time: 21:46
 */
public class SubscribedTagsFragment extends BaseListFragment<Tag> implements ITagFollowGuideView {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);

        mListAdapter=new TagFollowAdapter();
        mListAdapter.setItemListener(this);

        mUltimateRecyclerView.setAdapter(mListAdapter);
        mUltimateRecyclerView.addItemDividerDecoration(this.getContext());
        mUltimateRecyclerView.enableDefaultSwipeRefresh(false);

        mPresenter=new SubscribedTagsPresenter(this);
        mPresenter.loadNew();

        return view;
    }

    @Override
    public void showConfirm() {

    }
}
