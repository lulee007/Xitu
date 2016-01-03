package com.lulee007.xitu.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.github.pwittchen.prefser.library.Prefser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lulee007.xitu.LoginOptionsActivity;
import com.lulee007.xitu.models.Account;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AuthUserHelper {
    private static AuthUserHelper ourInstance = new AuthUserHelper();

    private static Prefser prefser;
    final String KEY_IsLoggedIn = "user_isLoggedIn";

    public static AuthUserHelper getInstance() {
        return ourInstance;
    }

    private AuthUserHelper() {
    }

    public boolean isLoggedIn() {
        return prefser.get(KEY_IsLoggedIn, Boolean.class, Boolean.FALSE);
    }

    public HashMap getUser() {

        return prefser.get("user", HashMap.class, null);

    }

    public void saveUser(HashMap user) {
        prefser.put("user", user);
        prefser.put(KEY_IsLoggedIn, Boolean.TRUE);
    }

    public void saveUserDetail(Account account) {
        prefser.put("user_detail", account);
        if (account.isMobilePhoneVerified()) {
            HashMap tempUser = new Gson().fromJson(String.format("{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"%s\"}", account.getObjectId()), new TypeToken<HashMap>() {
            }.getType());
            saveUser(tempUser);
        }
    }

    public void deleteUser() {
        prefser.remove("user");
        prefser.remove("user_detail");
        prefser.put(KEY_IsLoggedIn, Boolean.FALSE);
    }

    public static void init(Context context) {
        prefser = new Prefser(context);
    }

    public Account getUserDetail() {
        return prefser.get("user_detail", Account.class, null);
    }

    public void showNeedLoginDialog(final Context context) {
        new SweetAlertDialog(context).setTitleText("需要登陆")
                .setContentText("登陆后即可使用更多功能")
                .setConfirmText("现在去登陆")
                .setCancelText("取消")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent intent = new Intent(context, LoginOptionsActivity.class);
                        intent.putExtra(LoginOptionsActivity.INTENT_KEY_NEED_LOGIN_RESULT,true);
                        ((Activity) context).startActivityForResult(intent, XTConstant.ACTIVITY_REQUEST_CODE.LOGIN_BY_PHONE);
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }
}
