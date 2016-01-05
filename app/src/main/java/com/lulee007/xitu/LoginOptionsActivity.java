package com.lulee007.xitu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.pwittchen.prefser.library.Prefser;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.presenter.RegisterByPhonePresenter;
import com.lulee007.xitu.util.XTConstant;
import com.mikepenz.materialize.MaterializeBuilder;
import com.orhanobut.logger.Logger;

public class LoginOptionsActivity extends XTBaseActivity implements View.OnClickListener {


    public static final String INTENT_KEY_NEED_LOGIN_RESULT = "need_login_result";
    private static final String PREFSER_KEY_LOGIN_OPTION_FIRST_TIME = "login_option_first_time";
    private Button phoneBtn;
    private Button skipBtn;
    private LinearLayout weiboBtn;
    private boolean needLoginResult;
    private Prefser prefser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);
        new MaterializeBuilder().withActivity(this).build();

        needLoginResult = getIntent().getBooleanExtra(INTENT_KEY_NEED_LOGIN_RESULT, false);

        phoneBtn = (Button) findViewById(R.id.phone_login);
        skipBtn = (Button) findViewById(R.id.skip_login);
        weiboBtn = (LinearLayout) findViewById(R.id.weibo_login);
        phoneBtn.setOnClickListener(this);
        skipBtn.setOnClickListener(this);
        weiboBtn.setOnClickListener(this);
        prefser = new Prefser(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.weibo_login:
                showToast("待开发...");
//                intent = new Intent(this, LoginByWeiboActivity.class);
//                intent.putExtra("showDialog", false);
//                this.startActivity(intent);
                break;
            case R.id.phone_login:
                if (isFirstLoad()) {
                    prefser.put(PREFSER_KEY_LOGIN_OPTION_FIRST_TIME, Boolean.FALSE);
                    if (!needLoginResult) {
                        startActivity(RegisterByPhoneActivity.class);
                    } else {
                        intent = new Intent(LoginOptionsActivity.this, RegisterByPhoneActivity.class);
                        intent.putExtra(INTENT_KEY_NEED_LOGIN_RESULT, true);
                        startActivityForResult(intent, XTConstant.ACTIVITY_REQUEST_CODE.LOGIN_BY_PHONE);
                    }
                } else {
                    if (!needLoginResult) {
                        startActivity(LoginByPhoneActivity.class);
                    } else {
                        intent = new Intent(LoginOptionsActivity.this, LoginByPhoneActivity.class);
                        intent.putExtra(INTENT_KEY_NEED_LOGIN_RESULT, true);
                        startActivityForResult(intent, XTConstant.ACTIVITY_REQUEST_CODE.LOGIN_BY_PHONE);
                    }
                }
                break;
            case R.id.skip_login:
                if(needLoginResult){
                    finish();
                }else {
                    startActivity(MainActivity.class);
                    Logger.d("点击了跳过按钮，跳转到主页");
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private boolean isFirstLoad() {
        return prefser.get(PREFSER_KEY_LOGIN_OPTION_FIRST_TIME, Boolean.class, Boolean.TRUE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_FIRST_USER) {
            if (needLoginResult && requestCode == XTConstant.ACTIVITY_REQUEST_CODE.LOGIN_BY_PHONE) {
                setResult(RESULT_FIRST_USER);
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
