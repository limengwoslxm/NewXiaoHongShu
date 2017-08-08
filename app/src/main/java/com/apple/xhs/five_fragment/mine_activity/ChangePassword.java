package com.apple.xhs.five_fragment.mine_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apple.xhs.R;
import com.apple.xhs.custom_view.InfoSettingTitle;
import com.base.BaseActivity;
import com.bean.MyUser;
import com.collecter.ErrorCollecter;
import com.data.UpdateDataBmob;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by limeng on 2017/8/8.
 */

public class ChangePassword extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.changepwdtoolbar)
    InfoSettingTitle toolbar;
    @BindView(R.id.changeoldpwd)
    EditText changeoldpwd;
    @BindView(R.id.changenewpwd)
    EditText changenewpwd;
    @BindView(R.id.changepwdagain)
    EditText changepwdagain;
    @BindView(R.id.surechangepwd)
    Button surechangepwd;

    @Override
    public int getContentViewId() {
        return R.layout.changepassword;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListener();
    }

    private void setListener() {
        toolbar.setImgListener(this);
        surechangepwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:
                finish();
                break;
            case R.id.surechangepwd:
                changePwd();
                finish();
                break;
        }
    }

    private void changePwd() {
        String oldP = changeoldpwd.getText().toString();
        String newP = changenewpwd.getText().toString();
        String newPA = changepwdagain.getText().toString();
        if(!newP.equals(newPA)){
            Toast.makeText(this,"两次输入新密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        BmobUser.updateCurrentUserPassword(oldP, newP, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Toast.makeText(getApplicationContext(),"修改密码成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), ErrorCollecter.errorCode(e),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
