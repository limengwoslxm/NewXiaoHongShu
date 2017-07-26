package com.apple.xhs.five_fragment.mine_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apple.xhs.CustomView.InfoSettingTitle;
import com.apple.xhs.R;
import com.base.BaseActivity;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * Created by limeng on 2017/7/26.
 */

public class MineSettingMombaby extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.reset_mombaby)
    InfoSettingTitle mombaby;
    @BindView(R.id.beiyun)
    TextView beiyun;
    @BindView(R.id.huaiyun)
    TextView huaiyun;
    @BindView(R.id.youbb)
    TextView youbb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListener();
    }

    private void initListener() {
        mombaby.setImgListener(this);
        mombaby.setDoneListener(this);
        beiyun.setOnClickListener(this);
        huaiyun.setOnClickListener(this);
        youbb.setOnClickListener(this);

        Intent intentM = getIntent();
        String m = intentM.getStringExtra("momto");
        setOtherNormal();
        switch (m){
            case "备孕中":
                beiyun.setSelected(true);
                break;
            case "怀孕中":
                huaiyun.setSelected(true);
                break;
            case "有宝宝":
                youbb.setSelected(true);
                break;
            default:beiyun.setSelected(true);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.mine_setting_mombaby;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:
                setResult(0,new Intent(this,MineUserInfoSetting.class));
                finish();
                break;
            case R.id.my_setting_done:
                Intent intent = new Intent(this,MineUserInfoSetting.class);
                if(beiyun.isSelected())
                    intent.putExtra("momback",beiyun.getText().toString());
                if(huaiyun.isSelected())
                    intent.putExtra("momback",huaiyun.getText().toString());
                if(youbb.isSelected())
                    intent.putExtra("momback",youbb.getText().toString());
                setResult(5,intent);
                finish();
                break;
            case R.id.beiyun:
                setOtherNormal();
                beiyun.setSelected(true);
                break;
            case R.id.huaiyun:
                setOtherNormal();
                huaiyun.setSelected(true);
                break;
            case R.id.youbb:
                setOtherNormal();
                youbb.setSelected(true);
                break;
        }
    }

    private void setOtherNormal() {
        beiyun.setSelected(false);
        huaiyun.setSelected(false);
        youbb.setSelected(false);
    }

}
