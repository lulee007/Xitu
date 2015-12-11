package com.lulee007.xitu.base;

import com.orhanobut.logger.Logger;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/** 1、every subscription must be added in mCompositeSubscription
 *  2、in Activity onDestroy must call unSubscribeAll
 * User: lulee007@live.com
 * Date: 2015-12-11
 * Time: 11:27
 */
public abstract class XTBasePresenter<T1> {
    protected CompositeSubscription mCompositeSubscription;
    protected T1 mView;
    public XTBasePresenter(T1 view){
        mCompositeSubscription=new CompositeSubscription();
        mView=view;
    }

    protected void addSubscription(Subscription subscription){
        if(subscription!=null){
            mCompositeSubscription.add(subscription);
        }
    }

    public  void unSubscribeAll(){
        mView = null;
        mCompositeSubscription.unsubscribe();
        Logger.d("unsubscribe all");
    }
}
