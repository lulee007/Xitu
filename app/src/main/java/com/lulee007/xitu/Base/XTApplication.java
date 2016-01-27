package com.lulee007.xitu.base;

import android.app.Application;

import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.util.DrawerImageLoaderHelper;
import com.lulee007.xitu.util.IconFontUtil;
import com.mcxiaoke.packer.helper.PackerNg;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.typeface.GenericFont;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.AnalyticsConfig;
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

        DrawerImageLoaderHelper.init();

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

        // 如果没有使用PackerNg打包添加渠道，默认返回的是""
        // com.mcxiaoke.packer.helper.PackerNg
        final String market = PackerNg.getMarket(this);
        // 或者使用 PackerNg.getMarket(Context,defaultValue)
        // 之后就可以使用了，比如友盟可以这样设置
        Logger.d("=====market:{%s}=====",market);
        AnalyticsConfig.setChannel(market);
        MobclickAgent.setDebugMode(true);

    }

}
