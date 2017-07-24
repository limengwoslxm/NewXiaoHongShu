package com.apple.xhs;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.collecter.ActivityCollecter;
import com.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by limeng on 2017/7/21.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tab_home) TextView tab_home;
    @BindView(R.id.tab_search) TextView tab_search;
    @BindView(R.id.tab_store) TextView tab_store;
    @BindView(R.id.tab_msg) TextView tab_msg;
    @BindView(R.id.tab_me) TextView tab_me;

    @BindView(R.id.fragment_home) View fragment_home;
    @BindView(R.id.fragment_search) View fragment_search;
    @BindView(R.id.fragment_store) View fragment_store;
    @BindView(R.id.fragment_msg) View fragment_msg;
    @BindView(R.id.fragment_me) View fragment_me;
    FragmentManager fm;
    List<Fragment> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        initView();
    }
    private void setFullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    @Override
    public int getContentViewId() {
        return R.layout.main_activity;
    }

    @SuppressLint("ResourceAsColor")
    private void initView() {
        resetFragment();
        tab_home.setSelected(true);

        tab_home.setOnClickListener(this);
        tab_search.setOnClickListener(this);
        tab_store.setOnClickListener(this);
        tab_msg.setOnClickListener(this);
        tab_me.setOnClickListener(this);

        fragment_home.setVisibility(View.VISIBLE);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        resetTabColor();
        resetFragment();
        switch (view.getId()){
            case R.id.tab_home:
                fragment_home.setVisibility(View.VISIBLE);
                tab_home.setSelected(true);
                break;
            case R.id.tab_search:
                fragment_search.setVisibility(View.VISIBLE);
                tab_search.setSelected(true);
                break;
            case R.id.tab_store:
                fragment_store.setVisibility(View.VISIBLE);
                tab_store.setSelected(true);
                break;
            case R.id.tab_msg:
                fragment_msg.setVisibility(View.VISIBLE);
                tab_msg.setSelected(true);
                break;
            case R.id.tab_me:
                fragment_me.setVisibility(View.VISIBLE);
                tab_me.setSelected(true);
                break;
        }
    }
    @SuppressLint("ResourceAsColor")
    public void resetTabColor(){
        tab_home.setSelected(false);
        tab_search.setSelected(false);
        tab_store.setSelected(false);
        tab_msg.setSelected(false);
        tab_me.setSelected(false);
    }
    public void resetFragment(){
        fragment_home.setVisibility(View.INVISIBLE);
        fragment_store.setVisibility(View.INVISIBLE);
        fragment_search.setVisibility(View.INVISIBLE);
        fragment_msg.setVisibility(View.INVISIBLE);
        fragment_me.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        ActivityCollecter.finishAll();
    }
}
