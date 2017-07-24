package com.apple.xhs.five_fragment.home_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.apple.xhs.R;

/**
 * Created by limeng on 2017/7/24.
 */

public class ScrollViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_scrollview_show_more);
    }
}
