package com.lulee007.xitu.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * User: lulee007@live.com
 * Date: 2015-12-08
 * Time: 12:50
 */
public class XTBaseActivity extends AppCompatActivity {


    public void startActivity(Class target){
        Intent intent=new Intent(this,target);
        startActivity(intent);
    }
}
