package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.services.EntryService;
import com.lulee007.xitu.view.IEntriesView;

import java.util.HashMap;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-13
 * Time: 16:26
 */
public class RecommendedEntriesPresenter extends XTBasePresenter<IEntriesView> {

    private EntryService entryService;
    private double rankIndex;
    private String createdAt;

    public RecommendedEntriesPresenter(IEntriesView view) {
        super(view);
        pageOffset = 30;
        entryService = new EntryService();
    }


    @Override
    protected HashMap<String, String> buildRequestParams(String where, int skip) {
        HashMap<String, String> params = new HashMap<>();
        params.put("order", "-rankIndex");
        params.put("limit", pageOffset + "");
        params.put("include", "user,user.installation");
        if (where != null) {
            params.put("where", where);
        }
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

    public void loadNew() {
        HashMap<String, String> params = buildRequestParams(null);

        Subscription subscription = entryService.getEntryList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                               @Override
                               public void call(List<Entry> entries) {
                                   if (entries.size() == 0) {
                                       mView.noData();
                                       return;
                                   }
                                   Entry lastest = entries.get(0);
                                   rankIndex = lastest.getRankIndex();
                                   createdAt = lastest.getCreatedAt();
                                   mView.addNew(entries);
                                   if (entries.size() < pageOffset) {
                                       mView.noMore();
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.addNewError();
                            }
                        }
                );
        addSubscription(subscription);
    }

    public void refresh() {
        String where = String.format("{\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"%s\"}},\"rankIndex\":{\"$gt\":%f}}", createdAt, rankIndex);
        HashMap<String, String> params = buildRequestParams(where);
        params.remove("limit");
        Subscription subscription = entryService.getEntryList(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                               @Override
                               public void call(List<Entry> entries) {
                                   if (entries.size() == 0) {
                                       mView.refreshNoContent();
                                   } else {
                                       mView.refresh(entries);
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.refreshError();
                            }
                        }
                );
        addSubscription(subscription);

    }

    public void loadMore() {
        HashMap<String, String> params = buildRequestParams(null, pageIndex * pageOffset);
        Subscription subscription = entryService.getEntryList(params)
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
