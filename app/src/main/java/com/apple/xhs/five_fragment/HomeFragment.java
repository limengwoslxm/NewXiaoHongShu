package com.apple.xhs.five_fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.apple.xhs.R;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_1;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_10;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_11;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_12;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_2;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_3;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_4;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_5;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_6;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_7;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_8;
import com.apple.xhs.five_fragment.home_fragment.HomeFragment_9;
import com.apple.xhs.five_fragment.home_util.MyFragmentPagerAdapter;
import com.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by limeng on 2017/7/22.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    View popUpView,homeTop,popUpDismiss1,popUpDismiss2;
    TabLayout tabLayout;
    ViewPager viewPager;
    PopupWindow popupWindow;
    WindowManager windowManager;
    MyFragmentPagerAdapter adapter;
    View hideLine;
    List<Fragment> data = new ArrayList<>();
    List<String> list = new ArrayList<>();

    HomeFragment_1 homeFragment_1;
    HomeFragment_2 homeFragment_2;
    HomeFragment_3 homeFragment_3;
    HomeFragment_4 homeFragment_4;
    HomeFragment_5 homeFragment_5;
    HomeFragment_6 homeFragment_6;
    HomeFragment_7 homeFragment_7;
    HomeFragment_8 homeFragment_8;
    HomeFragment_9 homeFragment_9;
    HomeFragment_10 homeFragment_10;
    HomeFragment_11 homeFragment_11;
    HomeFragment_12 homeFragment_12;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout,container,false);
        initViewPopWindow(view);
        initViewPager(view);
        return view;
    }

    private void initViewPager(View view) {
        viewPager = view.findViewById(R.id.home_viewpager);
        tabLayout = view.findViewById(R.id.home_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        initAddFragment();
        adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),data,list);
        addAllFragment();
        viewPager.setAdapter(adapter);
    }

    private void addAllFragment() {
        adapter.addFragment(homeFragment_1,"推荐");
        adapter.addFragment(homeFragment_2,"关注");
        adapter.addFragment(homeFragment_3,"武汉");
        adapter.addFragment(homeFragment_4,"男人");
        adapter.addFragment(homeFragment_5,"护肤");
        adapter.addFragment(homeFragment_6,"居家");
        adapter.addFragment(homeFragment_7,"时尚");
        adapter.addFragment(homeFragment_8,"美食");
        adapter.addFragment(homeFragment_9,"运动");
        adapter.addFragment(homeFragment_10,"旅行");
        adapter.addFragment(homeFragment_11,"彩妆");
        adapter.addFragment(homeFragment_12,"母婴");
    }

    private void initAddFragment() {
        homeFragment_1 = new HomeFragment_1();
        homeFragment_2 = new HomeFragment_2();
        homeFragment_3 = new HomeFragment_3();
        homeFragment_4 = new HomeFragment_4();
        homeFragment_5 = new HomeFragment_5();
        homeFragment_6 = new HomeFragment_6();
        homeFragment_7 = new HomeFragment_7();
        homeFragment_8 = new HomeFragment_8();
        homeFragment_9 = new HomeFragment_9();
        homeFragment_10 = new HomeFragment_10();
        homeFragment_11 = new HomeFragment_11();
        homeFragment_12 = new HomeFragment_12();
    }

    //弹出窗口
    private void initViewPopWindow(View view) {
        view.findViewById(R.id.home_scrollview_showMore).setOnClickListener(this);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popUpView = layoutInflater.inflate(R.layout.home_scrollview_show_more,null);
        windowManager = getActivity().getWindowManager();
        popupWindow = new PopupWindow(popUpView,windowManager.getDefaultDisplay().getWidth(),
                windowManager.getDefaultDisplay().getHeight());
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        //弹出框显示在该控件下方
        homeTop = view.findViewById(R.id.home_top);
        initViewOthers(popUpView);
    }
    private void initViewOthers(View view) {
        popUpDismiss1 = view.findViewById(R.id.popUpDismiss1);
        popUpDismiss2 = view.findViewById(R.id.popUpDismiss2);
        hideLine = view.findViewById(R.id.home_line_hide);
        popUpDismiss1.setOnClickListener(this);
        popUpDismiss2.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_scrollview_showMore:
                hideLine.setVisibility(View.INVISIBLE);
                popupWindow.showAsDropDown(homeTop);
                break;
            case R.id.popUpDismiss1:
            case R.id.popUpDismiss2:
                hideLine.setVisibility(View.VISIBLE);
                popupWindow.dismiss();
                break;
        }
    }
}
