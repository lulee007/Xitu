package com.lulee007.xitu.services;

import com.google.gson.Gson;
import com.lulee007.xitu.base.XTBaseService;
import com.lulee007.xitu.models.Subscribe;

import java.util.HashMap;
import java.util.List;

import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;
import rx.functions.Func1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-08
 * Time: 09:44
 */
public class SubscribeService extends XTBaseService<SubscribeService.SubscribeWebService> {


    public SubscribeService(){
        super(SubscribeWebService.class);
    }

    protected   interface SubscribeWebService {

        @GET("/classes/Subscribe")
        Observable<SubscribeDataEnvelope> getSubscribeList(@QueryMap HashMap<String, String> params);

        @DELETE("/classes/Subscribe/{objectId}")
        Observable<Object> unSubscribe(@Path("objectId") String objectId);


    }

    private class SubscribeDataEnvelope extends BaseDataEnvelope<List<Subscribe>>{}

    //

    /** 获取用户所有的订阅标签
     * order	-createAt
     include	tag
     where	{"user":{"__type":"Pointer","className":"_User","objectId":"563c1d9xxxx749ea071246"}}
     limit	500
     */


    /**
     *
     * @param params
     * @return
     */
    public Observable<List<Subscribe>> getSubscribes(HashMap<String,String> params){
        return webService.getSubscribeList(params).map(new Func1<SubscribeDataEnvelope, List<Subscribe>>() {
            @Override
            public List<Subscribe> call(SubscribeDataEnvelope subscribeDataEnvelope) {
                return subscribeDataEnvelope.results;
            }
        });
    }


    public Observable<Boolean> unSubscribe(String objectId){
        return webService.unSubscribe(objectId)
                .map(new Func1<Object ,Boolean>() {
                    @Override
                    public Boolean call(Object o) {
                        return Boolean.TRUE;
                    }
                });

    }

}
