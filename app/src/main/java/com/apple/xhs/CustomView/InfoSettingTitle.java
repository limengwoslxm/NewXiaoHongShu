package com.apple.xhs.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/24.
 */

public class InfoSettingTitle extends RelativeLayout {
    ImageView img;
    TextView textView,textViewDone;
    public InfoSettingTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUiView(context,attrs);
    }

    private void initUiView(Context context,AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InfoSettingTitle);
        String text = array.getString(R.styleable.InfoSettingTitle_setText);
        String textdone = array.getString(R.styleable.InfoSettingTitle_setDoneText);

        LayoutInflater.from(context).inflate(R.layout.mine_mysettingview,this);
        img = findViewById(R.id.my_setting_back);
        textView = findViewById(R.id.my_setting_title);
        textViewDone = findViewById(R.id.my_setting_done);
        textView.setText(text);
        textViewDone.setText(textdone);
    }

    public void setImgListener(OnClickListener listener) {
        img.setOnClickListener(listener);
    }
    public void setDoneListener(OnClickListener listener) {
        textViewDone.setOnClickListener(listener);
    }
}
