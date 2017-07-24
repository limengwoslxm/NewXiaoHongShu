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
 * Created by limeng on 2017/7/24.
 */

public class MineSettingName extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.reset_name)
    InfoSettingTitle resetname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewLisener();
    }

    @Override
    public int getContentViewId() {
        return R.layout.mine_setting_name;
    }

    private void setViewLisener() {
        resetname.setImgListener(this);
        resetname.setDoneListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:
                finish();
                break;
            case R.id.my_setting_done:
                finish();
                break;
        }
    }
}
