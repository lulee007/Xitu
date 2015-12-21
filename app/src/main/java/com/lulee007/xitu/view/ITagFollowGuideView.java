package com.lulee007.xitu.view;

import com.lulee007.xitu.base.IXTBaseView;
import com.lulee007.xitu.models.Tag;

public interface ITagFollowGuideView extends IXTBaseView<Tag> {

    void showConfirm();

    void onUnSubscribeTag(int position);

    void onUnSubscribeTagError();

    void onSubscribeTagError();

    void onSubscribeTag(String objectId, int position);
}
