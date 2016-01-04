package com.lulee007.xitu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.util.XTConstant;
import com.lulee007.xitu.view.fragment.TagWithUserStatusFragment;
import com.mikepenz.materialize.MaterializeBuilder;
import com.orhanobut.logger.Logger;

public class TagFollowGuideActivity extends XTBaseActivity {


    private TagWithUserStatusFragment tagWithUserStatusFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d("在TagFollow页：OnCreate开始");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_follow_guide);
        new MaterializeBuilder().withActivity(this).build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("关注标签");
        }
        tagWithUserStatusFragment = new TagWithUserStatusFragment();
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
                onBackPressed();
        }
        return true;

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_FIRST_USER) {
            if (requestCode == XTConstant.ACTIVITY_REQUEST_CODE.LOGIN_BY_PHONE) {
                tagWithUserStatusFragment.notifyUserLoggedIn();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
