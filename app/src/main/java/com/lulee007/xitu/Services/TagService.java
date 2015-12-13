package com.lulee007.xitu.services;

import com.lulee007.xitu.base.XTBaseService;
import com.lulee007.xitu.models.Tag;

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
public class TagService extends XTBaseService<TagService.TagWebService> {


    public  TagService(){
        super(TagWebService.class);
    }

    protected   interface  TagWebService{

        @GET("/classes/Tag")
        Observable<TagDataEnvelope> getTags(@QueryMap HashMap<String,String> params);

    }

    private class  TagDataEnvelope extends BaseDataEnvelope<List<Tag>>{}

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



    public Observable<List<Tag>> getTags(HashMap<String,String> params){
        return webService.getTags(params).map(new Func1<TagDataEnvelope, List<Tag>>() {
            @Override
            public List<Tag> call(TagDataEnvelope tagDataEnvelope) {
                return tagDataEnvelope.results;
            }
        });
    }
}
