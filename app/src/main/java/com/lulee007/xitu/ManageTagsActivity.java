package com.lulee007.xitu;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.lulee007.xitu.adapter.CommonFragmentPagerAdapter;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.view.fragment.SubscribedTagsFragment;
import com.lulee007.xitu.view.fragment.TagWithUserStatusFragment;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.ArrayList;
import java.util.List;

public class ManageTagsActivity extends XTBaseActivity {

    public static final String INTENT_KEY_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_tags);
        new MaterializeBuilder().withActivity(this).build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionBarWithTitle(getString(R.string.manage_tag_activity_title));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.tag_pager);
        List<Fragment> fragments= new ArrayList<>();
        fragments.add(new TagWithUserStatusFragment());
        fragments.add(new SubscribedTagsFragment());
        List<String> titles=new ArrayList<>();
        titles.add("所有标签");
        titles.add("已关注标签");
        FragmentManager fragmentManager=getSupportFragmentManager();
        CommonFragmentPagerAdapter commonFragmentPagerAdapter =new CommonFragmentPagerAdapter(fragmentManager,fragments,titles);
        viewPager.setAdapter(commonFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
