package com.lulee007.xitu.services;

import com.lulee007.xitu.base.XTBaseService;
import com.lulee007.xitu.models.Entry;

import java.util.HashMap;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;
import rx.functions.Func1;

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
        @GET("/classes/Entry")
        Observable<EntryDataEnvelope> getEntries(@QueryMap HashMap<String, String> params);
    }

    public class EntryDataEnvelope extends BaseDataEnvelope<List<Entry>> {

    }

    /** init
     * order	-rankIndex
     include	user,user.installation
     limit	30
     */

    /** load more
     * order	-rankIndex
     include	user,user.installation
     skip	30
     limit	30
     */

    /**refresh
     * order	-rankIndex
     include	user
     where	{"createdAt":{"$gt":{"__type":"Date","iso":"2015-12-13T06:11:52.175Z"}},"rankIndex":{"$gt":10.242118952816718}}
     */

    /**
     *
     * @param params
     * @return
     */
    public Observable<List<Entry>> getEntryList(HashMap<String ,String > params) {
        return this.entryWebService.getEntries(params)
                .map(new Func1<EntryDataEnvelope, List<Entry>>() {
                    @Override
                    public List<Entry> call(EntryDataEnvelope entryDataEnvelope) {
                        return entryDataEnvelope.results;
                    }
                });
    }




}


