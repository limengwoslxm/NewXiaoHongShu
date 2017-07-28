package com.apple.xhs.five_fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.apple.xhs.R;
import com.apple.xhs.five_fragment.home_activity.HomeOpenCamera;
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
import com.apple.xhs.five_fragment.view_util.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by limeng on 2017/7/22.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    View popUpView,homeTop,popUpDismiss1,popUpDismiss2;
    TextView tab1,tab2,tab3,tab4,tab5,tab6,tab7,tab8,tab9,tab10,tab11,tab12;
    ImageView openCamera;
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
        initOthersView(view);
        initViewPager(view);
        setViewListener();
        return view;
    }

    private void initOthersView(View view) {
        openCamera = view.findViewById(R.id.home_open_camera);
        tab1 = popUpView.findViewById(R.id.home_tab1);
        tab2 = popUpView.findViewById(R.id.home_tab2);
        tab3 = popUpView.findViewById(R.id.home_tab3);
        tab4 = popUpView.findViewById(R.id.home_tab4);
        tab5 = popUpView.findViewById(R.id.home_tab5);
        tab6 = popUpView.findViewById(R.id.home_tab6);
        tab7 = popUpView.findViewById(R.id.home_tab7);
        tab8 = popUpView.findViewById(R.id.home_tab8);
        tab9 = popUpView.findViewById(R.id.home_tab9);
        tab10 = popUpView.findViewById(R.id.home_tab10);
        tab11 = popUpView.findViewById(R.id.home_tab11);
        tab12 = popUpView.findViewById(R.id.home_tab12);
    }

    private void setViewListener() {
        viewPager.setOnPageChangeListener(this);
        openCamera.setOnClickListener(this);

    }

    private void initViewPager(View view) {
        viewPager = view.findViewById(R.id.home_viewpager);
        tabLayout = view.findViewById(R.id.home_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        getFragment();
        adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),data,list);
        addAllFragment();
        viewPager.setAdapter(adapter);
        tabLayout.getTabAt(0).select();
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

    private void getFragment() {
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
        popUpView = layoutInflater.inflate(R.layout.home_tab_show_more,null);
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
            case R.id.home_open_camera:
                startActivity(new Intent(getActivity(), HomeOpenCamera.class));
                getActivity().overridePendingTransition(R.anim.home_camera_open,R.anim.home_camera_close);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setPopUpWindowSeclected(position);
    }

    private void setPopUpWindowSeclected(int position) {
        resetAlltabColor();
        switch (position){
            case 0:
                tab1.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 1:
                tab2.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 2:
                tab3.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 3:
                tab4.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 4:
                tab5.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 5:
                tab6.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 6:
                tab7.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 7:
                tab8.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 8:
                tab9.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 9:
                tab10.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 10:
                tab11.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
            case 11:
                tab12.setTextColor(getResources().getColor(R.color.xhsColor));
                break;
        }
    }

    private void resetAlltabColor() {
        tab1.setTextColor(getResources().getColor(R.color.black));
        tab2.setTextColor(getResources().getColor(R.color.black));
        tab3.setTextColor(getResources().getColor(R.color.black));
        tab4.setTextColor(getResources().getColor(R.color.black));
        tab5.setTextColor(getResources().getColor(R.color.black));
        tab6.setTextColor(getResources().getColor(R.color.black));
        tab7.setTextColor(getResources().getColor(R.color.black));
        tab8.setTextColor(getResources().getColor(R.color.black));
        tab9.setTextColor(getResources().getColor(R.color.black));
        tab10.setTextColor(getResources().getColor(R.color.black));
        tab11.setTextColor(getResources().getColor(R.color.black));
        tab12.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
