package com.apple.xhs.five_fragment.search_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.apple.xhs.custom_view.InfoSettingTitle;
import com.apple.xhs.R;
import com.apple.xhs.adapter_util.MyFragmentPagerAdapter;
import com.apple.xhs.five_fragment.search_activity.search_fragment.SearchAddF_1;
import com.apple.xhs.five_fragment.search_activity.search_fragment.SearchAddF_2;
import com.apple.xhs.five_fragment.search_activity.search_fragment.SearchAddF_3;
import com.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by limeng on 2017/7/27.
 */

public class SearchAdd extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.search_find_friend)
    InfoSettingTitle searchTitle;
    @BindView(R.id.search_add_tab)
    TabLayout tabLayout;
    @BindView(R.id.search_add_viewpager)
    ViewPager viewPager;
    List<Fragment> data = new ArrayList<>();
    List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewListener();
        setTabViewPager();
    }

    private void setTabViewPager() {
        tabLayout.setupWithViewPager(viewPager);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),data,list);
        SearchAddF_1 one = new SearchAddF_1();
        SearchAddF_2 two = new SearchAddF_2();
        SearchAddF_3 three = new SearchAddF_3();
        adapter.addFragment(one,"推荐");
        adapter.addFragment(two,"兴趣");
        adapter.addFragment(three,"附近");
        viewPager.setAdapter(adapter);
    }

    private void initViewListener() {
        searchTitle.setImgListener(this);
        searchTitle.setDoneListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.search_add_friend;
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
