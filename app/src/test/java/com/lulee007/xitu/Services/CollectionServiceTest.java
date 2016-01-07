package com.lulee007.xitu.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CollectionServiceTest {

    CollectionService collectionService = null;

    @Before
    public void setUp() throws Exception {
        collectionService = new CollectionService();
    }

    @After
    public void tearDown() throws Exception {
        collectionService = null;
    }

    @Test
    public void testUnSubscribeEntry() throws Exception {
        Boolean result= collectionService.unSubscribeEntry("567a23fxxxx960b2855b7d328a6f")
                .toBlocking().single();
        assertThat(result,equalTo(Boolean.TRUE));
    }
}