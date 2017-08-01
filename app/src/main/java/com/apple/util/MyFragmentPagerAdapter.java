package com.apple.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by limeng on 2017/7/24.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
    List<Fragment> data;
    List<String> list;
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> data,List<String> list) {
        super(fm);
        this.data = data;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
    public void addFragment(Fragment fragment,String string){
        data.add(fragment);
        list.add(string);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
