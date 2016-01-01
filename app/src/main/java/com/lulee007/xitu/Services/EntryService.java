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


    public EntryService() {
        super(EntryWebService.class);
    }

    protected interface EntryWebService {
        @GET("/classes/Entry")
        Observable<EntryDataEnvelope> getEntries(@QueryMap HashMap<String, String> params);
    }

    protected class EntryDataEnvelope extends BaseDataEnvelope<List<Entry>> {

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

    /**user subscribed
     * order	-createdAt
     include	user,user.installation
     where	{"tags":{"$in":[{"__type":"Pointer","className":"Tag","objectId":"5597a04de4b08a686ce56e63"},{"__type":"Pointer","className":"Tag","objectId":"5597838ae4b08a686ce23139"},{"__type":"Pointer","className":"Tag","objectId":"55964d83e4b08a686cc6b353"},{"__type":"Pointer","className":"Tag","objectId":"5597a05ae4b08a686ce56f6f"},{"__type":"Pointer","className":"Tag","objectId":"5597a3cae4b08a686ce5d0fb"},{"__type":"Pointer","className":"Tag","objectId":"559b2efbe4b0bd7d53736819"},{"__type":"Pointer","className":"Tag","objectId":"555e9abae4b00c57d9956122"},{"__type":"Pointer","className":"Tag","objectId":"5597a34fe4b08a686ce5c51c"},{"__type":"Pointer","className":"Tag","objectId":"5597a035e4b08a686ce56bd9"},{"__type":"Pointer","className":"Tag","objectId":"555e9abee4b00c57d9956152"},{"__type":"Pointer","className":"Tag","objectId":"55c1748160b28fd99e49ea68"},{"__type":"Pointer","className":"Tag","objectId":"555e9a77e4b00c57d9955d64"},{"__type":"Pointer","className":"Tag","objectId":"5598efd4e4b04fe7027b23a4"},{"__type":"Pointer","className":"Tag","objectId":"559a7207e4b08a686d25703e"},{"__type":"Pointer","className":"Tag","objectId":"559b3aa7e4b0d4d1b1c94472"},{"__type":"Pointer","className":"Tag","objectId":"5597a602e4b08a686ce608d2"},{"__type":"Pointer","className":"Tag","objectId":"555eadbbe4b00c57d99623cc"},{"__type":"Pointer","className":"Tag","objectId":"559a7227e4b08a686d25744f"},{"__type":"Pointer","className":"Tag","objectId":"555e9adae4b00c57d99562a4"},{"__type":"Pointer","className":"Tag","objectId":"555e99ffe4b00c57d99556aa"},{"__type":"Pointer","className":"Tag","objectId":"5597a063e4b08a686ce57030"},{"__type":"Pointer","className":"Tag","objectId":"5597a8d5e4b08a686ce64e50"},{"__type":"Pointer","className":"Tag","objectId":"55c174a100b0ee7fd6647a8d"},{"__type":"Pointer","className":"Tag","objectId":"5597a4b6e4b08a686ce5e860"},{"__type":"Pointer","className":"Tag","objectId":"55e7cc62ddb2dd0023accd0d"},{"__type":"Pointer","className":"Tag","objectId":"5597838ee4b08a686ce2319d"},{"__type":"Pointer","className":"Tag","objectId":"55979e42e4b08a686ce53bad"}]}}
     limit	30
     */

    /**
     * @param params
     * @return
     */
    public Observable<List<Entry>> getEntryList(HashMap<String, String> params) {
        return this.webService.getEntries(params)
                .map(new Func1<EntryDataEnvelope, List<Entry>>() {
                    @Override
                    public List<Entry> call(EntryDataEnvelope entryDataEnvelope) {
                        return entryDataEnvelope.results;
                    }
                });
    }


}


