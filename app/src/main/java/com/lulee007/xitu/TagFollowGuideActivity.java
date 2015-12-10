package com.lulee007.xitu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.lulee007.xitu.adapter.TagFollowAdapter;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.presenter.TagFollowGuidePresenter;
import com.lulee007.xitu.view.ITagFollowGuideView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;

public class TagFollowGuideActivity extends XTBaseActivity implements ITagFollowGuideView{

    private UltimateRecyclerView ultimateRecyclerView;
    private TagFollowAdapter tagFollowAdapter;
    private TagFollowGuidePresenter tagFollowGuidePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_follow_guide);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle("关注标签");
        }

        ultimateRecyclerView=(UltimateRecyclerView)findViewById(R.id.rv_follow_tags);
        ultimateRecyclerView.setHasFixedSize(false);
        ultimateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ultimateRecyclerView.addItemDividerDecoration(this);
        tagFollowAdapter=new TagFollowAdapter();
        ultimateRecyclerView.setAdapter(tagFollowAdapter);

        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(tagFollowAdapter);
        ultimateRecyclerView.addItemDecoration(headersDecor);

        View header= getLayoutInflater().inflate(R.layout.tag_follow_view_header,ultimateRecyclerView.mRecyclerView,false);
        ultimateRecyclerView.setParallaxHeader(header);

        tagFollowAdapter.setCustomLoadMoreView(LayoutInflater.from(this).inflate(R.layout.custom_bottom_progressbar, null));
        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                tagFollowGuidePresenter.loadMore(tagFollowAdapter.getLastItemPosition());
            }
        });

        ultimateRecyclerView.setEmptyView(R.layout.loading_data_progressbar);
        ultimateRecyclerView.showEmptyView();
        tagFollowGuidePresenter=new TagFollowGuidePresenter(this);
        tagFollowGuidePresenter.loadNewTags();
        Logger.d("在TagFollow页","OnCreate结束");

    }

    @Override
    public void addMoreTags(List<Tag> tags) {
        tagFollowAdapter.addMore(tags);
        int lastPos=tagFollowAdapter.getItemCount();
        tagFollowAdapter.notifyItemChanged(lastPos);
    }

    @Override
    public void addNewTags(HashMap<String, List<Tag>> tagsMap) {
        ultimateRecyclerView.hideEmptyView();
        tagFollowAdapter.init(tagsMap.get("hot"), tagsMap.get("normal"));
        tagFollowAdapter.notifyDataSetChanged();
        ultimateRecyclerView.enableLoadmore();
    }

    @Override
    public void addNewError() {

    }

    @Override
    public void addMoreError() {

    }

    @Override
    public void noMore() {
        ultimateRecyclerView.disableLoadmore();
    }
}
