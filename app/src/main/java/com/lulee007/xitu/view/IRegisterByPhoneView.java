package com.lulee007.xitu.view;

import com.lulee007.xitu.base.IXTBaseView;
import com.lulee007.xitu.models.LeanCloudError;

import org.json.JSONObject;

public interface IRegisterByPhoneView {

    void onRegisterSuccess(String phone);
    void onRegisterError(LeanCloudError jsonObject);
}
