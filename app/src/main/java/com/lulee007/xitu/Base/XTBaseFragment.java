package com.lulee007.xitu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
