package com.apple.xhs.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/25.
 */

public class InfoSettingRow extends RelativeLayout {

    public InfoSettingRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InfoSettingRow);
        String str = array.getString(R.styleable.InfoSettingRow_setRowTitle);

        LayoutInflater.from(context).inflate(R.layout.mine_infosettingrow,this);
        TextView view = (TextView) findViewById(R.id.setting_rows);
        view.setText(str);
    }
}
