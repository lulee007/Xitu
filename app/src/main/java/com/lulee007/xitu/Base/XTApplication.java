package com.lulee007.xitu.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.util.IconFontUtil;
import com.lulee007.xitu.util.XTConstant;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.typeface.GenericFont;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

import im.fir.sdk.FIR;

/**
 * User: lulee007@live.com
 * Date: 2015-12-07
 * Time: 17:03
 */
public class XTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AuthUserHelper.init(this);

        //pretty logger
        Logger.init("XTAppLog");

        //内存泄露检测
        LeakCanary.install(this);

        //only required if you add a custom or generic font on your own
        Iconics.init(getApplicationContext());
        //Generic font creation process
        GenericFont xtIconFont = IconFontUtil.buildXTFont();
        Iconics.registerFont(xtIconFont);

        FIR.init(this);

        MobclickAgent.setDebugMode(true);

    }

}
