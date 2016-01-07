package com.lulee007.xitu.models;

import com.google.gson.Gson;

/**
 * User: lulee007@live.com
 * Date: 2015-12-31
 * Time: 19:59
 */
public class Account {


    /**
     * viewedEntriesCount : 0
     * role : guest
     * totalCollectionsCount : 0
     * subscribedTagsCount : 0
     * followersCount : 0
     * updatedAt : 2016-01-02T06:10:44.745Z
     * postedEntriesCount : 0
     * commentedEntriesCount : 0
     * collectedEntriesCount : 0
     * objectId : 568390f460b2c297d014f7d2
     * username : ILulee
     * createdAt : 2015-12-30T08:08:20.249Z
     * totalHotIndex : 0
     * emailVerified : false
     * totalCommentsCount : 0
     * installation : {"__type":"Pointer","className":"_Installation","objectId":"9hCg0aBc9spmN9aLnjMGgsopCbfr38Ge"}
     * blacklist : false
     * mobilePhoneNumber : 13122299956
     * apply : false
     * followeesCount : 0
     * authData : null
     * avatar_large : http://ac-mhke0kuv.clouddn.com/uTFC7YuRpY8NYcHRqTDv4czdOrkE6KNr4VJcE3SB
     * mobilePhoneVerified : true
     */

    private int viewedEntriesCount;
    private String role;
    private int totalCollectionsCount;
    private int subscribedTagsCount;
    private int followersCount;
    private String updatedAt;
    private int postedEntriesCount;
    private int commentedEntriesCount;
    private int collectedEntriesCount;
    private String objectId;
    private String username;
    private String createdAt;
    private int totalHotIndex;
    private boolean emailVerified;
    private int totalCommentsCount;
    /**
     * __type : Pointer
     * className : _Installation
     * objectId : 9hCg0aBc9spmN9aLnjMGgsopCbfr38Ge
     */

    private InstallationEntity installation;
    private boolean blacklist;
    private String mobilePhoneNumber;
    private boolean apply;
    private int followeesCount;
    private Object authData;
    private String avatar_large;
    private boolean mobilePhoneVerified;

    public static Account objectFromData(String str) {

        return new Gson().fromJson(str, Account.class);
    }

    public void setViewedEntriesCount(int viewedEntriesCount) {
        this.viewedEntriesCount = viewedEntriesCount;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setTotalCollectionsCount(int totalCollectionsCount) {
        this.totalCollectionsCount = totalCollectionsCount;
    }

    public void setSubscribedTagsCount(int subscribedTagsCount) {
        this.subscribedTagsCount = subscribedTagsCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setPostedEntriesCount(int postedEntriesCount) {
        this.postedEntriesCount = postedEntriesCount;
    }

    public void setCommentedEntriesCount(int commentedEntriesCount) {
        this.commentedEntriesCount = commentedEntriesCount;
    }

    public void setCollectedEntriesCount(int collectedEntriesCount) {
        this.collectedEntriesCount = collectedEntriesCount;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setTotalHotIndex(int totalHotIndex) {
        this.totalHotIndex = totalHotIndex;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setTotalCommentsCount(int totalCommentsCount) {
        this.totalCommentsCount = totalCommentsCount;
    }

    public void setInstallation(InstallationEntity installation) {
        this.installation = installation;
    }

    public void setBlacklist(boolean blacklist) {
        this.blacklist = blacklist;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public void setApply(boolean apply) {
        this.apply = apply;
    }

    public void setFolloweesCount(int followeesCount) {
        this.followeesCount = followeesCount;
    }

    public void setAuthData(Object authData) {
        this.authData = authData;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }

    public void setMobilePhoneVerified(boolean mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
    }

    public int getViewedEntriesCount() {
        return viewedEntriesCount;
    }

    public String getRole() {
        return role;
    }

    public int getTotalCollectionsCount() {
        return totalCollectionsCount;
    }

    public int getSubscribedTagsCount() {
        return subscribedTagsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getPostedEntriesCount() {
        return postedEntriesCount;
    }

    public int getCommentedEntriesCount() {
        return commentedEntriesCount;
    }

    public int getCollectedEntriesCount() {
        return collectedEntriesCount;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getUsername() {
        return username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getTotalHotIndex() {
        return totalHotIndex;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public int getTotalCommentsCount() {
        return totalCommentsCount;
    }

    public InstallationEntity getInstallation() {
        return installation;
    }

    public boolean isBlacklist() {
        return blacklist;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public boolean isApply() {
        return apply;
    }

    public int getFolloweesCount() {
        return followeesCount;
    }

    public Object getAuthData() {
        return authData;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public boolean isMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    public static class InstallationEntity {
        private String __type;
        private String className;
        private String objectId;

        public static InstallationEntity objectFromData(String str) {

            return new Gson().fromJson(str, InstallationEntity.class);
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
