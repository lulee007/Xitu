package com.lulee007.xitu.Services;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.lulee007.xitu.Base.XTBaseService;
import com.lulee007.xitu.Models.Entry;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-07
 * Time: 21:28
 */
public class EntryService extends XTBaseService {


    private EntryWebService entryWebService;


    public EntryService() {
        super();
        entryWebService = restAdapter.create(EntryWebService.class);
    }

    public interface EntryWebService {
        @GET("/Entry")
        Observable<EntryDataEnvelope> getEntries(@QueryMap HashMap<String, String> params);
    }

    public class EntryDataEnvelope extends BaseDataEnvelope<List<Entry>> {

    }


    public Observable<EntryDataEnvelope> getEntryList(HashMap<String ,String > params) {
        return this.entryWebService.getEntries(params);
    }




}


