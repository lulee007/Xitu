package com.lulee007.xitu.view.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulee007.xitu.R;
import com.lulee007.xitu.adapter.EntryAdapter;
import com.lulee007.xitu.base.XTBaseFragment;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.presenter.EntriesPresenter;
import com.lulee007.xitu.util.DataStateViewHelper;
import com.lulee007.xitu.view.IEntriesView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntriesFragment extends XTBaseFragment implements IEntriesView ,DataStateViewHelper.DataStateViewListener{

    private UltimateRecyclerView ultimateRecyclerView;
    private EntryAdapter entryAdapter;
    private DataStateViewHelper dataStateViewHelper;
    private EntriesPresenter entriesPresenter;
    public EntriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_entries, container, false);
        ultimateRecyclerView= (UltimateRecyclerView) view.findViewById(R.id.rv_entries);
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        ultimateRecyclerView.addItemDividerDecoration(this.getContext());
        ultimateRecyclerView.setHasFixedSize(false);

        entryAdapter=new EntryAdapter();
        ultimateRecyclerView.setAdapter(entryAdapter);

        dataStateViewHelper=new DataStateViewHelper(ultimateRecyclerView);
        dataStateViewHelper.setDataStateViewListener(this);
        dataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING);

        entriesPresenter=new EntriesPresenter(this);
        entriesPresenter.loadNew();

        return view;
    }



    @Override
    public void refresh(List<Entry> entries) {

    }

    @Override
    public void refreshNoContent() {

    }

    @Override
    public void addMore(List<Entry> moreTags) {

    }

    @Override
    public void addNew(List<Entry> newTags) {
        entryAdapter.init(newTags);
        entryAdapter.notifyDataSetChanged();
        dataStateViewHelper.setView(DataStateViewHelper.DateState.CONTENT);
    }

    @Override
    public void addNewError() {

    }

    @Override
    public void addMoreError() {

    }

    @Override
    public void noMore() {

    }

    @Override
    public void noData() {

    }

    @Override
    public void onErrorRetry() {

    }

    @Override
    public void onLoadMoreErrorRetry() {

    }
}
