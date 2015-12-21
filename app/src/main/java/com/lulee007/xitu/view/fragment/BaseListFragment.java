package com.lulee007.xitu.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lulee007.xitu.R;
import com.lulee007.xitu.base.IXTBaseView;
import com.lulee007.xitu.base.XTBaseAdapter;
import com.lulee007.xitu.base.XTBaseFragment;
import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.util.DataStateViewHelper;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

/**
 * User: lulee007@live.com
 * Date: 2015-12-15
 * Time: 17:42
 */
public abstract class BaseListFragment<T> extends XTBaseFragment implements IXTBaseView<T>, DataStateViewHelper.DataStateViewListener ,XTBaseAdapter.ItemListener{

    protected UltimateRecyclerView mUltimateRecyclerView;
    protected XTBaseAdapter mListAdapter;
    protected DataStateViewHelper mDataStateViewHelper;
    protected XTBasePresenter mPresenter;
    protected int containerId=0;

    public BaseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(containerId==0?R.layout.fragment_list_container :containerId, container, false);
        mUltimateRecyclerView = (UltimateRecyclerView) view.findViewById(R.id.rv_entries);
        mUltimateRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        mUltimateRecyclerView.setHasFixedSize(false);
        mUltimateRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mUltimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });

        mUltimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                mPresenter.loadMore();
            }
        });

        mDataStateViewHelper = new DataStateViewHelper(mUltimateRecyclerView);
        mDataStateViewHelper.setDataStateViewListener(this);
        mDataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribeAll();
        mPresenter=null;
    }

    @Override
    public void refresh(List<T> entries) {
        mUltimateRecyclerView.setRefreshing(false);

        mListAdapter.insert(entries);
    }

    @Override
    public void refreshNoContent() {
        mUltimateRecyclerView.setRefreshing(false);
        Toast.makeText(getContext(), "没啦，干货正在准备中呢！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshError() {
        mUltimateRecyclerView.setRefreshing(false);
        Toast.makeText(getContext(), "服务器开小差了！", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void addMore(List<T> moreItems) {
        mListAdapter.addMore(moreItems);
    }

    @Override
    public void addNew(List<T> newItems) {
        mListAdapter.init(newItems);
        mDataStateViewHelper.setView(DataStateViewHelper.DateState.CONTENT);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.loading_data_progressbar, null);
        mUltimateRecyclerView.reenableLoadmore(view);
        mDataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING_MORE);
    }

    @Override
    public void addNewError() {
        mDataStateViewHelper.setView(DataStateViewHelper.DateState.ERROR);
    }

    @Override
    public void addMoreError() {
        mDataStateViewHelper.setView(DataStateViewHelper.DateState.LOAD_MORE_ERROR);
    }

    @Override
    public void noMore() {
        mUltimateRecyclerView.disableLoadmore();
    }

    @Override
    public void noData() {
        mDataStateViewHelper.setView(DataStateViewHelper.DateState.NO_DATA);
    }

    @Override
    public void onErrorRetry() {
        mDataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING);
        mPresenter.loadNew();
    }

    @Override
    public void onLoadMoreErrorRetry() {
        mDataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING_MORE);
        mPresenter.loadMore();
    }

    @Override
    public void onNoDataButtonClick() {

    }

    @Override
    public void onItemClick(Object item) {

    }
}
