package com.lulee007.xitu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.lulee007.xitu.base.XTBaseActivity;
import com.mikepenz.materialize.MaterializeBuilder;

public class RegisterByPhoneActivity extends XTBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_phone);
        new MaterializeBuilder().withActivity(this).build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
