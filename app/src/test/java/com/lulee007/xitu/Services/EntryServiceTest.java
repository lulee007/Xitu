package com.lulee007.xitu.services;

import com.google.gson.Gson;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.models.Subscribe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class EntryServiceTest {

    private EntryService entryService;

    @Before
    public void setUp() throws Exception {
        entryService = new EntryService();
    }

    @After
    public void tearDown() throws Exception {
        entryService = null;
    }

    @Test
    public void testGetEntryList() throws Exception {
        refresh();
        getUserSubscribedEntries();

    }

    private void refresh() {
        HashMap<String, String> params = new HashMap<>();
        params.put("order", "-createdAt");
        params.put("limit", "10");
        int count = entryService.getEntryList(params)

                .flatMap(new Func1<List<Entry>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<Entry> entries) {
                        return Observable.from(entries);
                    }
                })
                .count().toBlocking().single();
        assertThat(count, equalTo(10));
    }

    private void getUserSubscribedEntries() {
        HashMap<String, String> params = new HashMap<>();
        params.put("limit", "500");
        params.put("order", "-createAt");
        params.put("include", "tag");
        params.put("where", "{\"user\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"563c1d9xxxx749ea071246\"}}");

        SubscribeService subscribeService = new SubscribeService();

        final HashMap<String, String> entryParams = new HashMap<>();
        entryParams.put("order", "-createdAt");
        entryParams.put("limit", "30");
//        entryParams.put("where","{\"tags\":{\"$in\":[{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597a04de4b08a686ce56e63\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597838ae4b08a686ce23139\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"55964d83e4b08a686cc6b353\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597a05ae4b08a686ce56f6f\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597a3cae4b08a686ce5d0fb\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"559b2efbe4b0bd7d53736819\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"555e9abae4b00c57d9956122\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597a34fe4b08a686ce5c51c\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597a035e4b08a686ce56bd9\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"555e9abee4b00c57d9956152\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"55c1748160b28fd99e49ea68\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"555e9a77e4b00c57d9955d64\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5598efd4e4b04fe7027b23a4\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"559a7207e4b08a686d25703e\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"559b3aa7e4b0d4d1b1c94472\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597a602e4b08a686ce608d2\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"555eadbbe4b00c57d99623cc\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"559a7227e4b08a686d25744f\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"555e9adae4b00c57d99562a4\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"555e99ffe4b00c57d99556aa\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597a063e4b08a686ce57030\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597a8d5e4b08a686ce64e50\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"55c174a100b0ee7fd6647a8d\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597a4b6e4b08a686ce5e860\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"55e7cc62ddb2dd0023accd0d\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"5597838ee4b08a686ce2319d\"},{\"__type\":\"Pointer\",\"className\":\"Tag\",\"objectId\":\"55979e42e4b08a686ce53bad\"}]}}");
        entryParams.put("include", "user,user.installation");

       int count = subscribeService.getSubscribes(params)
                .flatMap(new Func1<List<Subscribe>, Observable<Subscribe>>() {
                    @Override
                    public Observable<Subscribe> call(List<Subscribe> subscribes) {
                        return Observable.from(subscribes);
                    }
                })
                .map(new Func1<Subscribe, HashMap>() {
                    @Override
                    public HashMap call(Subscribe subscribe) {
                        HashMap<String ,String > hashMap=new HashMap<String, String>();
                        hashMap.put("__type","Pointer");
                        hashMap.put("objectId",subscribe.getTag().getObjectId());
                        hashMap.put("className","Tag");
                        return hashMap;
                    }
                })
                .toList()
                .flatMap(new Func1<List<HashMap>, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(List<HashMap> hashMaps) {
                        String where = new Gson().toJson(hashMaps);
                        entryParams.put("where", String.format("{\"tags\":{\"$in\":%s}}", where));
                        return  entryService.getEntryList(entryParams);
                    }
                })
                .flatMap(new Func1<List<Entry>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<Entry> entries) {
                        return Observable.from(entries);
                    }
                })
                .count()
                .toBlocking()
                .first();

        assertThat(count, equalTo(30));

    }
}