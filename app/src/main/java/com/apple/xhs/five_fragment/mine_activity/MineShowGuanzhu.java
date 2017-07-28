package com.apple.xhs.five_fragment.mine_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.apple.xhs.CustomView.InfoSettingTitle;
import com.apple.xhs.R;
import com.apple.xhs.five_fragment.view_util.MyFragmentPagerAdapter;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        tablayout.setupWithViewPager(viewPager);
        FragmentOne one = new FragmentOne();
        FragmentTwo two = new FragmentTwo();
        FragmentThree three = new FragmentThree();
        FragmentFour four = new FragmentFour();
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),data,list);
        adapter.addFragment(one,"用户");
        adapter.addFragment(two,"专辑");
        adapter.addFragment(three,"标签");
        adapter.addFragment(four,"商家");
        viewPager.setAdapter(adapter);
        show_guanzhu.setImgListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.mine_show_guanzhu;
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
