package com.lulee007.xitu.models;

import com.google.gson.Gson;

/**
 * User: lulee007@live.com
 * Date: 2015-12-11
 * Time: 21:31
 */
public class Author {

    /**
     * viewedEntriesCount : 866
     * role : editor
     * totalCollectionsCount : 5401
     * subscribedTagsCount : 189
     * blogCheckDate : {"iso":"2015-12-09T12:04:47.372Z","__type":"Date"}
     * followersCount : 6
     * updatedAt : 2015-12-11T13:29:37.610Z
     * postedEntriesCount : 832
     * latestCollectionUserNotification : {"iso":"2015-12-11T01:48:30.161Z","__type":"Date"}
     * commentedEntriesCount : 80
     * raw : {"id":5383066644,"idstr":"5383066644","class":1,"screen_name":"稀土圈","name":"稀土圈","province":"11","city":"8","location":"北京 海淀区","description":"每天推荐特别的人，官网地址 xitu.io。","url":"http:\/\/xitu.io","profile_image_url":"http:\/\/tp1.sinaimg.cn\/5383066644\/50\/5711603199\/1","cover_image":"http:\/\/ww4.sinaimg.cn\/crop.0.0.920.300\/005SiNxyjw1evu16l6sxgj30pk08ct95.jpg","profile_url":"xitucircle","domain":"xitucircle","weihao":"","gender":"m","followers_count":6973,"friends_count":552,"pagefriends_count":2,"statuses_count":2683,"favourites_count":39,"created_at":"Fri Nov 21 16:36:50 +0800 2014","following":false,"allow_all_act_msg":false,"geo_enabled":true,"verified":true,"verified_type":2,"remark":"","status":{"created_at":"Tue Nov 17 09:10:03 +0800 2015","id":3910094355539366,"mid":"3910094355539366","idstr":"3910094355539366","text":"【学习】nodejs stream 手册 - 写在前面的话: 如果你正在学习Nodejs，那么流一定是一个你需要掌握的概念。如果你想成为一个Node高手，那么流一定是武功秘籍中不可缺少的一个部分。关于流这个主题，由Node高手substack带来的stream-handbook绝对是经典。分享by@Archer_小A 详戳→http:\/\/t.cn\/RU8JsDz","source_allowclick":0,"source_type":1,"source":"<a href=\"http:\/\/weibo.com\/\" rel=\"nofollow\">微博 weibo.com<\/a>","favorited":false,"truncated":false,"in_reply_to_status_id":"","in_reply_to_user_id":"","in_reply_to_screen_name":"","pic_urls":[],"geo":null,"reposts_count":0,"comments_count":0,"attitudes_count":0,"mlevel":0,"visible":{"type":0,"list_id":0},"biz_feature":0,"darwin_tags":[],"userType":0},"ptype":0,"allow_all_comment":true,"avatar_large":"http:\/\/tp1.sinaimg.cn\/5383066644\/180\/5711603199\/1","avatar_hd":"http:\/\/ww2.sinaimg.cn\/crop.0.0.511.511.1024\/005SiNxyjw1emjqhw1hk7j30e80e8q33.jpg","verified_reason":"北京北比信息技术有限公司","verified_trade":"","verified_reason_url":"","verified_source":"","verified_source_url":"","verified_state":0,"verified_level":3,"verified_reason_modified":"","verified_contact_name":"","verified_contact_email":"","verified_contact_mobile":"","follow_me":false,"online_status":0,"bi_followers_count":280,"lang":"zh-cn","star":0,"mbtype":0,"mbrank":0,"block_word":0,"block_app":0,"credit_score":80,"user_ability":0,"urank":20}
     * collectedEntriesCount : 195
     * objectId : 551d6923e4b0cd5b623f54da
     * username : 稀土圈
     * latestLoginedInAt : {"iso":"2015-12-11T09:55:19.857Z","__type":"Date"}
     * createdAt : 2015-04-02T16:06:59.376Z
     * totalHotIndex : 165284
     * blogAddress : http://xitu.io
     * self_description : 每天推荐特别的人，官网地址 xitu.io。
     * latestCheckedNotificationAt : {"iso":"2015-12-11T09:00:16.332Z","__type":"Date"}
     * cover_image_phone :
     * emailVerified : false
     * totalCommentsCount : 376
     * installation : {"__type":"Pointer","className":"_Installation","objectId":"289N9fzKbaAbO21UdLmcFG55DKUi9G6T"}
     * blacklist : false
     * weibo_id : 5383066644
     * apply : false
     * followeesCount : 0
     * avatar_hd : http://ww2.sinaimg.cn/crop.0.0.511.511.1024/005SiNxyjw1emjqhw1hk7j30e80e8q33.jpg
     * blogRssAddress :
     * authData : {"weibo":{"uid":"5383066644","access_token":"2.00YXnSsF03JiAL439648f0140JCqs6","expiration_in":"43199"}}
     * avatar_large : http://tp1.sinaimg.cn/5383066644/180/5711603199/1
     * mobilePhoneVerified : false
     */


    public static Author objectFromData(String str) {

        return new Gson().fromJson(str, Author.class);
    }


    private int viewedEntriesCount;
    private String role;
    private int totalCollectionsCount;
    private int subscribedTagsCount;
    /**
     * iso : 2015-12-09T12:04:47.372Z
     * __type : Date
     */

    private BlogCheckDateEntity blogCheckDate;
    private int followersCount;
    private String updatedAt;
    private int postedEntriesCount;
    /**
     * iso : 2015-12-11T01:48:30.161Z
     * __type : Date
     */

    private LatestCollectionUserNotificationEntity latestCollectionUserNotification;
    private int commentedEntriesCount;
    private String raw;
    private int collectedEntriesCount;
    private String objectId;
    private String username;
    /**
     * iso : 2015-12-11T09:55:19.857Z
     * __type : Date
     */

    private LatestLoginedInAtEntity latestLoginedInAt;
    private String createdAt;
    private int totalHotIndex;
    private String blogAddress;
    private String self_description;
    /**
     * iso : 2015-12-11T09:00:16.332Z
     * __type : Date
     */

    private LatestCheckedNotificationAtEntity latestCheckedNotificationAt;
    private String cover_image_phone;
    private boolean emailVerified;
    private int totalCommentsCount;
    /**
     * __type : Pointer
     * className : _Installation
     * objectId : 289N9fzKbaAbO21UdLmcFG55DKUi9G6T
     */

    private InstallationEntity installation;
    private boolean blacklist;
    private String weibo_id;
    private boolean apply;
    private int followeesCount;
    private String avatar_hd;
    private String blogRssAddress;
    /**
     * weibo : {"uid":"5383066644","access_token":"2.00YXnSsF03JiAL439648f0140JCqs6","expiration_in":"43199"}
     */

    private AuthDataEntity authData;
    private String avatar_large;
    private boolean mobilePhoneVerified;


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

    public void setBlogCheckDate(BlogCheckDateEntity blogCheckDate) {
        this.blogCheckDate = blogCheckDate;
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

    public void setLatestCollectionUserNotification(LatestCollectionUserNotificationEntity latestCollectionUserNotification) {
        this.latestCollectionUserNotification = latestCollectionUserNotification;
    }

    public void setCommentedEntriesCount(int commentedEntriesCount) {
        this.commentedEntriesCount = commentedEntriesCount;
    }

    public void setRaw(String raw) {
        this.raw = raw;
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

    public void setLatestLoginedInAt(LatestLoginedInAtEntity latestLoginedInAt) {
        this.latestLoginedInAt = latestLoginedInAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setTotalHotIndex(int totalHotIndex) {
        this.totalHotIndex = totalHotIndex;
    }

    public void setBlogAddress(String blogAddress) {
        this.blogAddress = blogAddress;
    }

    public void setSelf_description(String self_description) {
        this.self_description = self_description;
    }

    public void setLatestCheckedNotificationAt(LatestCheckedNotificationAtEntity latestCheckedNotificationAt) {
        this.latestCheckedNotificationAt = latestCheckedNotificationAt;
    }

    public void setCover_image_phone(String cover_image_phone) {
        this.cover_image_phone = cover_image_phone;
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

    public void setWeibo_id(String weibo_id) {
        this.weibo_id = weibo_id;
    }

    public void setApply(boolean apply) {
        this.apply = apply;
    }

    public void setFolloweesCount(int followeesCount) {
        this.followeesCount = followeesCount;
    }

    public void setAvatar_hd(String avatar_hd) {
        this.avatar_hd = avatar_hd;
    }

    public void setBlogRssAddress(String blogRssAddress) {
        this.blogRssAddress = blogRssAddress;
    }

    public void setAuthData(AuthDataEntity authData) {
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

    public BlogCheckDateEntity getBlogCheckDate() {
        return blogCheckDate;
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

    public LatestCollectionUserNotificationEntity getLatestCollectionUserNotification() {
        return latestCollectionUserNotification;
    }

    public int getCommentedEntriesCount() {
        return commentedEntriesCount;
    }

    public String getRaw() {
        return raw;
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

    public LatestLoginedInAtEntity getLatestLoginedInAt() {
        return latestLoginedInAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getTotalHotIndex() {
        return totalHotIndex;
    }

    public String getBlogAddress() {
        return blogAddress;
    }

    public String getSelf_description() {
        return self_description;
    }

    public LatestCheckedNotificationAtEntity getLatestCheckedNotificationAt() {
        return latestCheckedNotificationAt;
    }

    public String getCover_image_phone() {
        return cover_image_phone;
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

    public String getWeibo_id() {
        return weibo_id;
    }

    public boolean isApply() {
        return apply;
    }

    public int getFolloweesCount() {
        return followeesCount;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public String getBlogRssAddress() {
        return blogRssAddress;
    }

    public AuthDataEntity getAuthData() {
        return authData;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public boolean isMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    public static class BlogCheckDateEntity {
        private String iso;
        private String __type;

        public static BlogCheckDateEntity objectFromData(String str) {

            return new Gson().fromJson(str, BlogCheckDateEntity.class);
        }

        public void setIso(String iso) {
            this.iso = iso;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getIso() {
            return iso;
        }

        public String get__type() {
            return __type;
        }
    }

    public static class LatestCollectionUserNotificationEntity {
        private String iso;
        private String __type;

        public static LatestCollectionUserNotificationEntity objectFromData(String str) {

            return new Gson().fromJson(str, LatestCollectionUserNotificationEntity.class);
        }

        public void setIso(String iso) {
            this.iso = iso;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getIso() {
            return iso;
        }

        public String get__type() {
            return __type;
        }
    }

    public static class LatestLoginedInAtEntity {
        private String iso;
        private String __type;

        public static LatestLoginedInAtEntity objectFromData(String str) {

            return new Gson().fromJson(str, LatestLoginedInAtEntity.class);
        }

        public void setIso(String iso) {
            this.iso = iso;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getIso() {
            return iso;
        }

        public String get__type() {
            return __type;
        }
    }

    public static class LatestCheckedNotificationAtEntity {
        private String iso;
        private String __type;

        public static LatestCheckedNotificationAtEntity objectFromData(String str) {

            return new Gson().fromJson(str, LatestCheckedNotificationAtEntity.class);
        }

        public void setIso(String iso) {
            this.iso = iso;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getIso() {
            return iso;
        }

        public String get__type() {
            return __type;
        }
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

    public static class AuthDataEntity {
        /**
         * uid : 5383066644
         * access_token : 2.00YXnSsF03JiAL439648f0140JCqs6
         * expiration_in : 43199
         */

        private WeiboEntity weibo;

        public static AuthDataEntity objectFromData(String str) {

            return new Gson().fromJson(str, AuthDataEntity.class);
        }

        public void setWeibo(WeiboEntity weibo) {
            this.weibo = weibo;
        }

        public WeiboEntity getWeibo() {
            return weibo;
        }

        public static class WeiboEntity {
            private String uid;
            private String access_token;
            private String expiration_in;

            public static WeiboEntity objectFromData(String str) {

                return new Gson().fromJson(str, WeiboEntity.class);
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setAccess_token(String access_token) {
                this.access_token = access_token;
            }

            public void setExpiration_in(String expiration_in) {
                this.expiration_in = expiration_in;
            }

            public String getUid() {
                return uid;
            }

            public String getAccess_token() {
                return access_token;
            }

            public String getExpiration_in() {
                return expiration_in;
            }
        }
    }
}

