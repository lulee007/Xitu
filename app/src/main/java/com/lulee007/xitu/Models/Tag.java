package com.lulee007.xitu.models;

import com.google.gson.Gson;


public class Tag {

    private boolean isSubscribed;
    private String subscribedId;

    private boolean official;
    private String updatedAt;
    private String color;
    private int viewsCount;
    private String objectId;
    private boolean hot;
    /**
     * bucket : mhke0kuv
     * metaData : {"owner":"unknown","size":235936}
     * url : http://ac-mhke0kuv.clouddn.com/51d6d58806f3a929.jpg
     * __type : File
     * createdAt : 2015-11-09T09:46:46.954Z
     * objectId : 56406b8660b27cc2783fe6b1
     * name : wp_004.jpg
     * key : 51d6d58806f3a929.jpg
     * updatedAt : 2015-11-09T09:46:46.954Z
     */

    private BackgroundEntity background;
    private String createdAt;
    /**
     * mime_type : image/png
     * updatedAt : 2015-09-30T09:05:15.296Z
     * key : 7bab0e1e66ea386e6f94.png
     * name : android-icon.svg.png
     * objectId : 560ba5cbddb2dd0035490094
     * createdAt : 2015-09-30T09:05:15.296Z
     * __type : File
     * url : http://ac-mhke0kuv.clouddn.com/7bab0e1e66ea386e6f94.png
     * metaData : {"owner":"unknown","mime_type":"image/png"}
     * bucket : mhke0kuv
     */

    private IconEntity icon;
    private String title;
    private int collectionsCount;
    private String alias;
    private boolean showOnNav;
    private int subscribersCount;
    private int entriesCount;

    public static Tag objectFromData(String str) {

        return new Gson().fromJson(str, Tag.class);
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

    public void setBackground(BackgroundEntity background) {
        this.background = background;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setIcon(IconEntity icon) {
        this.icon = icon;
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

    public BackgroundEntity getBackground() {
        return background;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public IconEntity getIcon() {
        return icon;
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

    public boolean isShowOnNav() {
        return showOnNav;
    }

    public int getSubscribersCount() {
        return subscribersCount;
    }

    public int getEntriesCount() {
        return entriesCount;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public String getSubscribedId() {
        return subscribedId;
    }

    public void setSubscribedId(String subscribedId) {
        this.subscribedId = subscribedId;
    }

    public static class BackgroundEntity {
        private String bucket;
        /**
         * owner : unknown
         * size : 235936
         */

        private MetaDataEntity metaData;
        private String url;
        private String __type;
        private String createdAt;
        private String objectId;
        private String name;
        private String key;
        private String updatedAt;

        public static BackgroundEntity objectFromData(String str) {

            return new Gson().fromJson(str, BackgroundEntity.class);
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public void setMetaData(MetaDataEntity metaData) {
            this.metaData = metaData;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getBucket() {
            return bucket;
        }

        public MetaDataEntity getMetaData() {
            return metaData;
        }

        public String getUrl() {
            return url;
        }

        public String get__type() {
            return __type;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getObjectId() {
            return objectId;
        }

        public String getName() {
            return name;
        }

        public String getKey() {
            return key;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public static class MetaDataEntity {
            private String owner;
            private int size;

            public static MetaDataEntity objectFromData(String str) {

                return new Gson().fromJson(str, MetaDataEntity.class);
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getOwner() {
                return owner;
            }

            public int getSize() {
                return size;
            }
        }
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
         * owner : unknown
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



