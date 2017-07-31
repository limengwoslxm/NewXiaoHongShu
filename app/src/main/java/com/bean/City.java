package com.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by xiong on 2017/7/30.
 */

public class City extends BmobObject {
    private String cityname;

    public City() {
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
