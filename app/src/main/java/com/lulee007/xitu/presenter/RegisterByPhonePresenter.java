package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.services.CommonSaveService;
import com.lulee007.xitu.view.IRegisterByPhoneView;

import java.util.HashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-30
 * Time: 20:41
 */
public class RegisterByPhonePresenter extends XTBasePresenter{

    CommonSaveService commonSaveService;

    public RegisterByPhonePresenter(IRegisterByPhoneView view) {
        super(null);
        commonSaveService=new CommonSaveService();
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

    public void registerByPhone(String phoneNumber, String userName, String pwd) {
        Subscription subscription=commonSaveService.saveRegisterPhone(phoneNumber,userName,pwd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HashMap>() {
                               @Override
                               public void call(HashMap hashMap) {

                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        }
                );
        addSubscription(subscription);

    }
}
