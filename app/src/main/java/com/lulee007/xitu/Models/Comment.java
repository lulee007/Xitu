package com.lulee007.xitu.models;



        import android.text.format.DateFormat;
        import com.avos.avoscloud.AVClassName;
        import com.avos.avoscloud.AVObject;
        import com.avos.avoscloud.AVUser;

@AVClassName("Comment")
public class Comment extends AVObject {
    public Comment() {
    }

    public Comment(String var1) {
        super(var1);
    }

    public String getCommentTime() {
        return DateFormat.format("yyyy-MM-dd  HH:mm", this.getCreatedAt()).toString();
    }

    public AVUser getCommentUser() {
        return this.getAVUser("user");
    }

    public String getCommentUserAvatar() {
        return this.getCommentUser().getString("avatar_large");
    }

    public String getCommentUsername() {
        return this.getAVUser("user").getString("username");
    }

    public String getContent() {
        return this.getReply() != null?"回复 " + this.getReply().getCommentUsername() + " : " + this.getString("content"):this.getString("content");
    }



    public String getOriginalComment() {
        return this.getString("content");
    }

    public String getRawText() {
        return this.getCommentUser().getUsername() + ":" + this.getContent();
    }

    public Comment getReply() {
        return (Comment)this.getAVObject("reply");
    }
}
