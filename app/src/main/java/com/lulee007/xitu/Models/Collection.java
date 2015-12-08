package com.lulee007.xitu.models;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import android.support.annotation.NonNull;
import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import java.io.Serializable;

@AVClassName("Collection")
public class Collection extends AVObject implements Serializable {
    public Collection() {
    }

    public Collection buildConnectionWithCurrentUser(@NonNull Entry var1) {
        this.put("entry", var1);
        this.put("user", AVUser.getCurrentUser());
        return this;
    }
}
