package com.apple.xhs.five_fragment.mine_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.apple.xhs.CustomView.InfoSettingTitle;
import com.apple.xhs.CustomView.UserInfoRow;
import com.apple.xhs.R;
import com.base.BaseActivity;

import butterknife.BindView;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewListener();
    }

    @Override
    public int getContentViewId() {
        return R.layout.mine_user_info;
    }
    private void setViewListener() {
        back.setImgListener(this);
        name.setOnClickListener(this);
        id.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:
                finish();
                break;
            case R.id.override_name:
                startActivity(new Intent(this,MineSettingName.class));
                break;
            case R.id.override_id:
                startActivity(new Intent(this,MineSettingID.class));
                break;
        }
    }
}
