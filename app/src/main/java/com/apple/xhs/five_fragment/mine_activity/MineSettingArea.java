package com.apple.xhs.five_fragment.mine_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.apple.xhs.CustomView.InfoSettingTitle;
import com.apple.xhs.R;
import com.base.BaseActivity;

import butterknife.BindView;


/**
 * Created by limeng on 2017/7/25.
 */

public class MineSettingArea extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.reset_area)
    InfoSettingTitle area;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        area.setImgListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.mine_setting_area;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.my_setting_back){
            setResult(0,new Intent(this,MineUserInfoSetting.class));
            finish();
        }
    }
}
