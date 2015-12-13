package com.lulee007.xitu.services;

import com.lulee007.xitu.base.XTBaseService;

import java.util.HashMap;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;
import rx.functions.Func1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-08
 * Time: 09:44
 */
public class SubscribeService extends XTBaseService<SubscribeService.SubscribeWebService> {

    private SubscribeWebService tagWebService;

    public SubscribeService(){
        super(SubscribeWebService.class);
        tagWebService=restAdapter.create(SubscribeWebService.class);
    }

    protected   interface SubscribeWebService {

        @GET("/Subscribe")
        Observable<SubscribeDataEnvelope> getTags(@QueryMap HashMap<String, String> params);

    }

    private class SubscribeDataEnvelope extends BaseDataEnvelope<List<HashMap>>{}

    //
    /** 推荐（hot）
     * order	-entriesCount
     * where	{"hot":true}
     * limit	100
     */
    /** 其他（not hot）
     * order	-entriesCount
     * where	{"hot":{"$ne":true}}
     * limit	100
     */



    public Observable<List<HashMap>> getSubscribes(HashMap<String,String> params){
        return tagWebService.getTags(params).map(new Func1<SubscribeDataEnvelope, List<HashMap>>() {
            @Override
            public List<HashMap> call(SubscribeDataEnvelope tagDataEnvelope) {
                return tagDataEnvelope.results;
            }
        });
    }
}
