package com.apple.xhs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.apple.xhs.custom_view.InfoSettingTitle;
import com.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by limeng on 2017/8/3.
 */

public class ServiceHelp extends BaseActivity {
    @BindView(R.id.helptoolbar)
    InfoSettingTitle toolbar;
    @Override
    public int getContentViewId() {
        return R.layout.serviceshelp;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setImgListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
