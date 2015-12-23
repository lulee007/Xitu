package com.lulee007.xitu.view;

import com.lulee007.xitu.base.IXTBaseView;
import com.lulee007.xitu.models.Entry;

import java.util.List;

/**
 * User: lulee007@live.com
 * Date: 2015-12-13
 * Time: 16:30
 */
public interface IEntriesView extends IXTBaseView<Entry> {

    void onCollected(int position);
    void onCollectError();

    void onUnCollect(int position);
    void onUnCollectError();

}
