package com.lulee007.xitu.models;

import com.google.gson.Gson;


public class Subscribe {


    /**
     * __type : Pointer
     * className : _User
     * objectId : 55d1a92160b2b52cdae75f7e
     */

    private UserEntity user;
    /**
     * __type : Pointer
     * className : Tag
     * objectId : 555e9acae4b00c57d99561e6
     */

    private TagEntity tag;
    private String createdAt;
    private String updatedAt;
    private String objectId;

    public static Subscribe objectFromData(String str) {

        return new Gson().fromJson(str, Subscribe.class);
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public UserEntity getUser() {
        return user;
    }

    public TagEntity getTag() {
        return tag;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public static class UserEntity {
        private String __type;
        private String className;
        private String objectId;

        public static UserEntity objectFromData(String str) {

            return new Gson().fromJson(str, UserEntity.class);
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String get__type() {
            return __type;
        }

        public String getClassName() {
            return className;
        }

        public String getObjectId() {
            return objectId;
        }
    }

    public static class TagEntity {
        private String __type;
        private String className;
        private String objectId;

        public static TagEntity objectFromData(String str) {

            return new Gson().fromJson(str, TagEntity.class);
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String get__type() {
            return __type;
        }

        public String getClassName() {
            return className;
        }

        public String getObjectId() {
            return objectId;
        }
    }
}

