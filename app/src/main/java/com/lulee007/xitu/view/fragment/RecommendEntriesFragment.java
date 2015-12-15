package com.lulee007.xitu.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lulee007.xitu.R;
import com.lulee007.xitu.adapter.EntryListItemAdapter;
import com.lulee007.xitu.base.XTBaseFragment;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.presenter.RecommendedEntriesPresenter;
import com.lulee007.xitu.util.DataStateViewHelper;
import com.lulee007.xitu.view.IEntriesView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

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
        mUltimateRecyclerView.setAdapter(mListAdapter);



        mPresenter = new RecommendedEntriesPresenter(this);
        mPresenter.loadNew();

        return view;
    }

}
