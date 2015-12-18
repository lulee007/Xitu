package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.services.EntryService;
import com.lulee007.xitu.view.IEntriesByTagFragmentView;

import java.util.HashMap;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-17
 * Time: 16:29
 */
public class EntriesByTagFragmentPresenter extends XTBasePresenter<IEntriesByTagFragmentView> {

    private EntryService entryService;
    private String orderBy;
    private String  where=null;
    private double rankIndex=0.0;

    public EntriesByTagFragmentPresenter(IEntriesByTagFragmentView entriesByTagFragmentView) {
        super(entriesByTagFragmentView);
        entryService = new EntryService();
        pageOffset = 20;
    }




    /**
     * include	user,user.installation
     limit	20
     where	{"tagsTitleArray":"工具资源"}
     order	-rankIndex
     */
    /**
     * @param where {hot:true} etc.
     * @param skip  skip count
     * @return
     */
    @Override
    protected HashMap<String, String> buildRequestParams(String where, int skip) {
        HashMap<String, String> params = new HashMap<>();
        params.put("include", "user,user.installation");
        params.put("limit", pageOffset + "");
        params.put("where", where);
        if (orderBy == null || orderBy.trim().isEmpty()) {
            throw new IllegalArgumentException("orderBy cannot be null,use setOrderBy() set");
        }
        params.put("order", orderBy);
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

    private  HashMap<String, String> buildRequestParams(){
        if(where==null || where.trim().isEmpty()){
            throw  new IllegalArgumentException("where cannot be null,use setWhere() set");
        }
        return buildRequestParams(String.format("{\"tagsTitleArray\":\"%s\"}",where), 0);
    }

    @Override
    public void loadNew() {
         Subscription subscription= entryService.getEntryList(buildRequestParams())
                   .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<Entry>>() {
                        @Override
                        public void call(List<Entry> entries) {
                            if(entries.size()==0){
                                mView.noData();
                                return;
                            }
                            createdAt=entries.get(0).getCreatedAt();
                            rankIndex=entries.get(0).getRankIndex();
                            mView.addNew(entries);
                            if(entries.size()<pageOffset){
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
        String where=String.format("{\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"%s\"}},\"rankIndex\":{\"$gt\":%f},\"tagsTitleArray\":\"%s\"}",createdAt,rankIndex,this.where);
        HashMap<String ,String > params=buildRequestParams(where);
        Subscription subscription= entryService.getEntryList(params)
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
        addSubscription(subscription);
    }

    @Override
    public void loadMore() {
        Subscription subscription= entryService.getEntryList(buildRequestParams(String.format("{\"tagsTitleArray\":\"%s\"}",where),pageOffset*pageIndex))
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

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setWhere(String where) {
        this.where = where;
    }
}
