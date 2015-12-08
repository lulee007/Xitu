package com.lulee007.xitu.models;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.google.gson.Gson;

import java.util.List;


public class Entry {


    private String category;
    /**
     * __type : Relation
     * className : Tag
     */

    private TagsEntity tags;
    private int hotIndex;
    private String updatedAt;
    private int viewsCount;
    private int collectionCount;
    private String content;
    private String objectId;
    private boolean hot;
    private boolean original;
    private String createdAt;
    private String title;
    private boolean english;
    private double rankIndex;
    private String url;
    private boolean gfw;
    private int commentsCount;
    /**
     * __type : Pointer
     * className : _User
     * objectId : 5602161360b249ad610b5171
     */

    private UserEntity user;
    private String originalUrl;
    private List<?> tagsTitleArray;

    public static Entry objectFromData(String str) {

        return new Gson().fromJson(str, Entry.class);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTags(TagsEntity tags) {
        this.tags = tags;
    }

    public void setHotIndex(int hotIndex) {
        this.hotIndex = hotIndex;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEnglish(boolean english) {
        this.english = english;
    }

    public void setRankIndex(double rankIndex) {
        this.rankIndex = rankIndex;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setGfw(boolean gfw) {
        this.gfw = gfw;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setTagsTitleArray(List<?> tagsTitleArray) {
        this.tagsTitleArray = tagsTitleArray;
    }

    public String getCategory() {
        return category;
    }

    public TagsEntity getTags() {
        return tags;
    }

    public int getHotIndex() {
        return hotIndex;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public String getContent() {
        return content;
    }

    public String getObjectId() {
        return objectId;
    }

    public boolean isHot() {
        return hot;
    }

    public boolean isOriginal() {
        return original;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEnglish() {
        return english;
    }

    public double getRankIndex() {
        return rankIndex;
    }

    public String getUrl() {
        return url;
    }

    public boolean isGfw() {
        return gfw;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public List<?> getTagsTitleArray() {
        return tagsTitleArray;
    }

    public static class TagsEntity {
        private String __type;
        private String className;

        public static TagsEntity objectFromData(String str) {

            return new Gson().fromJson(str, TagsEntity.class);
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String get__type() {
            return __type;
        }

        public String getClassName() {
            return className;
        }
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

