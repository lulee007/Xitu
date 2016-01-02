package com.lulee007.xitu.view;

import com.lulee007.xitu.models.Account;

public interface IMainView {
    void showTagFollowGuideActivity();
    void showMainFragment();

    void showManageTagActivity();

    void showNeedLoginDialog();

    void showChangeUserName();

    void showLoginOptionPage();

    void fillAccountHeader(Account userDetail);

    void showChangeUserIcon();
}
