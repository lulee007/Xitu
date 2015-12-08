package com.lulee007.xitu.services;

import com.lulee007.xitu.models.Tag;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class TagServiceTest {

    @Test
    public void testGetTags() throws Exception {

        TagService tagService=new TagService();
        HashMap<String, String> params = new HashMap<>();
        params.put("order", "-entriesCount");
        params.put("limit", "10");
        params.put("where", "{\"hot\":true}");
        int count =tagService.getTags(params).flatMap(new Func1<List<Tag>, Observable<?>>() {
            @Override
            public Observable<?> call(List<Tag> tags) {
                return Observable.from(tags);
            }
        }).count().toBlocking().single();
        assertThat(count, equalTo(10));

    }
}