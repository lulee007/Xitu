package com.lulee007.xitu.models;


import com.google.gson.Gson;

public class Collection {

    /**
     * __type : Pointer
     * className : _User
     * objectId : 563c1d9xxxx749ea071246
     */

    private UserEntity user;
    /**
     * __type : Pointer
     * className : Entry
     * objectId : 56403b2e60b259ca8e31cc4b
     */

    private Entry entry;
    /**
     * user : {"__type":"Pointer","className":"_User","objectId":"563c1d9xxxx749ea071246"}
     * entry : {"__type":"Pointer","className":"Entry","objectId":"56403b2e60b259ca8e31cc4b"}
     * createdAt : 2015-11-10T01:44:43.588Z
     * updatedAt : 2015-11-10T01:44:43.671Z
     * objectId : 56414c0b00b0023c3ab7da21
     */

    private String createdAt;
    private String updatedAt;
    private String objectId;

    public static Collection objectFromData(String str) {

        return new Gson().fromJson(str, Collection.class);
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
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

    public Entry getEntry() {
        return entry;
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

}
