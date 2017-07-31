package com.apple.xhs.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.apple.xhs.R;
import com.apple.xhs.custom_view.InfoSettingTitle;
import com.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by limeng on 2017/7/29.
 */

public class ProductScan extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.product_scan)
    InfoSettingTitle toptoolbar;
    @BindView(R.id.product_scan_main)
    View main;
    PopupWindow popupWindow;
    View popView;
    @Override
    public int getContentViewId() {
        return R.layout.product_scan;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopTitle();
        setPopUpWindow();
        addViewListener();
    }

    private void addViewListener() {
        toptoolbar.setImgListener(this);
        toptoolbar.setDoneListener(this);
        //popUpWindow的取消按钮监听
        popView.findViewById(R.id.quxiao).setOnClickListener(this);
    }

    private void setTopTitle() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        toptoolbar.setTitleText(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:
                finish();
                break;
            case R.id.my_setting_done:
                popupWindow.showAtLocation(main,Gravity.BOTTOM,0,0);
                backgroundAlpha(0.6f);
                break;
            case R.id.quxiao:
                popupWindow.dismiss();
                break;
        }
    }
    private void setPopUpWindow() {
        LayoutInflater inflater = getLayoutInflater();
        popView =inflater.inflate(R.layout.share_popupwindow,null);
        popupWindow = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.SharePopupWindow);
        popupWindow.setOnDismissListener(new MyPopUpWindow());
    }
    public class MyPopUpWindow implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}