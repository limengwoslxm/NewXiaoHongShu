package com.apple.xhs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.MyUser;
import com.base.BaseActivity;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by limeng on 2017/7/22.
 */

public class Logon extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.logon_user_name) TextView userName;
    @BindView(R.id.logon_user_pass) TextView userPass;
    @BindView(R.id.logon_user_pass_aga) TextView userPassAga;
    @BindView(R.id.logon_user_email) TextView userEmail;
    @BindView(R.id.bt_logon) Button logon;

    String name,pass,passAga,email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.logon;
    }

    private void initView() {
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
                    Toast.makeText(getApplicationContext(),"注册失败" ,Toast.LENGTH_SHORT).show();
                    Log.e(Logon.ACTIVITY_SERVICE, e + "");
                }
            }
        });
    }
}
