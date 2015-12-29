package com.lulee007.xitu.util;

/**
 * User: lulee007@live.com
 * Date: 2015-12-29
 * Time: 13:37
 */

import android.support.v4.view.MotionEventCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MS5
 */
public class LeanCloudMD5Util {
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final int TYPE_WIFI = 1;


    public static String md5(String string) {
        try {
            return computeMD5(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh,UTF-8 should be supported?", e);
        }
    }
    public static String computeMD5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input, TYPE_NOT_CONNECTED, input.length);
            byte[] md5bytes = md.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = TYPE_NOT_CONNECTED; i < md5bytes.length; i += TYPE_WIFI) {
                String hex = Integer.toHexString(md5bytes[i] & MotionEventCompat.ACTION_MASK);
                if (hex.length() == TYPE_WIFI) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}