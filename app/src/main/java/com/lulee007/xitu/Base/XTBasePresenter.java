package com.lulee007.xitu.base;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 1、every subscription must be added in mCompositeSubscription
 * 2、in Activity onDestroy must call unSubscribeAll
 *
 * User: lulee007@live.com
 * Date: 2015-12-11
 * Time: 11:27
 */
public abstract class XTBasePresenter<T1 extends IXTBaseView> {

    protected int pageIndex = 1;
    protected  int pageOffset = 100;
    protected  String createdAt;
    protected CompositeSubscription mCompositeSubscription;
    public T1 mView;

    public XTBasePresenter(T1 view) {
        mCompositeSubscription = new CompositeSubscription();
        mView = view;
    }


    protected void addSubscription(Subscription subscription) {
        if (subscription != null) {
            mCompositeSubscription.add(subscription);
        }
    }

    public void unSubscribeAll() {
        mView = null;
        mCompositeSubscription.unsubscribe();
        Logger.d("unsubscribe all");
    }

    protected void onLoadMoreComplete(List items){
        if (items.size() == 0) {
            mView.noMore();
            return;
        }
        mView.addMore(items);
        pageIndex++;
        if (items.size() < pageOffset) {
            mView.noMore();
        }
    }

    /**
     * 构建查询参数
     *
     * @param where {hot:true} etc.
     * @param skip  skip count
     * @return HashMap
     */
    abstract protected HashMap<String, String> buildRequestParams(String where, int skip);

    /**
     * 构建查询参数
     *
     * @param where {hot:true} etc.
     * @return HashMap
     */
    @NonNull
    abstract protected HashMap<String, String> buildRequestParams(String where);


    abstract public  void loadNew();

    abstract public void refresh();

    abstract public  void loadMore();
}
