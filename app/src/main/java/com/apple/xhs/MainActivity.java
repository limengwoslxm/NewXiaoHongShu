package com.apple.xhs;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.apple.xhs.five_fragment.HomeFragment;
import com.collecter.ActivityCollecter;
import com.collecter.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limeng on 2017/7/21.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    TextView tab_home,tab_search,tab_store,tab_msg,tab_me;
    View fragment_home,fragment_search,fragment_store,fragment_msg,fragment_me;
    FragmentManager fm;
    List<Fragment> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
    }

    @SuppressLint("ResourceAsColor")
    private void initView() {
        tab_home = findViewById(R.id.tab_home);
        tab_home.setSelected(true);
        tab_search = findViewById(R.id.tab_search);
        tab_store = findViewById(R.id.tab_store);
        tab_msg = findViewById(R.id.tab_msg);
        tab_me = findViewById(R.id.tab_me);

        tab_home.setOnClickListener(this);
        tab_search.setOnClickListener(this);
        tab_store.setOnClickListener(this);
        tab_msg.setOnClickListener(this);
        tab_me.setOnClickListener(this);

        fragment_home = findViewById(R.id.fragment_home);
        fragment_search = findViewById(R.id.fragment_search);
        fragment_store = findViewById(R.id.fragment_store);
        fragment_msg = findViewById(R.id.fragment_msg);
        fragment_me = findViewById(R.id.fragment_me);
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
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollecter.finishAll();
    }
}
