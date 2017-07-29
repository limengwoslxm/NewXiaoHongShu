package com.apple.xhs.CustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/29.
 */

public class StoreSortTen extends LinearLayout {
    private View parent;
    private View Column1,Column2,Column3,Column4,Column5;
    public StoreSortTen(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.store_sort_customview,this);
        parent = findViewById(R.id.store_custom_parent);
        Column1 = findViewById(R.id.sort_column1);
        Column2 = findViewById(R.id.sort_column2);
        Column3 = findViewById(R.id.sort_column3);
        Column4 = findViewById(R.id.sort_column4);
        Column5 = findViewById(R.id.sort_column5);
    }

    public View getColumn1() {
        return Column1;
    }

    public View getColumn2() {
        return Column2;
    }

    public View getColumn3() {
        return Column3;
    }

    public View getColumn4() {
        return Column4;
    }

    public View getColumn5() {
        return Column5;
    }
}
