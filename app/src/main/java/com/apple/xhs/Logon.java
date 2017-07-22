package com.apple.xhs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.MyUser;
import com.collecter.BaseActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by limeng on 2017/7/22.
 */

public class Logon extends BaseActivity implements View.OnClickListener {
    TextView userName,userPass,userPassAga,userEmail;
    Button logon;
    String name,pass,passAga,email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.logon);
        initView();
    }

    private void initView() {
        userName = findViewById(R.id.logon_user_name);
        userPass = findViewById(R.id.logon_user_pass);
        userPassAga = findViewById(R.id.logon_user_pass_aga);
        userEmail = findViewById(R.id.logon_user_email);
        logon = findViewById(R.id.bt_logon);

        logon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        name = userName.getText().toString().trim();
        pass = userPass.getText().toString().trim();
        passAga = userPassAga.getText().toString().trim();
        email = userEmail.getText().toString().trim();
        if (!passAga.equals(pass)){
            Toast.makeText(getApplicationContext(),"密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        MyUser user = new MyUser();
        user.setUsername(name);
        user.setPassword(pass);
        user.setEmail(email);
        user.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e==null){
                    startActivity(new Intent(Logon.this,Login.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"注册失败" + e,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
