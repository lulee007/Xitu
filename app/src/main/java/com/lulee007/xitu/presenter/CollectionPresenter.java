package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Collection;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.services.CollectionService;
import com.lulee007.xitu.view.IEntriesByTagFragmentView;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-28
 * Time: 14:34
 */
public class CollectionPresenter extends XTBasePresenter<IEntriesByTagFragmentView> {

    public static final int BY_COLLECTION = 20;
    private CollectionService collectionService;
    private  String userId;

    public CollectionPresenter(IEntriesByTagFragmentView view, String userId) {
        super(view);
        this.userId = userId;
        collectionService = new CollectionService();
    }

    @Override
    protected HashMap<String, String> buildRequestParams(String where, int skip) {

        HashMap<String, String> params = new HashMap<>();
        params.put("limit", String.valueOf(pageOffset));
        params.put("order", "-createAt");
        params.put("include", "entry,entry.user");
        params.put("where", where);
        if (skip > 0) {
            params.put("skip", String.valueOf(skip));
        }
        return params;
    }

    @NonNull
    @Override
    protected HashMap<String, String> buildRequestParams(String where) {
        return buildRequestParams(where, 0);
    }

    @Override
    public void loadNew() {
        String where = String.format("{\"user\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"%s\"}}", userId);
        Subscription subscription = collectionService.getCollection(buildRequestParams(where))
                .flatMap(new Func1<List<Collection>, Observable<Collection>>() {
                    @Override
                    public Observable<Collection> call(List<Collection> collections) {
                        if (collections != null && collections.size() > 0) {
                            createdAt = collections.get(0).getCreatedAt();
                        }
                        return Observable.from(collections);
                    }
                })
                .map(new Func1<Collection, Entry>() {
                    @Override
                    public Entry call(Collection collection) {
                        return collection.getEntry();
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        if (entries.size() == 0) {
                            mView.noData();
                            return;
                        }
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
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void refresh() {
        String where = String.format("{\"user\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"%s\"},\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"%s\"}}}", userId,createdAt);
        HashMap<String, String> params = buildRequestParams(where);
        Subscription subscription = collectionService.getCollection(params)
                .flatMap(new Func1<List<Collection>, Observable<Collection>>() {
                    @Override
                    public Observable<Collection> call(List<Collection> collections) {
                        if (collections != null && collections.size() > 0) {
                            createdAt = collections.get(0).getCreatedAt();
                        }
                        return Observable.from(collections);
                    }
                })
                .map(new Func1<Collection, Entry>() {
                    @Override
                    public Entry call(Collection collection) {
                        return collection.getEntry();
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        if (entries.size() == 0) {
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
        addSubscription(subscription);
    }

    @Override
    public void loadMore() {
        String where = String.format("{\"user\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"%s\"}}", userId);

        Subscription subscription = collectionService.getCollection(buildRequestParams(where, pageOffset * pageIndex))
                .flatMap(new Func1<List<Collection>, Observable<Collection>>() {
                    @Override
                    public Observable<Collection> call(List<Collection> collections) {
                        return Observable.from(collections);
                    }
                })
                .map(new Func1<Collection, Entry>() {
                    @Override
                    public Entry call(Collection collection) {
                        return collection.getEntry();
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        onLoadMoreComplete(entries);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.addMoreError();
                    }
                });
        addSubscription(subscription);
    }
}
