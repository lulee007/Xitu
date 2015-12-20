package com.lulee007.xitu.util;

import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONObject getUser() {
        try {
            return new JSONObject("{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"563c1d9560b25749ea071246\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
