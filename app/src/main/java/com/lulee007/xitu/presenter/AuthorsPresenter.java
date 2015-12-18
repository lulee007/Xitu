package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Author;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.services.AuthorService;
import com.lulee007.xitu.services.TagService;
import com.lulee007.xitu.view.IAuthorsView;
import com.lulee007.xitu.view.ITagFollowGuideView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * User: lulee007@live.com
 * Date: 2015-12-09
 * Time: 14:28
 */
public class AuthorsPresenter extends XTBasePresenter<IAuthorsView> {

    private AuthorService authorService;

    public AuthorsPresenter(IAuthorsView view) {
        super(view);
        authorService=new AuthorService();
        pageOffset=50;

    }

    @Override
    protected HashMap<String, String> buildRequestParams(String where, int skip) {
        HashMap<String,String> params=new HashMap<>();
        params.put("order","-totalHotIndex");
        params.put("limit", pageOffset + "");
        params.put("where",where);
        if(skip>0){
            params.put("skip",skip+"");
        }
        return params;
    }

    @NonNull
    @Override
    protected HashMap<String, String> buildRequestParams(String where) {
        return buildRequestParams(where,0);
    }

    public void loadNew(){
        Subscription subscription= authorService.getAuthors(buildRequestParams("{\"role\":\"editor\"}"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Author>>() {
                    @Override
                    public void call(List<Author> authors) {
                        if(authors==null)
                            return;
                        if(authors.size()==0){
                            mView.noData();
                            return;
                        }

                        mView.addNew(authors);
                        if(authors.size()<pageOffset){
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

    }

    public void loadMore(){
        Subscription subscription=authorService.getAuthors(buildRequestParams("{\"role\":\"editor\"}",pageIndex*pageOffset))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Author>>() {
                    @Override
                    public void call(List<Author> authors) {
                        onLoadMoreComplete(authors);
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
