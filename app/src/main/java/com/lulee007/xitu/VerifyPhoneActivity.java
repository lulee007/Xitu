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
import com.lulee007.xitu.presenter.VerifyPhonePresenter;
import com.lulee007.xitu.util.ActivitiesHelper;
import com.lulee007.xitu.util.XTConstant;
import com.lulee007.xitu.view.IVerifyPhoneView;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class VerifyPhoneActivity extends XTBaseActivity implements IVerifyPhoneView {

    public static final String INTENT_KEY_PHONE_NUMBER = "phone_number";
    private VerifyPhonePresenter verifyPhonePresenter;
    private SweetAlertDialog sweetAlertDialog = null;
    private TextInputLayout verifyTextInputLayout;
    private EditText verifyCodeEditText;
    private Button verifyButton;
    private boolean needLoginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        verifyTextInputLayout = (TextInputLayout) findViewById(R.id.verify_InputLayout);
        verifyCodeEditText = (EditText) findViewById(R.id.verify_code);
        verifyButton = (Button) findViewById(R.id.verify);

        needLoginResult = getIntent().getBooleanExtra(LoginOptionsActivity.INTENT_KEY_NEED_LOGIN_RESULT, false);

        new MaterializeBuilder().withActivity(this).build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.login_by_phone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        verifyPhonePresenter = new VerifyPhonePresenter(this);

        verifyButton.setEnabled(false);

        Subscription verifySubscription = RxTextView.textChanges(verifyCodeEditText).skip(1)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() == 6;
                    }
                })
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        verifyTextInputLayout.setError(aBoolean ? "" : "请输入六位验证码");
                    }
                })
                .distinctUntilChanged()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        verifyButton.setEnabled(aBoolean);
                    }
                });
        verifyPhonePresenter.addSubscription(verifySubscription);

        RxView.clicks(verifyButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        sweetAlertDialog = new SweetAlertDialog(VerifyPhoneActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                        sweetAlertDialog.setTitleText("正在验证中...");
                        sweetAlertDialog.setCancelable(false);
                        sweetAlertDialog.show();
                        verifyPhonePresenter.verifyPhoneCode(Integer.parseInt(verifyCodeEditText.getText().toString()));
                    }
                });

    }

    @Override
    public void onVerifySuccess() {
        sweetAlertDialog.setTitleText("验证成功!")
                .setConfirmText("完成")
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        Observable.timer(800, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (sweetAlertDialog.isShowing())
                            sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .delay(250,TimeUnit.MILLISECONDS,AndroidSchedulers.mainThread())
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
    public void onVerifyError(LeanCloudError result) {
        sweetAlertDialog.setTitleText(getResources().getString(R.string.verify_error))
                .setConfirmText("确定")
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);

    }


}
