package com.lulee007.xitu.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulee007.xitu.R;
import com.lulee007.xitu.adapter.CommonFragmentPagerAdapter;
import com.lulee007.xitu.base.XTBaseFragment;
import com.lulee007.xitu.presenter.ListEntriesFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * not login : tab{emptyview+ recommond list}
 * login :tag{hot+ user subscribed tags list}
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends XTBaseFragment {

    private CommonFragmentPagerAdapter commonFragmentPagerAdapter;

    private TabLayout parentTabLayout;
    private TaggedCardEntriesFragment taggedCardEntriesFragment;

    public void setParentTabLayout(TabLayout tabLayout) {
        // Required empty public constructor
        parentTabLayout = tabLayout;
    }

    public MainFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container,false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_main_view);

        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        if (!isLoggedIn()) {
            fragments = new ArrayList<>();
            fragments.add(taggedCardEntriesFragment = new TaggedCardEntriesFragment());
            fragments.add(ListEntriesFragment.newInstanceForRecommended(ListEntriesFragmentPresenter.BY_RECOMMENDED));
            titles = new ArrayList<>();
            titles.add("我的关注");
            titles.add("发现");
        } else {
//            fragments.add()
        }
        commonFragmentPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(commonFragmentPagerAdapter);
        parentTabLayout.setupWithViewPager(viewPager);
        parentTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        parentTabLayout.setTabMode(TabLayout.MODE_FIXED);
        return view;
    }


    public void notifyChildRefreshEntries() {
        if (taggedCardEntriesFragment != null && taggedCardEntriesFragment.mPresenter != null)
            taggedCardEntriesFragment.mPresenter.loadNew();
    }
}
