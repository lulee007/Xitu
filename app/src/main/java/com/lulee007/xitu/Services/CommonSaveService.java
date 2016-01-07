package com.lulee007.xitu.services;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.lulee007.xitu.base.XTBaseService;
import com.lulee007.xitu.models.Account;
import com.lulee007.xitu.util.AuthUserHelper;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;
import rx.functions.Func1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-20
 * Time: 19:59
 */
public class CommonSaveService extends XTBaseService<CommonSaveService.CommonSaveWebService> {


    public CommonSaveService() {
        super(CommonSaveWebService.class);
    }



    protected interface CommonSaveWebService {
        @POST("/batch/save")
        Observable<Object> save(@Body SaveRequest request);

        @POST("/batch/save")
        Observable<LinkedTreeMap> save(@Body HashMap request);
    }

    public Observable<Account> saveRegisterPhone(String phoneNumber, String userName, String pwd) {
        final String internalId = UUID.randomUUID().toString();
        String dataStr = String.format("{\"requests\":[{\"body\":{\"__children\":[],\"__internalId\":\"%s\",\"mobilePhoneNumber\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"},\"method\":\"POST\",\"path\":\"/1.1/users\"}]}",
                internalId,
                phoneNumber,
                pwd,
                userName
        );

        HashMap data = new Gson().fromJson(dataStr, HashMap.class);
        return webService.save(data)
                .map(new Func1<LinkedTreeMap, Account>() {
                    @Override
                    public Account call(LinkedTreeMap o) {
                        return Account.objectFromData(new Gson().toJson(o.get(internalId)));
                    }
                });

    }

    public Observable<String> saveSubscription(String cid) {
        String classNameToSave = "Subscribe";

        PostEntity.BodyEntity.ChildrenEntity childTagEntity = new PostEntity.BodyEntity.ChildrenEntity();
        childTagEntity.setCid(cid);
        childTagEntity.setClassName("Tag");
        childTagEntity.setKey("tag");

        final PostEntity postEntity = buildPostEntity(classNameToSave, childTagEntity);

        SaveRequest saveRequest = new SaveRequest();
        saveRequest.addRequest(postEntity);
        return webService.save(saveRequest)
                .map(new Func1<Object, String>() {
                    @Override
                    public String call(Object o) {
                        try {
                            LinkedTreeMap data = ((LinkedTreeMap<String, LinkedTreeMap>) o).get(postEntity.getBody().get__internalId());
                            return (String) (data.get("objectId"));
                        } catch (Exception e) {
                            Logger.e(e.getCause(), "common save service save put internalId error: " + e.getMessage());
                        }
                        return null;
                    }
                });

    }

    public Observable<String> saveCollectEntry(String cid) {
        String classNameToSave = "Collection";

        PostEntity.BodyEntity.ChildrenEntity childTagEntity = new PostEntity.BodyEntity.ChildrenEntity();
        childTagEntity.setCid(cid);
        childTagEntity.setClassName("Entry");
        childTagEntity.setKey("entry");

        final PostEntity postEntity = buildPostEntity(classNameToSave, childTagEntity);

        SaveRequest saveRequest = new SaveRequest();
        saveRequest.addRequest(postEntity);
        return webService.save(saveRequest)
                .map(new Func1<Object, String>() {
                    @Override
                    public String call(Object o) {
                        try {
                            LinkedTreeMap data = ((LinkedTreeMap<String, LinkedTreeMap>) o).get(postEntity.getBody().get__internalId());
                            return (String) (data.get("objectId"));
                        } catch (Exception e) {
                            Logger.e(e.getCause(), "common save service save put internalId error: " + e.getMessage());
                        }
                        return null;
                    }
                });
    }

    @NonNull
    private PostEntity buildPostEntity(String className, PostEntity.BodyEntity.ChildrenEntity childTagEntity) {
        PostEntity postEntity = new PostEntity();
        postEntity.setMethod("POST");
        postEntity.setPath(String.format("/1.1/classes/%s", className));

        PostEntity.BodyEntity bodyEntity = new PostEntity.BodyEntity();
        bodyEntity.set__internalId(UUID.randomUUID().toString());

        List<PostEntity.BodyEntity.ChildrenEntity> childrenEntities = new ArrayList<>();

        //tag
        if (childTagEntity != null)
            childrenEntities.add(childTagEntity);

        PostEntity.BodyEntity.ChildrenEntity childrenEntity = new PostEntity.BodyEntity.ChildrenEntity();
        childrenEntity.setCid((String) AuthUserHelper.getInstance().getUser().get("objectId"));
//            childrenEntity.setCid("563c1d9xxxx749ea071246");
        childrenEntity.setClassName("_User");
        childrenEntity.setKey("user");
        //user
        childrenEntities.add(childrenEntity);


        bodyEntity.set__children(childrenEntities);

        postEntity.setBody(bodyEntity);
        return postEntity;
    }

    private PostEntity buildPostEntity(String className, List<PostEntity.BodyEntity.ChildrenEntity> childrenEntities) {
        PostEntity postEntity = buildPostEntity(className, (PostEntity.BodyEntity.ChildrenEntity) null);
        List<PostEntity.BodyEntity.ChildrenEntity> childrenEntityList = postEntity.getBody().get__children();
        if (childrenEntityList == null) {
            childrenEntityList = new ArrayList<>();
            postEntity.getBody().set__children(childrenEntityList);
        }
        if (childrenEntities != null) {
            childrenEntityList.addAll(childrenEntities);
        }
        return postEntity;

    }

    protected static class SaveRequest {

        public SaveRequest() {
            this.requests = new ArrayList<>();
        }

        private List<PostEntity> requests;


        public List<PostEntity> getRequests() {
            return requests;
        }

        public void setRequests(List<PostEntity> requests) {
            this.requests = requests;
        }

        public void addRequest(PostEntity postEntity) {
            if (this.requests == null) {
                this.requests = new ArrayList<>();
            }
            if (postEntity != null)
                this.requests.add(postEntity);
        }
    }

    protected static class PostEntity {

        /**
         * __children : [{"cid":"563c1d9xxxx749ea071246","className":"_User","key":"user"},{"cid":"5597838ee4b08a686ce2319d","className":"Tag","key":"tag"}]
         * __internalId : 81f07941-ec59-400c-86c5-9be00d4133d6
         */

        private BodyEntity body;
        /**
         * body : {"__children":[{"cid":"563c1d9xxxx749ea071246","className":"_User","key":"user"},{"cid":"5597838ee4b08a686ce2319d","className":"Tag","key":"tag"}],"__internalId":"81f07941-ec59-400c-86c5-9be00d4133d6"}
         * method : POST
         * path : /1.1/classes/Subscribe
         */

        private String method;
        private String path;

        public static PostEntity objectFromData(String str) {

            return new Gson().fromJson(str, PostEntity.class);
        }

        public void setBody(BodyEntity body) {
            this.body = body;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public BodyEntity getBody() {
            return body;
        }

        public String getMethod() {
            return method;
        }

        public String getPath() {
            return path;
        }

        public static class BodyEntity {
            private String __internalId;
            /**
             * cid : 563c1d9xxxx749ea071246
             * className : _User
             * key : user
             */

            private List<ChildrenEntity> __children;

            public static BodyEntity objectFromData(String str) {

                return new Gson().fromJson(str, BodyEntity.class);
            }

            public void set__internalId(String __internalId) {
                this.__internalId = __internalId;
            }

            public void set__children(List<ChildrenEntity> __children) {
                this.__children = __children;
            }

            public String get__internalId() {
                return __internalId;
            }

            public List<ChildrenEntity> get__children() {
                return __children;
            }

            public static class ChildrenEntity {
                private String cid;
                private String className;
                private String key;

                public static ChildrenEntity objectFromData(String str) {

                    return new Gson().fromJson(str, ChildrenEntity.class);
                }

                public void setCid(String cid) {
                    this.cid = cid;
                }

                public void setClassName(String className) {
                    this.className = className;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getCid() {
                    return cid;
                }

                public String getClassName() {
                    return className;
                }

                public String getKey() {
                    return key;
                }
            }
        }
    }
}
