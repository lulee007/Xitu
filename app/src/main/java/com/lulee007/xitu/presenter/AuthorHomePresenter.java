package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Author;
import com.lulee007.xitu.services.AuthorService;
import com.lulee007.xitu.view.IAuthorHomeView;

import java.util.HashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-22
 * Time: 23:04
 */
public class AuthorHomePresenter extends XTBasePresenter {

    private AuthorService authorService;
    private IAuthorHomeView authorHomeView;
    public AuthorHomePresenter(IAuthorHomeView authorHomeView){
        super(null);
        this.authorHomeView=authorHomeView;
        authorService=new AuthorService();
    }

    public void getAuthorInfo(String authorId){
       Subscription subscription= authorService.getAuthor(authorId)
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Action1<Author>() {
                              @Override
                              public void call(Author author) {
                                  authorHomeView.onGetAuthorInfoDone(author);
                              }
                          },
                       new Action1<Throwable>() {
                           @Override
                           public void call(Throwable throwable) {
                               authorHomeView.onGetAuthorInfoError();
                           }
                       }
               );
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
