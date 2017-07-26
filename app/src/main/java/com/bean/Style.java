package com.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by xiong on 2017/7/24.
 */

public class Style extends BmobObject {
    private String name;
    private BmobRelation note;

    public Style() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobRelation getNote() {
        return note;
    }

    public void setNote(BmobRelation note) {
        this.note = note;
    }
}
