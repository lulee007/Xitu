package com.lulee007.xitu;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.view.fragment.TagWithUserStatusFragment;
import com.orhanobut.logger.Logger;

public class TagFollowGuideActivity extends XTBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d("在TagFollow页：OnCreate开始");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_follow_guide);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("关注标签");
        }
        TagWithUserStatusFragment tagWithUserStatusFragment = new TagWithUserStatusFragment();
        Bundle arguments = new Bundle();
        arguments.putBoolean(TagWithUserStatusFragment.BUNDLE_KEY_SHOW_HEADER, true);
        tagWithUserStatusFragment.setArguments(arguments);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.tags_content, tagWithUserStatusFragment).commit();

        Logger.d("在TagFollow页：OnCreate结束");

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


}
