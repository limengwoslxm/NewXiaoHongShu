package com.apple.xhs.five_fragment.mine_activity;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.apple.xhs.custom_view.InfoSettingTitle;
import com.apple.xhs.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.base.BaseActivity;
import com.data.UpdateDataBmob;

import butterknife.BindView;


/**
 * Created by limeng on 2017/7/25.
 */

public class MineSettingArea extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.reset_area)
    InfoSettingTitle area;
    @BindView(R.id.mycurrentarea)
    TextView mycurrentarea;

    String addrStr,province;
    LocationClient locationClient;
    BDLocationListener locationListener;
    @Override
    public int getContentViewId() {
        return R.layout.mine_setting_area;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //创建LocationClient(位置客户端)对象，定位功能是通过此对象完成的
        locationClient = new LocationClient(this);
        //配置定位SDK参数，设置定位参数包括：设置精度，扫描间隔以及坐标系等参数。
        LocationClientOption option = new LocationClientOption();
        //高精度定位，使用GPS和网络
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系类型
        option.setCoorType("bd0911");
        //间隔多少秒刷新位置
        //option.setScanSpan(2000);
        //是否需要返回地址,默认不需要
        option.setIsNeedAddress(true);
        //将参数设置进去
        locationClient.setLocOption(option);
        //设置位置监听器，当定位服务上报数据时，可以通过监听器获取定位信息。
        locationListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                province = bdLocation.getProvince();
                addrStr = bdLocation.getCity();
                mycurrentarea.setText(province+addrStr);
                UpdateDataBmob.UpdataArea(province+addrStr);
            }
        };
        //注册监听事件
        locationClient.registerLocationListener(locationListener);
        //开始定位
        locationClient.start();
    }

    private void initView() {
        area.setImgListener(this);
        area.setDoneListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:
            case R.id.my_setting_done:
                Intent intent = new Intent(this,MineUserInfoSetting.class);
                intent.putExtra("mylocation",province+addrStr);
                setResult(3,intent);
                finish();
                break;
        }
    }
}
