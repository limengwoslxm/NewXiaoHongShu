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

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by limeng on 2017/7/21.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextView userName,userPass;
    TextView logon,reset;
    Button login;

    String name,pass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        initView();
    }

    private void initView() {
        userName = findViewById(R.id.user_name);
        userPass = findViewById(R.id.user_pass);
        login = findViewById(R.id.bt_login);
        logon = findViewById(R.id.go_logon);
        reset = findViewById(R.id.go_reset);

        login.setOnClickListener(this);
        logon.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                name = userName.getText().toString().trim();
                pass = userPass.getText().toString().trim();
                final MyUser user = new MyUser();
                user.setUsername(name);
                user.setPassword(pass);
                user.login(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if(e==null){
                            Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,MainActivity.class));
                        }else {
                            Toast.makeText(getApplicationContext(),"登陆失败" + e,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.go_logon:
                startActivity(new Intent(Login.this,Logon.class));
                break;
            case R.id.go_reset:
                startActivity(new Intent(Login.this,MainActivity.class));
                break;
        }
    }
}
