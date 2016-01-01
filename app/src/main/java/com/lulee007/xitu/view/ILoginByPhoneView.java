package com.lulee007.xitu.view;

import com.lulee007.xitu.models.LeanCloudError;

/**
 * User: lulee007@live.com
 * Date: 2016-01-01
 * Time: 16:48
 */
public interface ILoginByPhoneView  {
    void onLoginSuccess();
    void onLoginError(LeanCloudError error);
}
