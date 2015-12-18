package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.services.TagService;
import com.lulee007.xitu.view.IEntriesByTagView;

import java.util.HashMap;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-17
 * Time: 21:14
 */
public class EntriesByTagPresenter extends XTBasePresenter {

private TagService tagService;
    private IEntriesByTagView entriesByTagView;

    public EntriesByTagPresenter(IEntriesByTagView entriesByTagView){
        super(null);
        this.entriesByTagView=entriesByTagView;
        tagService=new TagService();
    }

    public void loadTag(String tag){
        HashMap<String ,String > params=new HashMap<>();
        params.put("where",String.format("{\"title\":\"%s\"}",tag));
        Subscription subscription= tagService.getTags(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Tag>>() {
                    @Override
                    public void call(List<Tag> items) {
                        if(items.size()>0){
                            entriesByTagView.setTagIcon(items.get(0));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.addNewError();
                    }
                });
        addSubscription(subscription);
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
