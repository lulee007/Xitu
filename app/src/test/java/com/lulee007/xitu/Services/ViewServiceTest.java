package com.lulee007.xitu.services;

import com.lulee007.xitu.models.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ViewServiceTest {
    ViewService viewService = null;

    @Before
    public void setUp() throws Exception {
        viewService = new ViewService();
    }

    @After
    public void tearDown() throws Exception {
        viewService = null;
    }

    @Test
    public void testGetViewedEntries() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("order", "-createdAt");
        params.put("include", "entry,user,entry.user");
        params.put("where", "{\"user\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"563c1d9xxxx749ea071246\"}}");
        params.put("limit", "20");
        Integer count = viewService.getViewedEntries(params)
                .flatMap(new Func1<List<View>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<View> views) {
                        return Observable.from(views);
                    }
                })
                .count()
                .toBlocking()
                .single();
        assertThat(count,equalTo(20));
    }
}