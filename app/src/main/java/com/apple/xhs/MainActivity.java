package com.apple.xhs;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.apple.xhs.five_fragment.HomeFragment;
import com.apple.xhs.five_fragment.MeFragment;
import com.apple.xhs.five_fragment.MsgFragment;
import com.apple.xhs.five_fragment.SearchFragment;
import com.apple.xhs.five_fragment.StoreFragment;
import com.collecter.ActivityCollecter;
import com.collecter.BaseActivity;


/**
 * Created by limeng on 2017/7/21.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    TextView tab_home,tab_search,tab_store,tab_msg,tab_me;
    FragmentManager manager;
    FragmentTransaction transaction;
    Fragment home_fragment,search_fragment,store_fragment,msg_fragment,me_fragment;
    View fg_home,fg_search,fg_store,fg_msg,fg_me;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
        initFragment();
        setFullScreen();
        addFragment();
    }

    private void initFragment() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
    }

    private void setFullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    @SuppressLint("ResourceAsColor")
    private void initView() {
        tab_home = findViewById(R.id.tab_home);
        tab_home.setSelected(true);
        tab_search = findViewById(R.id.tab_search);
        tab_store = findViewById(R.id.tab_store);
        tab_msg = findViewById(R.id.tab_msg);
        tab_me = findViewById(R.id.tab_me);

        fg_home = findViewById(R.id.fragment_home);
        fg_search = findViewById(R.id.fragment_search);
        fg_store = findViewById(R.id.fragment_store);
        fg_msg = findViewById(R.id.fragment_msg);
        fg_me = findViewById(R.id.fragment_me);

        resetFragment();
        fg_home.setVisibility(View.VISIBLE);

        tab_home.setOnClickListener(this);
        tab_search.setOnClickListener(this);
        tab_store.setOnClickListener(this);
        tab_msg.setOnClickListener(this);
        tab_me.setOnClickListener(this);

        home_fragment = new HomeFragment();
        search_fragment = new SearchFragment();
        store_fragment = new StoreFragment();
        msg_fragment = new MsgFragment();
        me_fragment = new MeFragment();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        resetTabColor();
        resetFragment();
        switch (view.getId()){
            case R.id.tab_home:
                fg_home.setVisibility(View.VISIBLE);
                tab_home.setSelected(true);
                break;
            case R.id.tab_search:
                fg_search.setVisibility(View.VISIBLE);
                tab_search.setSelected(true);
                break;
            case R.id.tab_store:
                fg_store.setVisibility(View.VISIBLE);
                tab_store.setSelected(true);
                break;
            case R.id.tab_msg:
                fg_msg.setVisibility(View.VISIBLE);
                tab_msg.setSelected(true);
                break;
            case R.id.tab_me:
                fg_me.setVisibility(View.VISIBLE);
                tab_me.setSelected(true);
                break;
        }
    }

    private void resetFragment() {
        fg_home.setVisibility(View.INVISIBLE);
        fg_search.setVisibility(View.INVISIBLE);
        fg_store.setVisibility(View.INVISIBLE);
        fg_msg.setVisibility(View.INVISIBLE);
        fg_me.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("ResourceAsColor")
    public void resetTabColor(){
        tab_home.setSelected(false);
        tab_search.setSelected(false);
        tab_store.setSelected(false);
        tab_msg.setSelected(false);
        tab_me.setSelected(false);
    }

    @Override
    public void onBackPressed() {
        ActivityCollecter.finishAll();
    }
    private void addFragment(){
        transaction.add(home_fragment,"home_fragment");
        transaction.add(search_fragment,"search_fragment");
        transaction.add(store_fragment,"store_fragment");
        transaction.add(msg_fragment,"msg_fragment");
        transaction.add(me_fragment,"me_fragment");
        transaction.commit();
    }
    private void popFragment(int id){
        switch (id){

        }
    };
}
