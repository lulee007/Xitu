package com.lulee007.xitu.util;

import org.junit.Test;

public class DateUtilTest {

    @Test
    public void testUpToNow() throws Exception {

        System.out.println(DateUtil.upToNow("2015-12-13T21:03:01.924Z").toString());
        System.out.println(DateUtil.upToNow("2015-12-13T20:35:58.924Z").toString());
        System.out.println(DateUtil.upToNow("2015-12-11T17:36:58.924Z").toString());
        System.out.println(DateUtil.upToNow("2015-12-01T20:36:58.924Z").toString());
        System.out.println(DateUtil.upToNow("2015-11-11T20:36:58.924Z").toString());
        System.out.println(DateUtil.upToNow("2014-11-11T20:36:58.924Z").toString());

        /**
         * 一分钟前
         * 28分钟前
         * 2天前
         * 2周前
         * 1月前
         * 1年前
         */
    }
}