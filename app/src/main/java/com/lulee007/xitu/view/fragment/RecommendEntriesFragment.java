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
public class RecommendEntriesFragment extends XTBaseFragment implements IEntriesView, DataStateViewHelper.DataStateViewListener {

    private UltimateRecyclerView ultimateRecyclerView;
    private EntryListItemAdapter entryListItemAdapter;
    private DataStateViewHelper dataStateViewHelper;
    private RecommendedEntriesPresenter recommendedEntriesPresenter;

    public RecommendEntriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entries, container, false);
        ultimateRecyclerView = (UltimateRecyclerView) view.findViewById(R.id.rv_entries);
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        ultimateRecyclerView.addItemDividerDecoration(this.getContext());
        ultimateRecyclerView.setHasFixedSize(false);

        entryListItemAdapter = new EntryListItemAdapter();
        ultimateRecyclerView.setAdapter(entryListItemAdapter);
        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recommendedEntriesPresenter.refresh();
            }
        });

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                recommendedEntriesPresenter.loadMore();
            }
        });

        dataStateViewHelper = new DataStateViewHelper(ultimateRecyclerView);
        dataStateViewHelper.setDataStateViewListener(this);
        dataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING);

        recommendedEntriesPresenter = new RecommendedEntriesPresenter(this);
        recommendedEntriesPresenter.loadNew();

        return view;
    }


    @Override
    public void refresh(List<Entry> entries) {
        ultimateRecyclerView.setRefreshing(false);

        entryListItemAdapter.insert(entries, Entry.class, entries.size());
    }

    @Override
    public void refreshNoContent() {
        ultimateRecyclerView.setRefreshing(false);
        Toast.makeText(getContext(), "没啦，干货正在准备中呢！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshError() {
        ultimateRecyclerView.setRefreshing(false);
        Toast.makeText(getContext(), "服务器开小差了！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void addMore(List<Entry> moreItems) {
        entryListItemAdapter.addMore(moreItems);
    }

    @Override
    public void addNew(List<Entry> newItems) {
        entryListItemAdapter.init(newItems);
        dataStateViewHelper.setView(DataStateViewHelper.DateState.CONTENT);
        View view=LayoutInflater.from(getContext()).inflate(R.layout.loading_data_progressbar,null);
        ultimateRecyclerView.reenableLoadmore(view);
        dataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING_MORE);
    }

    @Override
    public void addNewError() {
        dataStateViewHelper.setView(DataStateViewHelper.DateState.ERROR);
    }

    @Override
    public void addMoreError() {
        dataStateViewHelper.setView(DataStateViewHelper.DateState.LOAD_MORE_ERROR);
    }

    @Override
    public void noMore() {
        ultimateRecyclerView.disableLoadmore();
    }

    @Override
    public void noData() {
        dataStateViewHelper.setView(DataStateViewHelper.DateState.NO_DATA);
    }

    @Override
    public void onErrorRetry() {
        dataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING);
        recommendedEntriesPresenter.loadNew();
    }

    @Override
    public void onLoadMoreErrorRetry() {
        dataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING_MORE);
        recommendedEntriesPresenter.loadMore();
    }
}
