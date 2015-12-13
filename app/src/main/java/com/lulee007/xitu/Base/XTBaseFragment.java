package com.lulee007.xitu.base;

import android.support.v4.app.Fragment;

/**
 * User: lulee007@live.com
 * Date: 2015-12-13
 * Time: 15:17
 */
public  abstract class XTBaseFragment extends Fragment{
    protected  boolean isNeedLogin=false;

    //TODO 1、是否需要登录
    public boolean isLoggedIn(){
        return false;
    }

    public  boolean isNeedLogin(){
        return isNeedLogin;
    }


}
