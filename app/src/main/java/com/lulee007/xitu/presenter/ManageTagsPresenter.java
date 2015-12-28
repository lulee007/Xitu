package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.view.IManageTagsView;

import java.util.HashMap;

/**
 * User: lulee007@live.com
 * Date: 2015-12-18
 * Time: 17:05
 */
public class ManageTagsPresenter extends XTBasePresenter {
    private IManageTagsView manageTagsView;


    public ManageTagsPresenter(IManageTagsView view) {
        super(null);
        manageTagsView = view;
    }

    @Override
    protected HashMap<String, String> buildRequestParams(String where, int skip) {
        return null;
    }

    @NonNull
    @Override
    protected HashMap<String, String> buildRequestParams(String where) {
        return null;
    }

    @Override
    public void loadNew() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }
}
