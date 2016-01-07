package com.lulee007.xitu.presenter;

import android.content.Context;
import android.content.Intent;

import com.github.pwittchen.prefser.library.Prefser;
import com.lulee007.xitu.AuthorHomeActivity;
import com.lulee007.xitu.MainActivity;
import com.lulee007.xitu.models.Account;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.view.IMainView;

/**
 * User: lulee007@live.com
 * Date: 2015-12-09
 * Time: 17:10
 */
public class MainViewPresenter {
    private IMainView mainView;
    private Prefser prefser;
    final String KEY_IsFirstTimeIn = "isFirstTimeIn";

    public MainViewPresenter(IMainView view, Context context) {
        mainView = view;
        prefser = new Prefser(context);
    }

    public void init() {
        mainView.fillAccountHeader(AuthUserHelper.getInstance().getUserDetail());
        if (isFirstTimeIn()) {
            mainView.showTagFollowGuideActivity();
        } else {
            mainView.showMainFragment();
        }
    }

    private boolean isFirstTimeIn() {
        Boolean result = prefser.get(KEY_IsFirstTimeIn, Boolean.class, Boolean.TRUE);
        if (result) {
            prefser.put(KEY_IsFirstTimeIn, Boolean.FALSE);
        }
        return result;
    }

    public void showManageTagActivity() {
        if (AuthUserHelper.getInstance().isLoggedIn()) {
            mainView.showManageTagActivity();
        } else {
            mainView.showNeedLoginDialog();
        }
    }

    public void toggleUserNameClick() {
        if (AuthUserHelper.getInstance().isLoggedIn()) {
            mainView.showChangeUserName();
        } else {
            mainView.showLoginOptionPage();
        }
    }

    public void toggleUserIconClick() {
        if (AuthUserHelper.getInstance().isLoggedIn()) {
            mainView.showChangeUserIcon();
        } else {
            mainView.showLoginOptionPage();
        }
    }

    public void toggleMyHomeClick(Context context) {
        if(AuthUserHelper.getInstance().isLoggedIn()) {
            Account account = AuthUserHelper.getInstance().getUserDetail();
            Intent intent = AuthorHomeActivity.buildIntent(context, account.getAvatar_large(), account.getObjectId());
            context.startActivity(intent);
        }else{
            mainView.showNeedLoginDialog();
        }
    }

    public void updateUserHeader() {
        mainView.fillAccountHeader(AuthUserHelper.getInstance().getUserDetail());
    }
}
