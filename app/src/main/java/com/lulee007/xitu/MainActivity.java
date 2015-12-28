package com.lulee007.xitu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.presenter.MainViewPresenter;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.util.XTConstant;
import com.lulee007.xitu.view.IMainView;
import com.lulee007.xitu.view.fragment.ListEntriesFragment;
import com.lulee007.xitu.view.fragment.MainFragment;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class MainActivity extends XTBaseActivity implements NavigationView.OnNavigationItemSelectedListener, IMainView {

    private MainViewPresenter mainViewPresenter;
    private boolean doubleClickExit = false;
    private TabLayout tabLayout;
    private DrawerLayout drawer;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d("主页onCreate开始");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mainViewPresenter = new MainViewPresenter(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        currentFragment = new MainFragment(tabLayout);
        fragmentTransaction.replace(R.id.fragment_main, currentFragment);
        fragmentTransaction.commit();
        mainViewPresenter.init();
        Logger.d("主页onCreate结束");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add_tag:
                mainViewPresenter.showManageTagActivity();
                break;
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                startActivity(SettingsActivity.class);
                break;
            case R.id.nav_editors:
                startActivity(AuthorsActivity.class);
                break;
            case R.id.nav_home:
            case R.id.nav_read_history:
                switchFragment(item.getItemId());
                break;
            case R.id.nav_my_collection:

            default:
                break;
        }
        return true;
    }

    private void switchFragment(int id) {
        switch (id){
            case R.id.nav_read_history:
                currentFragment=ListEntriesFragment.newInstanceForHistory();
                tabLayout.setVisibility(View.GONE);
                getSupportActionBar().setTitle(R.string.read_history);
                break;
            case R.id.nav_home:
                currentFragment = new MainFragment(tabLayout);
                tabLayout.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle(R.string.app_name);

                break;
            default:
                break;

        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, currentFragment);
        fragmentTransaction.commit();
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
            return;
        }
        if (!doubleClickExit) {
            showToast("再按一次退出应用");
            doubleClickExit = true;
            Observable.timer(2, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<Long, Object>() {
                        @Override
                        public Object call(Long aLong) {
                            doubleClickExit = false;
                            return null;
                        }
                    }).subscribe();
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case XTConstant.ACTIVITY_REQUEST_CODE.TAG_FOLLOW_GUIDE:
                if (resultCode == XTConstant.ACTIVITY_RESULT_CODE.TAG_FOLLOW_GUIDE_SUBSCRIBE_DONE
                        && currentFragment instanceof MainFragment){
                    ((MainFragment)currentFragment).notifyChildRefreshEntries();
                }
                    break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showTagFollowGuideActivity() {
        startActivityForResult(new Intent(this, TagFollowGuideActivity.class), XTConstant.ACTIVITY_REQUEST_CODE.TAG_FOLLOW_GUIDE);
    }


    @Override
    public void showMainFragment() {

    }

    @Override
    public void showManageTagActivity() {
        Intent intent = new Intent(this, ManageTagsActivity.class);
        intent.putExtra(ManageTagsActivity.INTENT_KEY_USER, AuthUserHelper.getInstance().getUser().toString());
        startActivity(intent);
    }

    @Override
    public void showNeedLoginDialog() {

    }

}
