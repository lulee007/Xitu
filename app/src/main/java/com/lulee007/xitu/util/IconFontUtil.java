package com.lulee007.xitu.util;

import android.content.Context;

import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.typeface.GenericFont;
import com.mikepenz.iconics.typeface.IIcon;

/**
 * User: lulee007@live.com
 * Date: 2015-12-29
 * Time: 20:13
 */
public class IconFontUtil {

    public static IIcon getIcon(Context context,String name){
       return Iconics.findFont(context, XTConstant.XT_FONT.FONT_KEY).getIcon( XTConstant.XT_FONT.FONT_KEY+"_"+name);
    }

    public static GenericFont buildXTFont(){
        //register custom fonts like this (or also provide a font definition file)
        //Generic font creation process
        GenericFont xtIconFont = new GenericFont("XTIconFont", "阿里妈妈", XTConstant.XT_FONT.FONT_KEY, XTConstant.XT_FONT.FONT_NAME);
        xtIconFont.registerIcon("share", '\ue600');
        xtIconFont.registerIcon("liked", '\ue601');
        xtIconFont.registerIcon("home", '\ue602');
        xtIconFont.registerIcon("view", '\ue603');
        xtIconFont.registerIcon("check", '\ue604');
        xtIconFont.registerIcon("like", '\ue605');
        xtIconFont.registerIcon("person", '\ue606');
        xtIconFont.registerIcon("history", '\ue607');
        return xtIconFont;
    }

}
