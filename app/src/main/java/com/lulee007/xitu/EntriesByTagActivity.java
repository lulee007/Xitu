package com.lulee007.xitu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lulee007.xitu.adapter.CommonFragmentPagerAdapter;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.presenter.EntriesByTagPresenter;
import com.lulee007.xitu.presenter.ListEntriesFragmentPresenter;
import com.lulee007.xitu.view.IEntriesByTagView;
import com.lulee007.xitu.view.fragment.ListEntriesFragment;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.ArrayList;
import java.util.List;

public class EntriesByTagActivity extends XTBaseActivity implements IEntriesByTagView {

    public static final String BUNDLE_KEY_TAG_TITLE = "tag_title";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EntriesByTagPresenter entriesByTagPresenter;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_home);
        new MaterializeBuilder().withActivity(this).build();

        String tagTitle = getIntent().getStringExtra(BUNDLE_KEY_TAG_TITLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            View viewActionBar = getLayoutInflater().inflate(R.layout.entries_by_tag_title, null);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            actionBar.setCustomView(viewActionBar,params);
            actionBar.setDisplayShowCustomEnabled(true);
            TextView title= (TextView) actionBar.getCustomView().findViewById(R.id.tag_name);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            title.setText(tagTitle);
        }
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.entry_pager);
        List<String> titles = new ArrayList<>();
        titles.add("热门");
        titles.add("最新");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ListEntriesFragment.newInstance(ListEntriesFragmentPresenter.BY_TAG_HOT, tagTitle));
        fragments.add(ListEntriesFragment.newInstance(ListEntriesFragmentPresenter.BY_TAG_LATEST, tagTitle));
        FragmentManager fragmentManager = getSupportFragmentManager();
        CommonFragmentPagerAdapter commonFragmentPagerAdapter = new CommonFragmentPagerAdapter(fragmentManager, fragments, titles);
        viewPager.setAdapter(commonFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        entriesByTagPresenter = new EntriesByTagPresenter(this);
        entriesByTagPresenter.loadTag(tagTitle);
    }

    @Override
    public void setTagIcon(Tag tag) {
        if (actionBar != null) {
            ImageView icon= (ImageView) actionBar.getCustomView().findViewById(R.id.tag_icon);
            Glide.with(this).load(tag.getIcon().getUrl()).into(icon);
        }
    }
}
