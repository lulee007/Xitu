package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Account;
import com.lulee007.xitu.services.CommonSaveService;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.view.IRegisterByPhoneView;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit.RetrofitError;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-30
 * Time: 20:41
 */
public class RegisterByPhonePresenter extends XTBasePresenter {

    CommonSaveService commonSaveService;
    IRegisterByPhoneView registerByPhoneView;

    public RegisterByPhonePresenter(IRegisterByPhoneView view) {
        super(null);
        registerByPhoneView = view;
        commonSaveService = new CommonSaveService();
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
        Subscription subscription = commonSaveService.saveRegisterPhone(phoneNumber, userName, pwd)
                .delaySubscription(600, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Account>() {
                            @Override
                            public void call(Account account) {
                                AuthUserHelper.getInstance().saveUserDetail(account);
                                String phone = account.getMobilePhoneNumber();
                                registerByPhoneView.onRegisterSuccess(phone);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                registerByPhoneView.onRegisterError(parseError((RetrofitError) throwable));
                            }
                        }
                );
        addSubscription(subscription);
    }
}
