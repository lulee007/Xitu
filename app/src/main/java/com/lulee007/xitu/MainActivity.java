package com.lulee007.xitu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.models.Account;
import com.lulee007.xitu.presenter.MainViewPresenter;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.util.IconFontUtil;
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
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
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
    private AccountHeader headerResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d("主页onCreate开始");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.mipmap.profile_bg)

                .withSelectionListEnabled(false)
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile profile) {
                        mainViewPresenter.toggleUserNameClick();
                        return false;
                    }
                })
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        mainViewPresenter.toggleUserIconClick();
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
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
                        new PrimaryDrawerItem().withIdentifier(R.string.home) .withName(R.string.home).withIcon(IconFontUtil.getIcon(this, "home")),
                        new PrimaryDrawerItem().withIdentifier(R.string.my_collection).withName(R.string.my_collection).withIcon(IconFontUtil.getIcon(this, "person")),
                        new PrimaryDrawerItem().withIdentifier(R.string.read_history).withName(R.string.read_history).withIcon(IconFontUtil.getIcon(this, "history")),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withIdentifier(R.string.settings).withName(R.string.settings),
                        new SecondaryDrawerItem().withIdentifier(R.string.editors).withName(R.string.editors),
                        new SecondaryDrawerItem().withIdentifier(R.string.feedback).withName(R.string.feedback),
                        new SecondaryDrawerItem().withIdentifier(R.string.share).withName(R.string.share)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // position start with 1

                        switch (drawerItem.getIdentifier()) {
                            case R.string.home:
                                return switchFragment(position);
                            case R.string.my_collection:
                                mainViewPresenter.toggleMyHomeClick(MainActivity.this);
                                return true;
                            case R.string.read_history:
                                return switchFragment(position);
                            case R.string.settings:
                                startActivity(SettingsActivity.class);
                                return true;
                            case R.string.editors:
                                startActivity(AuthorsActivity.class);
                                return true;
                            case R.string.feedback:
                                return true;
                            case R.string.share:
                                return true;
                            case R.string.logout:
                                AuthUserHelper.getInstance().deleteUser();
                                appDrawer.removeItem(R.string.logout);
                                if (currentFragment instanceof MainFragment) {
                                    ((MainFragment) currentFragment).notifyChildRefreshEntries();
                                }
                                fillAccountHeader(null);
                                return false;

                            default:
                                //0 header 4 divider
                                return true;
                        }
                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .build();


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        mainViewPresenter = new MainViewPresenter(this, this);
        fragmentManager = getSupportFragmentManager();

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


    private boolean switchFragment(int id) {

        switch (id) {
            case 3:
                if(!AuthUserHelper.getInstance().isLoggedIn()){
                    appDrawer.setSelection(R.string.home,false);
                    AuthUserHelper.getInstance().showNeedLoginDialog(this);
                    return true;
                }
                currentFragment = ListEntriesFragment.newInstanceForHistory();
                tabLayout.setVisibility(View.GONE);
                getSupportActionBar().setTitle(R.string.read_history);
                break;
            case 1:
                MainFragment mainFragment=new MainFragment();
                mainFragment.setParentTabLayout(tabLayout);
                currentFragment = mainFragment;
                tabLayout.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle(R.string.app_name);

                break;
            default:
                break;

        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, currentFragment);
        fragmentTransaction.commit();
        return false;

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
            MobclickAgent.onKillProcess(this);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case XTConstant.ACTIVITY_REQUEST_CODE.TAG_FOLLOW_GUIDE:
                case XTConstant.ACTIVITY_REQUEST_CODE.MANAGE_TAG:
                    if (currentFragment instanceof MainFragment) {
                        ((MainFragment) currentFragment).notifyChildRefreshEntries();
                    } else if (currentFragment == null) {
                        // first in from tag_follow_guide
                        showMainFragment();
                    }
                    break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        } else if (resultCode == Activity.RESULT_FIRST_USER) {
            switch (requestCode) {
                case XTConstant.ACTIVITY_REQUEST_CODE.LOGIN_BY_PHONE:
                    mainViewPresenter.updateUserHeader();
                    if (currentFragment instanceof MainFragment) {
                        ((MainFragment) currentFragment).notifyChildRefreshEntries();
                    }
                    break;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showTagFollowGuideActivity() {
        startActivityForResult(new Intent(this, TagFollowGuideActivity.class), XTConstant.ACTIVITY_REQUEST_CODE.TAG_FOLLOW_GUIDE);
    }


    @Override
    public void showMainFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment=new MainFragment();
        mainFragment.setParentTabLayout(tabLayout);
        currentFragment = mainFragment;
        fragmentTransaction.replace(R.id.fragment_main, currentFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void showManageTagActivity() {
        Intent intent = new Intent(this, ManageTagsActivity.class);
        intent.putExtra(ManageTagsActivity.INTENT_KEY_USER, AuthUserHelper.getInstance().getUser().toString());
        startActivityForResult(intent, XTConstant.ACTIVITY_REQUEST_CODE.MANAGE_TAG);
    }

    @Override
    public void showNeedLoginDialog() {
        AuthUserHelper.getInstance().showNeedLoginDialog(this);
    }

    @Override
    public void showChangeUserName() {
        Account account = AuthUserHelper.getInstance().getUserDetail();

        new MaterialDialog.Builder(this)
                .title("修改用户名")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入用户名", account.getUsername() == null ? "" : account.getUsername(), false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        Logger.d(input.toString());
                    }
                })
                .positiveText("确定")
                .negativeText("取消")
                .show();
    }

    @Override
    public void showLoginOptionPage() {
        Intent intent = new Intent(this, LoginOptionsActivity.class);
        intent.putExtra(LoginOptionsActivity.INTENT_KEY_NEED_LOGIN_RESULT, true);
        startActivityForResult(intent, XTConstant.ACTIVITY_REQUEST_CODE.LOGIN_BY_PHONE);
    }

    @Override
    public void fillAccountHeader(Account userDetail) {
        if (userDetail != null) {
            ArrayList pro = headerResult.getProfiles();
            if (pro != null && pro.size() > 0)
                for (int i = 0; i < pro.size(); i++) {
                    headerResult.removeProfile(0);
                }
            ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
            profileDrawerItem.withEmail(userDetail.getUsername());
            if (userDetail.getAvatar_large() == null) {
                profileDrawerItem.withIcon(R.mipmap.empty_avatar_user);
            } else {
                profileDrawerItem.withIcon(userDetail.getAvatar_large());
            }
            headerResult.addProfiles(profileDrawerItem);

            appDrawer.addItem(new SecondaryDrawerItem().withName(R.string.logout).withIdentifier(R.string.logout));
        } else {
            ArrayList pro = headerResult.getProfiles();
            if (pro != null && pro.size() > 0)
                for (int i = 0; i < pro.size(); i++) {
                    headerResult.removeProfile(0);
                }
            headerResult.addProfiles(
                    new ProfileDrawerItem()
                            .withEmail("登陆.注册")
                            .withIcon(R.mipmap.empty_avatar_user)
            );
        }
    }

    @Override
    public void showChangeUserIcon() {
        new MaterialDialog.Builder(this)
                .items("拍照", "选择照片")
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Logger.d(String.format("更换头像:%d %s", which, text));
                    }
                })
                .show();
    }

}
