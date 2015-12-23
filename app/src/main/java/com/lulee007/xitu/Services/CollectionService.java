package com.lulee007.xitu.services;

import com.lulee007.xitu.base.XTBaseService;

import retrofit.http.DELETE;
import retrofit.http.Path;
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


}
