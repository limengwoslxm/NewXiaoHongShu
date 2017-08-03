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
import com.collecter.ErrorCollecter;
import com.data.UpdateDataBmob;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by limeng on 2017/7/22.
 */

public class Logon extends BaseActivity {
    @BindView(R.id.logon_user_name) TextView userName;
    @BindView(R.id.logon_user_pass) TextView userPass;
    @BindView(R.id.logon_user_pass_aga) TextView userPassAga;
    @BindView(R.id.logon_user_email) TextView userEmail;
    @BindView(R.id.logon_login) TextView logon_login;
    @BindView(R.id.bt_logon) Button logon;

    String name,pass,passAga,email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.logon;
    }

    private void initView() {

    }
    //注册
    @OnClick(R.id.bt_logon)
    public void logonOnClick() {
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
        if (email != null){
            user.setEmail(email);
        }
        user.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e==null){
                    UpdateDataBmob.UpdataIDNew(myUser.getObjectId());
                    startActivity(new Intent(Logon.this,Login.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), ErrorCollecter.errorCode(e),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.logon_login)
    public void loginOnClick(){
        startActivity(new Intent(Logon.this,Login.class));
    }
}
