package com.lulee007.xitu;

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

        //如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
//        AVOSCloud.initialize(this, BuildConfig.AVOSCloud_App_Id, BuildConfig.AVOSCloud_App_Key);
//        AVObject.registerSubclass(Collection.class);
//        AVObject.registerSubclass(Comment.class);
//        AVObject.registerSubclass(Entry.class);
//        AVObject.registerSubclass(Read.class);
//        AVObject.registerSubclass(Subscribe.class);
//        AVObject.registerSubclass(UserNotification.class);

    }
}
