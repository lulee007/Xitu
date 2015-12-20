package com.lulee007.xitu.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class TagWithUserStatusFragment extends BaseListFragment<Tag> implements ITagWithUserStatsView{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =super.onCreateView(inflater, container, savedInstanceState);

        mListAdapter=new TagFollowAdapter();
        mListAdapter.setItemListener(this);

        mUltimateRecyclerView.setAdapter(mListAdapter);
        mUltimateRecyclerView.addItemDividerDecoration(this.getContext());
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mListAdapter);
        mUltimateRecyclerView.addItemDecoration(headersDecor);
        mUltimateRecyclerView.enableDefaultSwipeRefresh(false);
        mPresenter=new TagWithUserStatusPresenter(this);
        mPresenter.loadNew();

        return  view;
    }
}
