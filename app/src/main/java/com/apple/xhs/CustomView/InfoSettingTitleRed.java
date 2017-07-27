package com.apple.xhs.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/24.
 */

public class InfoSettingTitleRed extends RelativeLayout {
    View img;
    TextView textView,textViewDone;
    public InfoSettingTitleRed(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUiView(context,attrs);
    }

    private void initUiView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InfoSettingTitleRed);
        String text = array.getString(R.styleable.InfoSettingTitleRed_setTextRed);
        String textdone = array.getString(R.styleable.InfoSettingTitleRed_setDoneTextRed);
        LayoutInflater.from(context).inflate(R.layout.mine_mysettingview_red,this);
        img = findViewById(R.id.my_setting_back_red);
        textView = (TextView) findViewById(R.id.my_setting_title_red);
        textViewDone = (TextView) findViewById(R.id.my_setting_done_red);
        textView.setText(text);
        textViewDone.setText(textdone);
    }

    public void setImgListener(OnClickListener listener) {
        img.setOnClickListener(listener);
    }
    public void setDoneListener(OnClickListener listener) {
        textViewDone.setOnClickListener(listener);
    }

    public TextView getTextViewDone() {
        return textViewDone;
    }

    public void setTextViewDone(TextView textViewDone) {
        this.textViewDone = textViewDone;
    }
}
