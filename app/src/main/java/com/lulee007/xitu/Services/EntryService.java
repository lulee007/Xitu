package com.lulee007.xitu.services;

import com.lulee007.xitu.base.XTBaseService;
import com.lulee007.xitu.models.Entry;

import java.util.HashMap;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * User: lulee007@live.com
 * Date: 2015-12-07
 * Time: 21:28
 */
public class EntryService extends XTBaseService<EntryService.EntryWebService> {


    private EntryWebService entryWebService;


    public EntryService() {
        super(EntryWebService.class);
        entryWebService = restAdapter.create(EntryWebService.class);
    }

    protected interface EntryWebService {
        @GET("/Entry")
        Observable<EntryDataEnvelope> getEntries(@QueryMap HashMap<String, String> params);
    }

    public class EntryDataEnvelope extends BaseDataEnvelope<List<Entry>> {

    }


    public Observable<EntryDataEnvelope> getEntryList(HashMap<String ,String > params) {
        return this.entryWebService.getEntries(params);
    }




}


