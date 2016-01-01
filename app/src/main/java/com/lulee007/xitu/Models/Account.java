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
     * sessionToken : yvyk3ili8afg2o43lpsxn126o
     * followersCount : 0
     * updatedAt : 2015-12-30T08:09:14.655Z
     * postedEntriesCount : 0
     * commentedEntriesCount : 0
     * collectedEntriesCount : 0
     * objectId : 568390f460b2c297d014f7d2
     * username : ILulee
     * createdAt : 2015-12-30T08:08:20.249Z
     * totalHotIndex : 0
     * emailVerified : false
     * totalCommentsCount : 0
     * blacklist : false
     * mobilePhoneNumber : 13073299956
     * apply : false
     * followeesCount : 0
     * mobilePhoneVerified : true
     */

    private int viewedEntriesCount;
    private String role;
    private int totalCollectionsCount;
    private int subscribedTagsCount;
    private String sessionToken;
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
    private boolean blacklist;
    private String mobilePhoneNumber;
    private boolean apply;
    private int followeesCount;
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

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
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

    public String getSessionToken() {
        return sessionToken;
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

    public boolean isMobilePhoneVerified() {
        return mobilePhoneVerified;
    }
}
