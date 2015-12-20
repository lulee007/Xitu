package com.lulee007.xitu;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.lulee007.xitu.adapter.MainFragmentPagerAdapter;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.view.fragment.SubscribedTagsFragment;
import com.lulee007.xitu.view.fragment.TagWithUserStatusFragment;

import java.util.ArrayList;
import java.util.List;

public class ManageTagsActivity extends XTBaseActivity {

    public static final String INTENT_KEY_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_tags);
        setActionBarWithTitle(getString(R.string.manage_tag_activity_title));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_tag);
        ViewPager viewPager = (ViewPager) findViewById(R.id.tag_pager);
        List<Fragment> fragments= new ArrayList<>();
        fragments.add(new TagWithUserStatusFragment());
        fragments.add(new SubscribedTagsFragment());
        List<String> titles=new ArrayList<>();
        titles.add("所有标签");
        titles.add("已关注标签");
        FragmentManager fragmentManager=getSupportFragmentManager();
        MainFragmentPagerAdapter mainFragmentPagerAdapter=new MainFragmentPagerAdapter(fragmentManager,fragments,titles);
        viewPager.setAdapter(mainFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
