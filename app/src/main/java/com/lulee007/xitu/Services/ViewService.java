package com.lulee007.xitu.services;

import com.lulee007.xitu.base.XTBaseService;
import com.lulee007.xitu.models.View;

import java.util.HashMap;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;
import rx.functions.Func1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-28
 * Time: 10:34
 */
public class ViewService extends XTBaseService<ViewService.ViewWebService> {


    public ViewService() {
        super(ViewWebService.class);
    }

    protected interface ViewWebService {
        @GET("/classes/View")
        Observable<ViewDataEnvelope> getViewedEntries(@QueryMap HashMap<String, String> params);
    }


    protected class ViewDataEnvelope extends BaseDataEnvelope<List<View>> {

    }

    public Observable<List<View>> getViewedEntries(HashMap<String, String> params) {
        return webService.getViewedEntries(params)
                .map(new Func1<ViewDataEnvelope, List<View>>() {
                    @Override
                    public List<View> call(ViewDataEnvelope viewDataEnvelope) {
                        return viewDataEnvelope.results;
                    }
                });
    }


}
