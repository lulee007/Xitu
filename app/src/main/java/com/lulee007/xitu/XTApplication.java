package com.lulee007.xitu;

import android.app.Application;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.lulee007.xitu.Models.Collection;
import com.lulee007.xitu.Models.Comment;
import com.lulee007.xitu.Models.Entry;
import com.lulee007.xitu.Models.Read;
import com.lulee007.xitu.Models.Subscribe;
import com.lulee007.xitu.Models.Tag;
import com.lulee007.xitu.Models.UserNotification;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

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
