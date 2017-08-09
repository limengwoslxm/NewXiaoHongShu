package com.apple.xhs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.base.BaseActivity;
import com.collecter.ErrorCollecter;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by limeng on 2017/7/24.
 */

public class ForgetPass extends BaseActivity implements TextWatcher {
    @BindView(R.id.user_email_toResetP)
    TextView reset;
    @BindView(R.id.bt_toResetP)
    Button toReset;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            toReset.setText(msg.obj.toString());
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toReset.setEnabled(false);
        reset.addTextChangedListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.forget_password;
    }

    @OnClick(R.id.bt_toResetP)
    public void ResetOnClick(){
        toReset.setEnabled(false);
        new Thread(){
            int i = 60;
            @Override
            public void run() {
                super.run();
                while (i >= 0){
                    Message message = Message.obtain();
                    message.obj = "重置密码(" + i + "s)";
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i--;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toReset.setEnabled(true);
                        toReset.setText("重置密码");
                    }
                });
            }
        }.start();
        final String email = reset.getText().toString().trim();
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(ForgetPass.this,"重置密码请求成功，请到" + email + "邮箱进行密码重置操作",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgetPass.this, ErrorCollecter.errorCode(e),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void backToLogin(View view) {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (reset.getText().toString().matches("[a-zA-Z_0-9]+@(([a-zA-z0-9]-*)+\\.){1,3}[a-zA-z\\-]+")){
            toReset.setEnabled(true);
        }
    }
}
