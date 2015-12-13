package com.lulee007.xitu.util;

import com.orhanobut.logger.Logger;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: lulee007@live.com
 * Date: 2015-12-13
 * Time: 20:03
 */
public class DateUtil {

    public static String upToNow(String dateTimeStr){
        try {
            String strDate = dateTimeStr.replace("T", " ").substring(0, 19);

            Date date = strToDateLong(strDate);
            Date current = new Date();
            long between = current.getTime() - date.getTime();
            if (between < 60 * 1000) {
                return "一分钟前";
            } else if (between < 60L * 1000 * 60) {
                return String.format("%d分钟前", (int) (1.0 * between / (60 * 1000) + 0.5));
            } else if (between < 60L * 1000 * 60 * 24) {
                return String.format("%d小时前", (int) (1.0 * between / (60 * 1000 * 60) + 0.5));

            } else if (between < 60L * 1000 * 60 * 24 * 7) {
                return String.format("%d天前", (int) (1.0 * between / (60 * 1000 * 60 * 24) + 0.5));

            } else if (between < 60L * 1000 * 60 * 24 * 30) {
                return String.format("%d周前", (int) (1.0 * between / (60L * 1000 * 60 * 24 * 7) + 0.5));

            } else if (between < 60L * 1000 * 60 * 24 * 365) {
                return String.format("%d月前", (int) ((1.0 * between) / (60L * 1000 * 60 * 24 * 30) + 0.5));

            } else {
                return String.format("%d年前", (int) ((1.0 * between) / (60D * 1000 * 60 * 24 * 365) + 0.5));
            }
        }catch (Exception ex){
            Logger.e(ex,"DateUtil.upToNow error");
        }
        return "";

    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * 2015-04-02T15:59:58.924Z
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
}
