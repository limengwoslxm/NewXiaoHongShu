package com.apple.xhs;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by limeng on 2017/7/21.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tab_home,tab_search,tab_store,tab_msg,tab_me;
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
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        resetTabColor();
        switch (view.getId()){
            case R.id.tab_home:
                tab_home.setSelected(true);
                break;
            case R.id.tab_search:
                tab_search.setSelected(true);
                break;
            case R.id.tab_store:
                tab_store.setSelected(true);
                break;
            case R.id.tab_msg:
                tab_msg.setSelected(true);
                break;
            case R.id.tab_me:
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
}
