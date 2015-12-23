package com.lulee007.xitu.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rx.functions.Action1;
import rx.functions.Func1;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class CommonSaveServiceTest {

    private CommonSaveService commonSaveService;

    @Test
    public void saveSubscription() throws Exception {
       Object o= commonSaveService.saveSubscription("5597a04de4b08a686ce56e63")
                .map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {
                        return o;
                    }
                }).toBlocking().single();
        assertThat(o.toString(),equalTo("{}"));
    }


    @Before
    public void setUp() throws Exception {
        commonSaveService = new CommonSaveService();
    }

    @After
    public void tearDown() throws Exception {
        commonSaveService = null;
    }

    @Test
    public void testSaveCollectEntry() throws Exception {
        Object o= commonSaveService.saveCollectEntry("567a1c8f00b06f9f616f5134")
                .map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {
                        return o;
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("on error");
                        throwable.printStackTrace();
                        fail();
                    }
                })
                .toBlocking().single();
        assertThat(o.toString(),not(equalTo("{}")));
    }
}