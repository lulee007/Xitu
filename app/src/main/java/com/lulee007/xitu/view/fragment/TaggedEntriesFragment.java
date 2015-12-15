package com.lulee007.xitu.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulee007.xitu.adapter.EntryCardItemAdapter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.presenter.TaggedEntriesPresenter;
import com.lulee007.xitu.view.IEntriesView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaggedEntriesFragment extends BaseListFragment<Entry> implements IEntriesView {


    public TaggedEntriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = super.onCreateView(inflater, container, savedInstanceState);
        mListAdapter = new EntryCardItemAdapter();
        mUltimateRecyclerView.setAdapter(mListAdapter);
        mUltimateRecyclerView.addItemDividerDecoration(this.getContext());
        mPresenter = new TaggedEntriesPresenter(this);
        mPresenter.loadNew();

        return view;
    }


    @Override
    public void noData() {
        super.noData();
    }
}
