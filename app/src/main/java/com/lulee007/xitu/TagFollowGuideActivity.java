package com.lulee007.xitu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.lulee007.xitu.adapter.TagFollowAdapter;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.presenter.TagFollowGuidePresenter;
import com.lulee007.xitu.util.DataStateViewHelper;
import com.lulee007.xitu.view.ITagFollowGuideView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class TagFollowGuideActivity extends XTBaseActivity implements ITagFollowGuideView, DataStateViewHelper.DataStateViewListener ,TagFollowAdapter.ItemListener{

    private UltimateRecyclerView ultimateRecyclerView;
    private TagFollowAdapter tagFollowAdapter;
    private TagFollowGuidePresenter tagFollowGuidePresenter;
    private DataStateViewHelper dataStateViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_follow_guide);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("关注标签");
        }

        ultimateRecyclerView = (UltimateRecyclerView) findViewById(R.id.rv_follow_tags);
        ultimateRecyclerView.setHasFixedSize(false);
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ultimateRecyclerView.addItemDividerDecoration(this);

        tagFollowAdapter = new TagFollowAdapter();
        tagFollowAdapter.setItemListener(this);
        ultimateRecyclerView.setAdapter(tagFollowAdapter);

        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(tagFollowAdapter);
        ultimateRecyclerView.addItemDecoration(headersDecor);

        View header = getLayoutInflater().inflate(R.layout.tag_follow_view_header, ultimateRecyclerView.mRecyclerView, false);
        ultimateRecyclerView.setNormalHeader(header);

        tagFollowAdapter.setCustomLoadMoreView(LayoutInflater.from(this).inflate(R.layout.custom_bottom_progressbar, null));
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                tagFollowGuidePresenter.loadMore();
            }
        });


        dataStateViewHelper = new DataStateViewHelper(ultimateRecyclerView);
        dataStateViewHelper.setDataStateViewListener(this);
        dataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING);
        tagFollowGuidePresenter = new TagFollowGuidePresenter(this);
        tagFollowGuidePresenter.loadNew();
        Logger.d("在TagFollow页", "OnCreate结束");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem skip = menu.add(0, 1, 0, "跳过");
        skip.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                finish();
        }
        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tagFollowGuidePresenter.unSubscribeAll();
        tagFollowGuidePresenter = null;
    }

    @Override
    public void addMore(List<Tag> moreItems) {
        tagFollowAdapter.addMore(moreItems);
    }

    @Override
    public void addNew(List<Tag> newItems) {
        dataStateViewHelper.setView(DataStateViewHelper.DateState.CONTENT);
        tagFollowAdapter.init(newItems);
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
        tagFollowGuidePresenter.loadMore();
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
        tagFollowGuidePresenter.loadNew();
    }


    @Override
    public void onFollowClick(Tag tag) {
        Logger.json(new Gson().toJson(tag));
    }
}
