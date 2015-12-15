package com.lulee007.xitu.models;

import com.google.gson.Gson;


public class Subscribe {
    /**
     * user : {"__type":"Pointer","className":"_User","objectId":"563c1d9560b25749ea071246"}
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
     * objectId : 563c1d9560b25749ea071246
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

    private TagEntity tag;
    private String createdAt;
    private String updatedAt;
    private String objectId;


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
        private boolean official;
        private String updatedAt;
        private String color;
        private int viewsCount;
        private String objectId;
        private boolean hot;
        private String createdAt;
        /**
         * mime_type : image/png
         * updatedAt : 2015-10-28T10:06:29.417Z
         * key : 0a3fd9348f29f55f9034.png
         * name : tools-icon.png
         * objectId : 56309e2560b20fc9ded55fe2
         * createdAt : 2015-10-28T10:06:29.417Z
         * __type : File
         * url : http://ac-mhke0kuv.clouddn.com/0a3fd9348f29f55f9034.png
         * metaData : {"owner":"551d677ee4b0cd5b623f49cb","mime_type":"image/png"}
         * bucket : mhke0kuv
         */

        private IconEntity icon;
        private String className;
        private String title;
        private int collectionsCount;
        private String alias;
        private String __type;
        private boolean showOnNav;
        private int subscribersCount;
        private int entriesCount;

        public static TagEntity objectFromData(String str) {

            return new Gson().fromJson(str, TagEntity.class);
        }

        public void setOfficial(boolean official) {
            this.official = official;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setViewsCount(int viewsCount) {
            this.viewsCount = viewsCount;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setHot(boolean hot) {
            this.hot = hot;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setIcon(IconEntity icon) {
            this.icon = icon;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCollectionsCount(int collectionsCount) {
            this.collectionsCount = collectionsCount;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public void setShowOnNav(boolean showOnNav) {
            this.showOnNav = showOnNav;
        }

        public void setSubscribersCount(int subscribersCount) {
            this.subscribersCount = subscribersCount;
        }

        public void setEntriesCount(int entriesCount) {
            this.entriesCount = entriesCount;
        }

        public boolean isOfficial() {
            return official;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getColor() {
            return color;
        }

        public int getViewsCount() {
            return viewsCount;
        }

        public String getObjectId() {
            return objectId;
        }

        public boolean isHot() {
            return hot;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public IconEntity getIcon() {
            return icon;
        }

        public String getClassName() {
            return className;
        }

        public String getTitle() {
            return title;
        }

        public int getCollectionsCount() {
            return collectionsCount;
        }

        public String getAlias() {
            return alias;
        }

        public String get__type() {
            return __type;
        }

        public boolean isShowOnNav() {
            return showOnNav;
        }

        public int getSubscribersCount() {
            return subscribersCount;
        }

        public int getEntriesCount() {
            return entriesCount;
        }

        public static class IconEntity {
            private String mime_type;
            private String updatedAt;
            private String key;
            private String name;
            private String objectId;
            private String createdAt;
            private String __type;
            private String url;
            /**
             * owner : 551d677ee4b0cd5b623f49cb
             * mime_type : image/png
             */

            private MetaDataEntity metaData;
            private String bucket;

            public static IconEntity objectFromData(String str) {

                return new Gson().fromJson(str, IconEntity.class);
            }

            public void setMime_type(String mime_type) {
                this.mime_type = mime_type;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public void set__type(String __type) {
                this.__type = __type;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setMetaData(MetaDataEntity metaData) {
                this.metaData = metaData;
            }

            public void setBucket(String bucket) {
                this.bucket = bucket;
            }

            public String getMime_type() {
                return mime_type;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public String getKey() {
                return key;
            }

            public String getName() {
                return name;
            }

            public String getObjectId() {
                return objectId;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public String get__type() {
                return __type;
            }

            public String getUrl() {
                return url;
            }

            public MetaDataEntity getMetaData() {
                return metaData;
            }

            public String getBucket() {
                return bucket;
            }

            public static class MetaDataEntity {
                private String owner;
                private String mime_type;

                public static MetaDataEntity objectFromData(String str) {

                    return new Gson().fromJson(str, MetaDataEntity.class);
                }

                public void setOwner(String owner) {
                    this.owner = owner;
                }

                public void setMime_type(String mime_type) {
                    this.mime_type = mime_type;
                }

                public String getOwner() {
                    return owner;
                }

                public String getMime_type() {
                    return mime_type;
                }
            }
        }
    }


}

