package com.lulee007.xitu.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AuthUserHelper {
    private static AuthUserHelper ourInstance = new AuthUserHelper();

    public static AuthUserHelper getInstance() {
        return ourInstance;
    }

    private AuthUserHelper() {
    }

    public  boolean isLoggedIn(){
        return true;
    }

    public HashMap getUser() {

            return new Gson().fromJson("{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"563c1d9560b25749ea071246\"}",new  TypeToken<HashMap>(){}.getType());


    }
}
