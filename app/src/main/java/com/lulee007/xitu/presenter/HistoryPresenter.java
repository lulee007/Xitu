package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.models.View;
import com.lulee007.xitu.services.ViewService;
import com.lulee007.xitu.util.AuthUserHelper;
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
 * Time: 10:28
 */
public class HistoryPresenter extends XTBasePresenter<IEntriesByTagFragmentView> {

    public final static int BY_HISTORY = 10;


    private ViewService viewService;


    public HistoryPresenter(IEntriesByTagFragmentView view) {
        super(view);
        viewService = new ViewService();
        pageOffset = 20;
    }

    @Override
    protected HashMap<String, String> buildRequestParams(String where, int skip) {
        /**
         * order	-createdAt
         include	entry,user,entry.user
         where	{"user":{"__type":"Pointer","className":"_User","objectId":"563c1d9xxxx749ea071246"}}
         limit	20
         */
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("order", "-createdAt");
        params.put("include", "entry,user,entry.user");
        params.put("where", where);
        params.put("limit", pageOffset + "");
        if (skip > 0) {
            params.put("skip", skip + "");
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
        String where = String.format("{\"user\":%s}", new Gson().toJson(AuthUserHelper.getInstance().getUser()));
        Subscription subscription = viewService.getViewedEntries(buildRequestParams(where))
                .flatMap(new Func1<List<View>, Observable<View>>() {
                    @Override
                    public Observable<View> call(List<View> views) {
                        if (views != null && views.size() > 0)
                            createdAt = views.get(0).getCreatedAt();

                        return Observable.from(views);
                    }
                })
                .map(new Func1<View, Entry>() {
                    @Override
                    public Entry call(View view) {

                        return view.getEntry();
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
        String where = String.format("{\"user\":%s,\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"%s\"}}}", new Gson().toJson(AuthUserHelper.getInstance().getUser()), createdAt);
        HashMap<String, String> params = buildRequestParams(where);
        Subscription subscription = viewService.getViewedEntries(params)
                .flatMap(new Func1<List<View>, Observable<View>>() {
                    @Override
                    public Observable<View> call(List<View> views) {
                        if (views != null && views.size() > 0)
                            createdAt = views.get(0).getCreatedAt();
                        return Observable.from(views);
                    }
                })
                .map(new Func1<View, Entry>() {
                    @Override
                    public Entry call(View view) {
                        return view.getEntry();
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
        String where = String.format("{\"user\":%s}", new Gson().toJson(AuthUserHelper.getInstance().getUser()));

        Subscription subscription = viewService.getViewedEntries(buildRequestParams(where, pageOffset * pageIndex))
                .flatMap(new Func1<List<View>, Observable<View>>() {
                    @Override
                    public Observable<View> call(List<View> views) {
                        return Observable.from(views);
                    }
                })
                .map(new Func1<View, Entry>() {
                    @Override
                    public Entry call(View view) {
                        return view.getEntry();
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
