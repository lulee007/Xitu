package com.lulee007.xitu.services;

import com.lulee007.xitu.models.Tag;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TagServiceTest {

    @Test
    public void testGetTags() throws Exception {

        TagService tagService = new TagService();
        HashMap<String, String> params = new HashMap<>();
        params.put("order", "-entriesCount");
        params.put("limit", "10");
        params.put("where", "{\"hot\":true}");
        int count = tagService.getTags(params).flatMap(new Func1<List<Tag>, Observable<?>>() {
            @Override
            public Observable<?> call(List<Tag> tags) {
                return Observable.from(tags);
            }
        }).count().toBlocking().single();
        assertThat(count, equalTo(10));

    }

    @Test
    public void testGetTagsByType() {
        TagService tagService = new TagService();

        HashMap hotTagParams = new HashMap<String, String>();
        hotTagParams.put("order", "-entriesCount");
        hotTagParams.put("limit", "10");
        hotTagParams.put("where", "{\"hot\":true}");
        HashMap normalTagParams = new HashMap<String, String>();
        normalTagParams.put("order", "-entriesCount");
        normalTagParams.put("limit", "10");
        normalTagParams.put("where", "{\"hot\":false}");

        String count = Observable.zip(
                tagService.getTags(hotTagParams),
                tagService.getTags(normalTagParams),
                new Func2<List<Tag>, List<Tag>, List<Tag>>() {
                    @Override
                    public List<Tag> call(List<Tag> o, List<Tag> o2) {
                        List<Tag> tas = new ArrayList<Tag>();
                        tas.addAll(o);
                        tas.addAll(o2);
                        return tas;
                    }
                })
                .flatMap(new Func1<List<Tag>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<Tag> o) {
                        return Observable.from(o);
                    }
                }).count().toBlocking().single().toString();

        assertThat(count, equalTo("20"));

    }
}