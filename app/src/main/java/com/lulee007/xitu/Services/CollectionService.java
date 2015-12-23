package com.lulee007.xitu.services;

import com.lulee007.xitu.base.XTBaseService;
import com.lulee007.xitu.models.Collection;

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
 * Date: 2015-12-23
 * Time: 12:35
 */
public class CollectionService extends XTBaseService<CollectionService.CollectionWebService> {


    public CollectionService() {
        super(CollectionWebService.class);
    }

    protected interface CollectionWebService {
        @DELETE("/classes/Collection/{entryId}")
        Observable<Object> unSubscribeEntry(@Path("entryId") String entryId);

        @GET("/classes/Collection")
        Observable<CollectionDataEnvelop> getCollection(@QueryMap HashMap<String, String> params);
    }

    public Observable<Boolean> unSubscribeEntry(String entryId) {
        return webService.unSubscribeEntry(entryId)
                .map(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object o) {
                        if (o != null && o.toString().equals("{}"))
                            return Boolean.TRUE;
                        else
                            return Boolean.FALSE;
                    }
                });
    }

    public Observable<List<Collection>> getCollection(HashMap<String, String> params) {
        return webService.getCollection(params)
                .map(new Func1<CollectionDataEnvelop, List<Collection>>() {
                    @Override
                    public List<Collection> call(CollectionDataEnvelop baseDataEnvelope) {
                        return baseDataEnvelope.results;
                    }
                });
    }

    private class CollectionDataEnvelop extends BaseDataEnvelope<List<Collection>> {
    }


}
