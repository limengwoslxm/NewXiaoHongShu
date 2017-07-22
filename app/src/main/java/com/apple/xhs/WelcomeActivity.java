package com.apple.xhs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;

/**
 * Created by limeng on 2017/7/21.
 */

public class WelcomeActivity extends Activity {
    int i = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_pic);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i == 1) {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    WelcomeActivity.this.finish();
                }else if(i == 2){
                    startActivity(new Intent(WelcomeActivity.this,LoginOrLogon.class));
                    WelcomeActivity.this.finish();
                }
            }
        },3000);
    }
}
