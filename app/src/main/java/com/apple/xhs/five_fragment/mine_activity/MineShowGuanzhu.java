package com.apple.xhs.five_fragment.mine_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.apple.xhs.custom_view.InfoSettingTitle;
import com.apple.xhs.R;
import com.apple.util.MyFragmentPagerAdapter;
import com.apple.xhs.five_fragment.mine_activity.biaoqian_fragment.FragmentFour;
import com.apple.xhs.five_fragment.mine_activity.biaoqian_fragment.FragmentOne;
import com.apple.xhs.five_fragment.mine_activity.biaoqian_fragment.FragmentThree;
import com.apple.xhs.five_fragment.mine_activity.biaoqian_fragment.FragmentTwo;
import com.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by limeng on 2017/7/26.
 */

public class MineShowGuanzhu extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.show_guanzhu)
    InfoSettingTitle show_guanzhu;
    @BindView(R.id.guan_viewpager)
    ViewPager viewPager;
    @BindView(R.id.guan_tablayout)
    TabLayout tablayout;
    List<Fragment> data = new ArrayList<>();
    List<String> list = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.mine_show_guanzhu;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        tablayout.setupWithViewPager(viewPager);
        FragmentOne one = new FragmentOne();
        FragmentTwo two = new FragmentTwo();
        FragmentThree three = new FragmentThree();
        FragmentFour four = new FragmentFour();
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),data,list);
        adapter.addFragment(one,"关注");
        adapter.addFragment(two,"粉丝");
        adapter.addFragment(three,"发布");
        adapter.addFragment(four,"收藏");
        viewPager.setAdapter(adapter);
        show_guanzhu.setImgListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        int i = intent.getIntExtra("statistical",0);
        switch (i){
            case 1:
                viewPager.setCurrentItem(0);
                break;
            case 2:
                viewPager.setCurrentItem(1);
                break;
            case 3:
                viewPager.setCurrentItem(2);
                break;
            case 4:
                viewPager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
