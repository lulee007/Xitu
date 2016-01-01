package com.lulee007.xitu.models;

import com.google.gson.Gson;

/**
 * User: lulee007@live.com
 * Date: 2015-12-31
 * Time: 14:29
 */
public class LeanCloudError {

    /**
     * code : 603
     * error : 无效的短信验证码
     */

    private int code;
    private String error;

    public static LeanCloudError objectFromData(String str) {

        return new Gson().fromJson(str, LeanCloudError.class);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
