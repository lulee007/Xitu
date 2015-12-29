package com.lulee007.xitu;

import android.app.Activity;
import android.os.Bundle;

import com.mikepenz.materialize.MaterializeBuilder;

public class LoginByPhoneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_phone);
        new MaterializeBuilder().withActivity(this).build();

    }
}
