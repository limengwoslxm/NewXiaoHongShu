package com.apple.xhs.five_fragment.mine_activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.apple.xhs.CustomView.InfoSettingTitle;
import com.apple.xhs.CustomView.UserInfoRow;
import com.apple.xhs.R;
import com.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by limeng on 2017/7/24.
 */

public class MineUserInfoSetting extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.infosetting)
    InfoSettingTitle back;
    @BindView(R.id.override_name)
    UserInfoRow name;
    @BindView(R.id.override_id)
    UserInfoRow id;
    @BindView(R.id.sex_choice)
    UserInfoRow sex;
    @BindView(R.id.signatures)
    UserInfoRow signatures;
    String sex_dialog;
    String currentName;
    String currentId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:
                break;
            case 1:
                String newname = data.getStringExtra("name");
                if(newname.equals("")){
                    return;
                }
                name.getName().setText(newname);
                break;
            case 2:
                String newid = data.getStringExtra("id");
                if(newid.equals("")){
                    return;
                }
                id.getName().setText(newid);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int getContentViewId() {
        return R.layout.mine_user_info;
    }
    private void setViewListener() {
        back.setImgListener(this);
        name.setOnClickListener(this);
        id.setOnClickListener(this);
        sex.setOnClickListener(this);
    }
    @OnClick(R.id.birthday_choice)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:
                finish();
                break;
            case R.id.override_name:
                currentName = name.getName().getText().toString();
                Intent intentName = new Intent(this,MineSettingName.class);
                intentName.putExtra("currentname",currentName);
                startActivityForResult(intentName,1);
                break;
            case R.id.override_id:
                currentId = id.getName().getText().toString();
                Intent intentId = new Intent(this,MineSettingID.class);
                intentId.putExtra("currentid",currentId);
                startActivityForResult(intentId,2);
                break;
            case R.id.sex_choice:
                sexChoiceDialog();
                break;
            case R.id.birthday_choice:
                setBirthday();
                break;
            case R.id.signatures:
                break;
        }
    }

    private void setBirthday() {

    }
    //性别选择
    private void sexChoiceDialog() {
        new AlertDialog.Builder(this)
                .setTitle("请选择你的性别")
                .setSingleChoiceItems(new String[]{"男", "女"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            sex_dialog = "男";
                        }else if(i == 1){
                            sex_dialog = "女";
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sex.getName().setText(sex_dialog);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

}
