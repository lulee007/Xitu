package com.lulee007.xitu.view;

import com.lulee007.xitu.models.Tag;

import java.util.HashMap;
import java.util.List;

public interface ITagFollowGuideView {
    void addMoreTags(List<Tag> tags);
    void addNewTags(HashMap<String,List<Tag>> tagsMap);

    void addNewError();
    void addMoreError();

    void noMore();
    void noData();
}
