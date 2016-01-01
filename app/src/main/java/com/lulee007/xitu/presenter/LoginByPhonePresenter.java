package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Account;
import com.lulee007.xitu.services.AccountService;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.view.ILoginByPhoneView;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit.RetrofitError;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2016-01-01
 * Time: 16:49
 */
public class LoginByPhonePresenter extends XTBasePresenter {

    private ILoginByPhoneView loginByPhoneView;
    private AccountService accountService;

    public LoginByPhonePresenter(ILoginByPhoneView view) {
        super(null);
        loginByPhoneView = view;
        accountService = new AccountService();
    }

    public void login(final String phone, String pwd) {
        Subscription subscription = accountService.login(phone, pwd)
                .delay(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Account>() {
                            @Override
                            public void call(Account s) {
                                AuthUserHelper.getInstance().saveUserDetail(s);
                                loginByPhoneView.onLoginSuccess();
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loginByPhoneView.onLoginError(parseError((RetrofitError) throwable));
                            }
                        });
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
