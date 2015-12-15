package com.lulee007.xitu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.lulee007.xitu.adapter.AuthorAdapter;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.models.Author;
import com.lulee007.xitu.presenter.AuthorsPresenter;
import com.lulee007.xitu.presenter.TagFollowGuidePresenter;
import com.lulee007.xitu.util.DataStateViewHelper;
import com.lulee007.xitu.view.IAuthorsView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.List;

public class AuthorsActivity extends XTBaseActivity implements IAuthorsView, DataStateViewHelper.DataStateViewListener ,AuthorAdapter.ItemListener{

    private UltimateRecyclerView ultimateRecyclerView;
    private AuthorAdapter authorAdapter;
    private AuthorsPresenter authorsPresenter;
    private DataStateViewHelper dataStateViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("掘金者");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.rv_authors);
        ultimateRecyclerView.setHasFixedSize(false);
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ultimateRecyclerView.addItemDividerDecoration(this);

        authorAdapter = new AuthorAdapter();
        authorAdapter.setItemListener(this);
        ultimateRecyclerView.setAdapter(authorAdapter);



        authorAdapter.setCustomLoadMoreView(LayoutInflater.from(this).inflate(R.layout.custom_bottom_progressbar, null));
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                authorsPresenter.loadMore();
            }
        });


        dataStateViewHelper = new DataStateViewHelper(ultimateRecyclerView);
        dataStateViewHelper.setDataStateViewListener(this);
        dataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING);
        authorsPresenter = new AuthorsPresenter(this);
        authorsPresenter.loadNew();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authorsPresenter.unSubscribeAll();
        authorsPresenter = null;
    }

    @Override
    public void addMore(List<Author> moreItems) {
        authorAdapter.addMore(moreItems);

    }

    @Override
    public void addNew(List<Author> newItems) {
        dataStateViewHelper.setView(DataStateViewHelper.DateState.CONTENT);
        authorAdapter.init(newItems);
        ultimateRecyclerView.enableLoadmore();
    }



    @Override
    public void addNewError() {
        dataStateViewHelper.setView(DataStateViewHelper.DateState.ERROR);
    }

    @Override
    public void addMoreError() {

        dataStateViewHelper.setView(DataStateViewHelper.DateState.LOAD_MORE_ERROR);
        //TODO 目前ultimateRecyclerView disableloadmore 有问题，只能退出重进
//        showToast("服务器出错了。");
    }

    @Override
    public void onLoadMoreErrorRetry() {
        dataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING_MORE);
        authorsPresenter.loadMore();
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
        authorsPresenter.loadNew();
    }


    @Override
    public void onFollowClick(Object item) {

    }
}
