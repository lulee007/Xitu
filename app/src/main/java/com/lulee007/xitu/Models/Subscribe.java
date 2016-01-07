package com.lulee007.xitu.models;

import com.google.gson.Gson;


public class Subscribe {
    /**
     * user : {"__type":"Pointer","className":"_User","objectId":"563c1d9xxxx749ea071246"}
     * tag : {"official":false,"updatedAt":"2015-12-15T02:24:50.991Z","color":"#ffcf11","viewsCount":114158,"objectId":"5597a04de4b08a686ce56e63","hot":true,"createdAt":"2015-07-04T08:58:53.410Z","icon":{"mime_type":"image/png","updatedAt":"2015-10-28T10:06:29.417Z","key":"0a3fd9348f29f55f9034.png","name":"tools-icon.png","objectId":"56309e2560b20fc9ded55fe2","createdAt":"2015-10-28T10:06:29.417Z","__type":"File","url":"http://ac-mhke0kuv.clouddn.com/0a3fd9348f29f55f9034.png","metaData":{"owner":"551d677ee4b0cd5b623f49cb","mime_type":"image/png"},"bucket":"mhke0kuv"},"className":"Tag","title":"工具资源","collectionsCount":8344,"alias":"freebie gongjuziyuan","__type":"Pointer","showOnNav":true,"subscribersCount":2475,"entriesCount":273}
     * createdAt : 2015-12-14T14:19:05.668Z
     * updatedAt : 2015-12-14T14:19:05.706Z
     * objectId : 566ecfd960b215d68bde0829
     */


    public static Subscribe objectFromData(String str) {

        return new Gson().fromJson(str, Subscribe.class);
    }


    /**
     * __type : Pointer
     * className : _User
     * objectId : 563c1d9xxxx749ea071246
     */

    private UserEntity user;
    /**
     * official : false
     * updatedAt : 2015-12-15T02:24:50.991Z
     * color : #ffcf11
     * viewsCount : 114158
     * objectId : 5597a04de4b08a686ce56e63
     * hot : true
     * createdAt : 2015-07-04T08:58:53.410Z
     * icon : {"mime_type":"image/png","updatedAt":"2015-10-28T10:06:29.417Z","key":"0a3fd9348f29f55f9034.png","name":"tools-icon.png","objectId":"56309e2560b20fc9ded55fe2","createdAt":"2015-10-28T10:06:29.417Z","__type":"File","url":"http://ac-mhke0kuv.clouddn.com/0a3fd9348f29f55f9034.png","metaData":{"owner":"551d677ee4b0cd5b623f49cb","mime_type":"image/png"},"bucket":"mhke0kuv"}
     * className : Tag
     * title : 工具资源
     * collectionsCount : 8344
     * alias : freebie gongjuziyuan
     * __type : Pointer
     * showOnNav : true
     * subscribersCount : 2475
     * entriesCount : 273
     */

    private Tag tag;
    private String createdAt;
    private String updatedAt;
    private String objectId;


    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setTag(Tag tag) {
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

    public Tag getTag() {
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



}

