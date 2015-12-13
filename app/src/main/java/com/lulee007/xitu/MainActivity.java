package com.lulee007.xitu;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.presenter.MainViewPresenter;
import com.lulee007.xitu.view.IMainView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout= (TabLayout) findViewById(R.id.tab_layout_main);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mainViewPresenter = new MainViewPresenter(this);
        mainViewPresenter.init();
        Logger.d("在Main页，OnCreate结束");

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment  mainFragment=new MainFragment(tabLayout);
        fragmentTransaction.replace(R.id.fragment_main,mainFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_add_tag:

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
        }

        return true;
    }

    @Override
    public void onBackPressed() {
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
        }else{
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public void showTagFollowGuideActivity() {
        startActivity(TagFollowGuideActivity.class);
    }

    @Override
    public void showExitAppToast() {

    }

    @Override
    public void showMainFragment() {

    }
}
