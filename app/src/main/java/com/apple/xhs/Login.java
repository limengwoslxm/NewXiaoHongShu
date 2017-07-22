package com.apple.xhs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by limeng on 2017/7/21.
 */

public class Login extends AppCompatActivity {
    TextView userName,userPass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        initView();
    }

    private void initView() {

    }
}
