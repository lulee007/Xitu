package com.lulee007.xitu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.lulee007.xitu.base.XTBaseActivity;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialize.MaterializeBuilder;
import com.orhanobut.logger.Logger;

public class LoginOptionsActivity extends XTBaseActivity implements View.OnClickListener{


    private Button phoneBtn;
    private Button skipBtn;
    private LinearLayout weiboBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);
        new MaterializeBuilder().withActivity(this).build();

        phoneBtn=(Button)findViewById(R.id.phone_login);
        skipBtn=(Button)findViewById(R.id.skip_login);
        weiboBtn=(LinearLayout)findViewById(R.id.weibo_login);
        phoneBtn.setOnClickListener(this);
        skipBtn.setOnClickListener(this);
        weiboBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.weibo_login:
                Intent intent = new Intent(this, LoginByWeiboActivity.class);
                intent.putExtra("showDialog", false);
                this.startActivity(intent);
                break;
            case R.id.phone_login:
                if(isFirstLoad()) {
                    startActivity(RegisterByPhoneActivity.class);
                }
                else {
                    startActivity(LoginByPhoneActivity.class);
                }
                break;
            case R.id.skip_login:
                startActivity(MainActivity.class);
                Logger.d("点击了跳过按钮，跳转到主页");
                finish();
                break;
            default:
                break;
        }
    }

    private boolean isFirstLoad(){

        return true;
    };
}
