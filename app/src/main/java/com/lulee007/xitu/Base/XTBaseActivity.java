package com.lulee007.xitu.Base;

import android.app.Activity;
import android.content.Intent;

/**
 * User: lulee007@live.com
 * Date: 2015-12-08
 * Time: 12:50
 */
public class XTBaseActivity extends Activity {


    public void startActivity(Class target){
        Intent intent=new Intent(this,target);
        startActivity(intent);
    }
}
