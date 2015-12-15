package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.models.Subscribe;
import com.lulee007.xitu.services.EntryService;
import com.lulee007.xitu.services.SubscribeService;
import com.lulee007.xitu.view.IEntriesView;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-15
 * Time: 13:57
 */
public class TaggedEntriesPresenter extends XTBasePresenter<IEntriesView> {

    private EntryService entryService;
    private SubscribeService subscribeService;
    private String createdAt;

    public TaggedEntriesPresenter(IEntriesView view) {
        super(view);
        entryService = new EntryService();
        subscribeService = new SubscribeService();
        pageOffset = 30;
    }

    @Override
    protected HashMap<String, String> buildRequestParams(@NonNull String where, int skip) {
        HashMap<String, String> entryParams = new HashMap<>();
        entryParams.put("order", "-createdAt");
        entryParams.put("limit", pageOffset + "");
        entryParams.put("where", String.format("{\"tags\":{\"$in\":%s}}", where));
        entryParams.put("include", "user,user.installation");
        if (skip > 0) {
            entryParams.put("skip", skip + "");
        }
        return entryParams;
    }

    @NonNull
    @Override
    protected HashMap<String, String> buildRequestParams(String where) {
        return buildRequestParams(where, 0);
    }

    private HashMap<String, String> buildSubscribeListParams(HashMap userMap) {
        String userWhereString = "{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"563c1d9560b25749ea071246\"}";
        if (userMap != null) {
            userWhereString = new Gson().toJson(userMap);
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("limit", "500");
        params.put("order", "-createAt");
        params.put("include", "tag");
        params.put("where", String.format("{\"user\":%s}", userWhereString));
        return params;
    }


    public void loadNew() {

        Subscription subscription = getSubscribedTags()
                .flatMap(new Func1<List<HashMap>, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(List<HashMap> hashMaps) {
                        String tagWhereString = new Gson().toJson(hashMaps);
                        return entryService.getEntryList(buildRequestParams(tagWhereString));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        if (entries.size() == 0) {
                            mView.noData();
                            return;
                        }
                        pageIndex = 0;
                        createdAt = entries.get(0).getCreatedAt();
                        mView.addNew(entries);
                        if (entries.size() < pageOffset) {
                            mView.noMore();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.addNewError();
                        Logger.e(throwable, "loadNew error");
                    }
                });
        addSubscription(subscription);
    }

    @NonNull
    private Observable<List<HashMap>> getSubscribedTags() {
        return subscribeService.getSubscribes(buildSubscribeListParams(null))
                .flatMap(new Func1<List<Subscribe>, Observable<Subscribe>>() {
                    @Override
                    public Observable<Subscribe> call(List<Subscribe> subscribes) {
                        return Observable.from(subscribes);
                    }
                })
                .map(new Func1<Subscribe, HashMap>() {
                    @Override
                    public HashMap call(Subscribe subscribe) {
                        HashMap<String, String> tagMap = new HashMap<String, String>();
                        Subscribe.TagEntity tag = subscribe.getTag();
                        if (tag != null) {
                            tagMap.put("__type", tag.get__type());
                            tagMap.put("objectId", tag.getObjectId());
                            tagMap.put("className", tag.getClassName());
                            return tagMap;
                        } else {
                            return tagMap;
                        }
                    }
                })
                .toList();
    }

    @Override
    public void refresh() {
        Subscription su = getSubscribedTags()
                .flatMap(new Func1<List<HashMap>, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(List<HashMap> hashMaps) {
                        HashMap<String, String> params = buildRequestParams("");
                        JSONObject json = new JSONObject();
                        try {
                            json.put("tags", hashMaps);
                            json.put("createdAt", String.format("{\"$gt\":{\"__type\":\"Date\",\"iso\":\"%s\"}}", createdAt));
                            params.put("", json.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return entryService.getEntryList(params);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        if(entries.size()==0){
                            mView.refreshNoContent();
                            return;
                        }
                        mView.refresh(entries);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.refreshError();
                    }
                });
                addSubscription(su);
    }

    @Override
    public void loadMore() {
        Subscription subscription=getSubscribedTags()
                .flatMap(new Func1<List<HashMap>, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(List<HashMap> hashMaps) {

                        return entryService.getEntryList(buildRequestParams(new Gson().toJson(hashMaps),pageOffset*pageIndex));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        if(entries.size()==0){
                            mView.noMore();
                            return;
                        }
                        mView.addMore(entries);
                        if(entries.size()<pageOffset){
                            mView.noMore();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscription(subscription);
    }


}
