package com.lulee007.xitu.models;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;



@AVClassName("UserNotification")
public class UserNotification extends AVObject {
    private Boolean isChecked = null;

    public UserNotification() {
    }

    public UserNotification(String var1) {
        super(var1);
    }

    public String getCategory() {
        return this.getType().equals("collection")?"collection":"comment";
    }

    public String getCommentContent() {
        try {
            String var2 = this.getAVObject("comment").getString("content");
            return var2;
        } catch (NullPointerException var3) {
            var3.printStackTrace();
            return "";
        }
    }

    public Entry getEntry() {
        return null;
    }

    public AVUser getNotifierUser() {
        return this.getAVUser("notifierUser");
    }

    public String getNotifierUserAvatar() {
        return this.getNotifierUser().getString("avatar_large");
    }

    public String getNotifierUsername() {
        try {
            String var2 = this.getNotifierUser().getString("username");
            return var2;
        } catch (NullPointerException var3) {
            var3.printStackTrace();
            return "";
        }
    }

    public String getReplyContent() {
        return this.getAVObject("reply") != null?this.getAVObject("reply").getString("content"):"";
    }

    public String getType() {
        return this.getString("type");
    }

    public boolean isChecked() {
        if(this.isChecked == null) {
            this.isChecked = Boolean.valueOf(this.getBoolean("check"));
        }

        return this.isChecked.booleanValue();
    }

    public void setIsChecked(boolean var1) {
        this.isChecked = Boolean.valueOf(var1);
    }
}
