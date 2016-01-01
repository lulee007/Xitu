package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.services.AccountService;
import com.lulee007.xitu.view.IVerifyPhoneView;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit.RetrofitError;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-31
 * Time: 13:55
 */
public class VerifyPhonePresenter extends XTBasePresenter {

    private AccountService accountService;
    private IVerifyPhoneView verifyPhoneView;

    public VerifyPhonePresenter(IVerifyPhoneView view) {
        super(null);
        verifyPhoneView = view;
        accountService = new AccountService();
    }

    public void verifyPhoneCode(int code) {
        Subscription subscription = accountService.verifyPhone(code)
                .delaySubscription(600, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<HashMap>() {
                            @Override
                            public void call(HashMap hashMap) {
                                verifyPhoneView.onVerifySuccess();
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                verifyPhoneView.onVerifyError(parseError((RetrofitError) throwable));
                            }
                        }
                );
        addSubscription(subscription);
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
