package com.lulee007.xitu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
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
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class MainActivity extends XTBaseActivity implements IMainView {

    private MainViewPresenter mainViewPresenter;
    private boolean doubleClickExit = false;
    private TabLayout tabLayout;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private Drawer appDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d("主页onCreate开始");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.profile_bg)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withEmail("lulee007@live.com")
                                .withName("lulee007")
                                .withIcon(R.mipmap.entry_image_default)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();


        appDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.home),
                        new PrimaryDrawerItem().withName(R.string.my_collection),
                        new PrimaryDrawerItem().withName(R.string.read_history),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.settings),
                        new SecondaryDrawerItem().withName(R.string.editors),
                        new SecondaryDrawerItem().withName(R.string.feedback),
                        new SecondaryDrawerItem().withName(R.string.share)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // position start with 1
                        switch (position) {
                            case 1:
                                switchFragment(position);
                                break;
                            case 2:
                                HashMap <String,String> userMap=AuthUserHelper.getInstance().getUser();
                                Intent intent=AuthorHomeActivity.buildIntent(MainActivity.this,"","");
                                startActivity(intent);
                                break;
                            case 3:
                                switchFragment(position);
                                break;
                            case 5:
                                startActivity(AuthorsActivity.class);
                                break;
                            case 6:
                                startActivity(SettingsActivity.class);
                                break;
                            default:
                                //0 header 4 divider
                                break;
                        }
                        Logger.d(position + "");
                        return false;
                    }
                })
                .build();


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

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

        switch (item.getItemId()) {
            case R.id.action_add_tag:
                mainViewPresenter.showManageTagActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void switchFragment(int id) {
        switch (id) {
            case 3:
                currentFragment = ListEntriesFragment.newInstanceForHistory();
                tabLayout.setVisibility(View.GONE);
                getSupportActionBar().setTitle(R.string.read_history);
                break;
            case 1:
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

    }

    @Override
    public void onBackPressed() {
        if (appDrawer.isDrawerOpen()) {
            appDrawer.closeDrawer();
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

    @Override
    public void showChangeUserName() {

    }

    @Override
    public void showLoginOptionPage() {
        startActivity(LoginOptionsActivity.class);
    }

}
