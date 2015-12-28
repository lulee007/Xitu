package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.services.EntryService;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.view.IEntriesByTagFragmentView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-17
 * Time: 16:29
 */
public class ListEntriesFragmentPresenter extends XTBasePresenter<IEntriesByTagFragmentView> {

    public final static int BY_RECOMMENDED = 0;
    public final static int BY_TAG_HOT = 1;
    public final static int BY_TAG_LATEST = 2;
    public final static int BY_USER = 3;

    private EntryService entryService;
    private String orderBy;
    private double rankIndex = 0.0;
    private int requestType = BY_RECOMMENDED;
    private Map<String, String> whereMap;
    private boolean isRefresh = false;

    public ListEntriesFragmentPresenter(IEntriesByTagFragmentView entriesByTagFragmentView, int type, Map<String, String> whereMap) {
        super(entriesByTagFragmentView);
        this.whereMap = whereMap;
        entryService = new EntryService();
        pageOffset = 20;
        requestType = type;
    }


    /**
     * include	user,user.installation
     limit	20
     tagTitle	{"tagsTitleArray":"工具资源"}
     order	-rankIndex
     */
    /**
     * @param where {hot:true} etc.
     * @param skip  skip count
     * @return
     */
    @Override
    protected HashMap<String, String> buildRequestParams(String where, int skip) {
        String myWhere = null;
        HashMap<String, String> params = new HashMap<>();
        params.put("include", "user,user.installation");

        switch (requestType) {
            case BY_RECOMMENDED:
                pageOffset = 30;
                orderBy = "-rankIndex";
                if (isRefresh) {
                    myWhere = String.format("{\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"%s\"}},\"rankIndex\":{\"$gt\":%f}}",
                            createdAt,
                            rankIndex
                    );
                } else {
                    myWhere = null;
                }
                break;
            case BY_TAG_HOT:
                pageOffset = 20;
                orderBy = "-rankIndex";
                if (isRefresh) {
                    myWhere = String.format("{\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"%s\"}},\"rankIndex\":{\"$gt\":%f},\"tagsTitleArray\":\"%s\"}",
                            createdAt,
                            rankIndex,
                            whereMap.get("tagsTitleArray"));
                } else {
                    myWhere = String.format("{\"tagsTitleArray\":\"%s\"}", whereMap.get("tagsTitleArray"));
                }
                break;
            case BY_TAG_LATEST:
                pageOffset = 20;
                orderBy = "-createdAt";
                if (isRefresh) {
                    myWhere = String.format("{\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"%s\"}},\"tagsTitleArray\":\"%s\"}",
                            createdAt,
                            whereMap.get("tagsTitleArray"));
                } else {
                    myWhere = String.format("{\"tagsTitleArray\":\"%s\"}", whereMap.get("tagsTitleArray"));
                }
                break;
            case BY_USER:
                pageOffset = 10;
                orderBy = "-createdAt";
                if (isRefresh) {
                    myWhere = String.format("{\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"%s\"}},\"user\":%s}",
                            createdAt,
                            new Gson().toJson(AuthUserHelper.getInstance().getUser()));
                } else {
                    myWhere = String.format("{\"user\":{\"__type\":\"Pointer\",\"objectId\":\"%s\",\"className\":\"_User\"}}\n", whereMap.get("authorId"));
                }
                break;
        }

        params.put("limit", pageOffset + "");
        params.put("order", orderBy);
        if (myWhere != null)
            params.put("where", myWhere);
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

    private HashMap<String, String> buildRequestParams() {

        return buildRequestParams(null, 0);
    }

    @Override
    public void loadNew() {
        isRefresh = false;
        Subscription subscription = entryService.getEntryList(buildRequestParams())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        if (entries.size() == 0) {
                            mView.noData();
                            return;
                        }
                        createdAt = entries.get(0).getCreatedAt();
                        rankIndex = entries.get(0).getRankIndex();
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
        isRefresh = true;
        Subscription subscription = entryService.getEntryList(buildRequestParams())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        if (entries.size() == 0) {
                            mView.refreshNoContent();
                            return;
                        }
                        createdAt = entries.get(0).getCreatedAt();
                        rankIndex = entries.get(0).getRankIndex();

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
        isRefresh = false;
        Subscription subscription = entryService.getEntryList(buildRequestParams(null, pageOffset * pageIndex))
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
