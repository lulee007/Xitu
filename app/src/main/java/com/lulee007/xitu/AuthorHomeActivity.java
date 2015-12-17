package com.lulee007.xitu;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lulee007.xitu.base.XTBaseActivity;
import com.lulee007.xitu.util.GlideCircleTransform;

public class AuthorHomeActivity extends XTBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_home);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("掘金者");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String url=getIntent().getStringExtra("url");
        Glide.with(this).load(url).transform(new GlideCircleTransform(this)).into((ImageView) findViewById(R.id.author_icon));
    }
}
