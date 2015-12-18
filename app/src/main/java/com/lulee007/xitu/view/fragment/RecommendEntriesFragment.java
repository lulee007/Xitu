package com.lulee007.xitu.view.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulee007.xitu.EntryWebPageActivity;
import com.lulee007.xitu.adapter.EntryListItemAdapter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.presenter.RecommendedEntriesPresenter;
import com.lulee007.xitu.view.IEntriesView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendEntriesFragment extends BaseListFragment<Entry> implements IEntriesView {



    public RecommendEntriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = super.onCreateView(inflater,container,savedInstanceState);
        mUltimateRecyclerView.addItemDividerDecoration(this.getContext());

        mListAdapter = new EntryListItemAdapter();
        mListAdapter.setItemListener(this);
        mUltimateRecyclerView.setAdapter(mListAdapter);

        mPresenter = new RecommendedEntriesPresenter(this);
        mPresenter.loadNew();

        return view;
    }


    @Override
    public void onItemClick(Object item) {
        Intent intent = EntryWebPageActivity.buildEntryWebPageParams(
                getContext(),
                ((Entry) item).getUrl(),
                ((Entry) item).getUser().getUsername(),
                ((Entry) item).getUser().getAvatar_large()
        );
        startActivity(intent);
    }
}
