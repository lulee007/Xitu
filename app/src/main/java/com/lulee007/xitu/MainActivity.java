package com.lulee007.xitu;

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
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
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
                .addProfiles(
                        new ProfileDrawerItem()
                                .withEmail("登陆.注册")
                                .withIcon(R.mipmap.empty_avatar_user)
                )
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
                        new PrimaryDrawerItem().withName(R.string.home).withIcon(IconFontUtil.getIcon(this, "home")),
                        new PrimaryDrawerItem().withName(R.string.my_collection).withIcon(IconFontUtil.getIcon(this, "person")),
                        new PrimaryDrawerItem().withName(R.string.read_history).withIcon(IconFontUtil.getIcon(this, "history")),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.settings),
                        new SecondaryDrawerItem().withName(R.string.editors),
                        new SecondaryDrawerItem().withName(R.string.feedback),
                        new SecondaryDrawerItem().withName(R.string.share),
                        new SecondaryDrawerItem().withName(R.string.abount_open)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // position start with 1

                        switch (position) {
                            case 1:
                                switchFragment(position);
                                return false;
                            case 2:
                                Account account = AuthUserHelper.getInstance().getUserDetail();
                                Intent intent = AuthorHomeActivity.buildIntent(MainActivity.this, account.getAvatar_large(), account.getObjectId());
                                startActivity(intent);
                                return true;
                            case 3:
                                switchFragment(position);
                                return false;
                            case 5:
                                startActivity(SettingsActivity.class);
                                return true;
                            case 6:
                                startActivity(AuthorsActivity.class);
                                return true;
                            case 9:
                                new LibsBuilder()
                                        //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                                        .withLibraries("logger","prefser","ultimaterecyclerview","sweetalert","rxjava")
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .withAutoDetect(true)
                                        .withActivityTitle("开源库")
                                                //start the activity
                                        .start(MainActivity.this);
                                return true;
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
        currentFragment = new MainFragment(tabLayout);
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
        startActivity(LoginOptionsActivity.class);
    }

    @Override
    public void fillAccountHeader(Account userDetail) {
        if (userDetail != null) {
            headerResult.removeProfile(0);
            ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
            profileDrawerItem.withEmail(userDetail.getUsername());
            if (userDetail.getAvatar_large() == null) {
                profileDrawerItem.withIcon(R.mipmap.empty_avatar_user);
            } else {
                profileDrawerItem.withIcon(userDetail.getAvatar_large());
            }
            headerResult.addProfiles(profileDrawerItem);
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
