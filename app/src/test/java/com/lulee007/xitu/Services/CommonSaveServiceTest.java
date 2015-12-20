package com.lulee007.xitu.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rx.functions.Func1;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class CommonSaveServiceTest {

    private CommonSaveService commonSaveService;

    @Test
    public void testSaveTag() throws Exception {
       Object o= commonSaveService.saveSubscription("5597a04de4b08a686ce56e63", "Subscribe")
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

}