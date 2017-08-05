package com.apple.xhs.searchwhole;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.apple.xhs.R;
import com.base.BaseActivity;

/**
 * Created by limeng on 2017/8/4.
 */

public class SearchWholeItem extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.searchwholeitem;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String string = intent.getStringExtra("lable");
        Toast.makeText(this, string,Toast.LENGTH_SHORT).show();
    }
}
