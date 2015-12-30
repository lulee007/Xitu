package com.lulee007.xitu.util;

import android.content.Context;

import com.github.pwittchen.prefser.library.Prefser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

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

        return prefser.get("user",HashMap.class,null);

    }

    public void saveUser(HashMap user){
        HashMap tempUser= new Gson().fromJson("{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"563c1d9560b25749ea071246\"}", new TypeToken<HashMap>() {
        }.getType());
        prefser.put("user",user);
        prefser.put(KEY_IsLoggedIn,Boolean.FALSE);
    }

    public void deleteUser(){
        prefser.remove("user");
        prefser.put(KEY_IsLoggedIn,Boolean.FALSE);
    }

    public static void init(Context context) {
        prefser = new Prefser(context);
    }
}
