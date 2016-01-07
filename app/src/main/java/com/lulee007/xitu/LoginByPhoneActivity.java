package com.lulee007.xitu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.models.LeanCloudError;
import com.lulee007.xitu.presenter.LoginByPhonePresenter;
import com.lulee007.xitu.presenter.RegisterByPhonePresenter;
import com.lulee007.xitu.util.ActivitiesHelper;
import com.lulee007.xitu.util.XTConstant;
import com.lulee007.xitu.view.ILoginByPhoneView;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class LoginByPhoneActivity extends XTBaseActivity implements ILoginByPhoneView {

    private Button loginButton;
    private LoginByPhonePresenter loginByPhonePresenter;
    private SweetAlertDialog sweetAlertDialog;
    private boolean needLoginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_phone);
        new MaterializeBuilder().withActivity(this).build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.login_by_phone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        needLoginResult = getIntent().getBooleanExtra(LoginOptionsActivity.INTENT_KEY_NEED_LOGIN_RESULT, false);

        final TextInputLayout phoneInputLayout = (TextInputLayout) findViewById(R.id.phone_InputLayout);
        final EditText phoneNumber = (EditText) findViewById(R.id.phone);
        final TextInputLayout pwdInputLayout = (TextInputLayout) findViewById(R.id.pwd_InputLayout);
        final EditText pwd = (EditText) findViewById(R.id.pwd);
        loginButton = (Button) findViewById(R.id.login);

        loginByPhonePresenter = new LoginByPhonePresenter(this);

        Observable<Boolean> phoneNumberOb = RxTextView.textChanges(phoneNumber).skip(1)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() == 11;
                    }
                })
                .distinctUntilChanged()
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        phoneInputLayout.setError(aBoolean ? "" : "手机号不正确");
                    }
                });


        Observable<Boolean> pwdOb = RxTextView.textChanges(pwd).skip(1)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() >= 6 && charSequence.length() < 20;
                    }
                })
                .distinctUntilChanged()
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        pwdInputLayout.setError(aBoolean ? "" : "密码不正确");

                    }
                });

        Subscription validSubscription = Observable
                .combineLatest(phoneNumberOb, pwdOb, new Func2<Boolean, Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean, Boolean aBoolean2) {
                        return aBoolean && aBoolean2;
                    }
                })
                .distinctUntilChanged()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        loginButton.setEnabled(aBoolean);
                    }
                });

        loginByPhonePresenter.addSubscription(validSubscription);

        RxView.clicks(findViewById(R.id.register_phone))
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (needLoginResult) {
                            Intent intent = new Intent(LoginByPhoneActivity.this, RegisterByPhoneActivity.class);
                            intent.putExtra(LoginOptionsActivity.INTENT_KEY_NEED_LOGIN_RESULT, true);
                            startActivityForResult(intent, XTConstant.ACTIVITY_REQUEST_CODE.LOGIN_BY_PHONE);
                        } else {
                            startActivity(RegisterByPhoneActivity.class);
                            ActivitiesHelper.instance().finish(LoginByPhoneActivity.class);
                        }
                    }
                });
        RxView.clicks(findViewById(R.id.skip_login))
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (needLoginResult) {
                            setResult(RESULT_FIRST_USER);
                            finish();
                        } else {
                            startActivity(MainActivity.class);
                            ActivitiesHelper.instance().finishAllBut(MainActivity.class);
                        }
                    }
                });
        RxView.clicks(findViewById(R.id.forgot_pwd))
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //TODO to find pwd page
                    }
                });
        RxView.clicks(loginButton)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        loginButton.setEnabled(false);
                        loginByPhonePresenter.login(phoneNumber.getText().toString(),
                                pwd.getText().toString());
                        sweetAlertDialog = new SweetAlertDialog(LoginByPhoneActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                        sweetAlertDialog.setTitleText("正在登陆中...");
                        sweetAlertDialog.setCancelable(false);
                        sweetAlertDialog.show();

                    }
                });

    }

    @Override
    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        sweetAlertDialog
                .setTitleText("登陆成功")
                .setConfirmText("确定")
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        Observable.timer(800, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (sweetAlertDialog.isShowing())
                            sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .delay(250, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (needLoginResult) {
                            setResult(RESULT_FIRST_USER);
                            finish();
                        }else {
                            startActivity(MainActivity.class);
                            ActivitiesHelper.instance().finishAllBut(MainActivity.class);
                        }
                    }
                });
    }

    @Override
    public void onLoginError(LeanCloudError error) {
        String toastText=null;
        switch (error.getCode()) {
            case 100:
                toastText = "服务器异常";
                break;
            case 124:
                toastText = "连接服务器超时";
                break;
            case 127:
                toastText = "手机号码无效";
                break;
            case 201:
                toastText = "密码错误";
                break;
            case 210:
                toastText="用户名和密码不匹配";
                break;
            case 211:
            case 213:
                toastText = "该手机没有被注册过";
                break;
            default:
                toastText = "未知错误";
                break;
        }

        sweetAlertDialog.setTitleText(toastText)
                .setConfirmText("确认")
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        loginButton.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_FIRST_USER){
            if(needLoginResult&& requestCode== XTConstant.ACTIVITY_REQUEST_CODE.LOGIN_BY_PHONE){
                setResult(RESULT_FIRST_USER);
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
