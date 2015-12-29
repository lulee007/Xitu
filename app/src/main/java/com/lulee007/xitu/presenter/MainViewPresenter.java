package com.lulee007.xitu.presenter;

import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.view.IMainView;

/**
 * User: lulee007@live.com
 * Date: 2015-12-09
 * Time: 17:10
 */
public class MainViewPresenter {
    private  IMainView mainView;

    public MainViewPresenter(IMainView view){
        mainView=view;
    }

    public void init(){
        if(isFirstTimeIn()){
            mainView.showTagFollowGuideActivity();
        }else{
            mainView.showMainFragment();
        }
    }

    private boolean isFirstTimeIn(){
        return true;
    }

    public void showManageTagActivity() {
        if(AuthUserHelper.getInstance().isLoggedIn()){
            mainView.showManageTagActivity();
        }else{
            mainView.showNeedLoginDialog();
        }
    }

    public void toggleUserNameClick() {
        if(!AuthUserHelper.getInstance().isLoggedIn()){
            mainView.showChangeUserName();
        }else{
            mainView.showLoginOptionPage();
        }
    }
}
