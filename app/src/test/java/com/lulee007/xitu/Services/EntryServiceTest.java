package com.lulee007.xitu.Services;

import org.junit.Test;

import java.util.HashMap;

import rx.Observable;
import rx.functions.Func1;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class EntryServiceTest {

    @Test
    public void testGetEntryList() throws Exception {
        EntryService entryService = new EntryService();
        HashMap<String, String> params = new HashMap<>();
        params.put("order", "-createdAt");
        params.put("limit", "10");
        int count = entryService.getEntryList(params)

                .flatMap(new Func1<EntryService.EntryDataEnvelope, Observable<?>>() {
                    @Override
                    public Observable<?> call(EntryService.EntryDataEnvelope entryDataEnvlope) {
                        return Observable.from(entryDataEnvlope.results);
                    }
                }).count().toBlocking().single();
        assertThat(count, equalTo(10));
    }
}