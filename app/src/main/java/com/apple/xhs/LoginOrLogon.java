package com.apple.xhs;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.apple.xhs.lol_fragment.OneFragment;
import com.apple.xhs.lol_fragment.ThreeFragment;
import com.apple.xhs.lol_fragment.TwoFragment;
import com.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limeng on 2017/7/21.
 */

public class LoginOrLogon extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private Fragment pager1,pager2,pager3;
    private FragmentManager fm;
    private List<Fragment> pagerList;
    private ImageView img1,img2,img3,account;
    private Button login,logon;
    private int x=125,y=215;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.loginorlogon;
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager1);
        viewPager.setOnPageChangeListener(this);

        login = findViewById(R.id.welcome_to_login);
        logon = findViewById(R.id.welcome_to_logon);
        login.setOnClickListener(this);
        logon.setOnClickListener(this);

        pager1 = new OneFragment();
        pager2 = new TwoFragment();
        pager3 = new ThreeFragment();
        fm = getSupportFragmentManager();

        img1 = findViewById(R.id.viewpager1_point1);
        img2 = findViewById(R.id.viewpager1_point2);
        img3 = findViewById(R.id.viewpager1_point3);
        img1.setImageResource(R.drawable.ic_walkthroughs_indicator_h);

        pagerList = new ArrayList<>();
        pagerList.add(pager1);
        pagerList.add(pager2);
        pagerList.add(pager3);
        viewPager.setOffscreenPageLimit(3);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(fm,pagerList);
        viewPager.setAdapter(adapter);

        account = findViewById(R.id.account);
        account.setX(x);
        account.setY(y);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(positionOffsetPixels==0){
            ObjectAnimator animator = ObjectAnimator.ofFloat(account,"rotationY",0.0f,360.0f);
            animator.setDuration(1000);
            animator.start();
        }
    }

    @Override
    public void onPageSelected(int position) {
        resetPoint();
        switch (position){
            case 0:
                img1.setImageResource(R.drawable.ic_walkthroughs_indicator_h);
                break;
            case 1:
                img2.setImageResource(R.drawable.ic_walkthroughs_indicator_h);
                break;
            case 2:
                img3.setImageResource(R.drawable.ic_walkthroughs_indicator_h);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d("tag", "onPageScrollStateChanged: "+state);
    }
    public void resetPoint(){
        img1.setImageResource(R.drawable.ic_walkthroughs_indicator_normal);
        img2.setImageResource(R.drawable.ic_walkthroughs_indicator_normal);
        img3.setImageResource(R.drawable.ic_walkthroughs_indicator_normal);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.welcome_to_login){
            startActivity(new Intent(LoginOrLogon.this,Login.class));
        }else if(id == R.id.welcome_to_logon){
            startActivity(new Intent(LoginOrLogon.this,Logon.class));
        }
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter{
        List<Fragment> data = new ArrayList<>();
        public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }
}
