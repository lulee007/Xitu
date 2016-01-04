package com.lulee007.xitu.view;

import com.lulee007.xitu.base.IXTBaseView;
import com.lulee007.xitu.models.Tag;

public interface ITagWithUserStatsView  extends IXTBaseView<Tag>{
    void showConfirm();

    void onUnSubscribeTagError();

    void onUnSubscribeTag(int position);

    void onSubscribeTagError();

    void onSubscribeTag(String objectId, int position);

    void showNeedLoginDialog();
}
