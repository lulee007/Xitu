package com.lulee007.xitu.base;

import android.app.Application;

import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

/**
 * User: lulee007@live.com
 * Date: 2015-12-07
 * Time: 17:03
 */
public class XTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //pretty logger
        Logger.init("XTAppLog");

        //内存泄露检测
        LeakCanary.install(this);

    }
}
