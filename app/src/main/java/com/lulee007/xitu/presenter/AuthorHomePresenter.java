package com.lulee007.xitu.presenter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Author;
import com.lulee007.xitu.services.AuthorService;
import com.lulee007.xitu.util.FastBlur;
import com.lulee007.xitu.util.HttpUtils;
import com.lulee007.xitu.view.IAuthorHomeView;

import java.util.HashMap;
import java.util.logging.Logger;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    public void loadUserBlurBackground(String url) {
        Subscription subscription = Observable.just(url)

                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        Bitmap bp= HttpUtils.getNetWorkBitmap(s);
                        try {
                          return FastBlur.doBlur(bp, 25, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        authorHomeView.onUserBlurBgDownloaded(bitmap);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        com.orhanobut.logger.Logger.e(throwable,"download image error");
                    }
                });
        addSubscription(subscription);
    }
}
