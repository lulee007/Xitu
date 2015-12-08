package com.lulee007.xitu.Services;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class SubscribeServiceTest {

    @Test
    public void testGetSubscribes() throws Exception {
        SubscribeService subscribeService=new SubscribeService();
        HashMap<String,String> params=new HashMap<>();
        params.put("limit", "10");
        int count =subscribeService.getSubscribes(params).flatMap(new Func1<List<HashMap>, Observable<?>>() {
            @Override
            public Observable<?> call(List<HashMap> hashMaps) {
                return Observable.from(hashMaps);
            }
        }).count().toBlocking().single();

        assertThat(count,equalTo(10));


    }
}