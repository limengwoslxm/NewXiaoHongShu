package com.apple.xhs.five_fragment.mine_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.apple.xhs.custom_view.InfoSettingTitle;
import com.apple.xhs.R;
import com.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by limeng on 2017/7/25.
 */

public class MineSettingSign extends BaseActivity implements View.OnClickListener, TextWatcher {
    @BindView(R.id.reset_signatures)
    InfoSettingTitle signatures;
    @BindView(R.id.sign_edit)
    EditText signEdit;
    @BindView(R.id.num_limit)
    TextView numLimit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewClickListener();
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String str = intent.getStringExtra("sign");
        signEdit.setText(str);
    }

    private void initViewClickListener() {
        signatures.setImgListener(this);
        signatures.setDoneListener(this);
        signEdit.addTextChangedListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.mine_setting_sign;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_setting_back:
                setResult(0, new Intent(this, MineUserInfoSetting.class));
                finish();
                break;
            case R.id.my_setting_done:
                String sign = signEdit.getText().toString();
                Intent intent = new Intent(this, MineUserInfoSetting.class);
                intent.putExtra("sign", sign);
                setResult(4, intent);
                finish();
                break;
        }
    }
    //监听字数变化
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void afterTextChanged(Editable editable) {
        int num = signEdit.getText().toString().length();
        numLimit.setHint((100-num)+"");
    }
}
