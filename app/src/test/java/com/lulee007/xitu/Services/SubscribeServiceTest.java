package com.lulee007.xitu.services;

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

public class SubscribeServiceTest {

    SubscribeService subscribeService;


    @Test
    public void testGetSubscribes() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("limit", "500");
        params.put("order", "-createAt");
        params.put("include", "tag");
        params.put("where", "{\"user\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"563c1d9xxxx749ea071246\"}}");
        int count = subscribeService.getSubscribes(params)
                .flatMap(new Func1<List<Subscribe>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<Subscribe> subscribes) {
                        return Observable.from(subscribes);
                    }
                })
                .count().toBlocking().single();

        assertThat(count, equalTo(27));


    }

    @Before
    public void setUp() throws Exception {
        subscribeService = new SubscribeService();
    }

    @After
    public void tearDown() throws Exception {
        subscribeService = null;
    }



    @Test
    public void testUnSubscribe() throws Exception {
      Object o=  subscribeService.unSubscribe("566f798a60b20de5463fde89")
                .map(new Func1<Boolean, Object>() {
                    @Override
                    public Object call(Boolean aBoolean) {
                        return null;
                    }
                }).toBlocking().single();

        assertThat(o,equalTo(null));
    }
}