package com.lulee007.xitu.view;

import com.lulee007.xitu.models.LeanCloudError;

import java.util.HashMap;

public interface IVerifyPhoneView {

    void onVerifySuccess();
    void onVerifyError(LeanCloudError result);
}
