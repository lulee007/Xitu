package com.lulee007.xitu.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * User: lulee007@live.com
 * Date: 2015-12-13
 * Time: 15:47
 */
public class CommonFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public CommonFragmentPagerAdapter(FragmentManager fm, @NonNull List<Fragment> fragments1, @NonNull List<String> titles) {
        super(fm);
        this.fragments = fragments1;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
            return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
