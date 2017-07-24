package com.apple.xhs.five_fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apple.xhs.Login;
import com.apple.xhs.R;

import cn.bmob.v3.BmobUser;

/**
 * Created by limeng on 2017/7/22.
 */

public class MeFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_layout,container,false);
        view.findViewById(R.id.mine_exit_account).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_exit_account:
                popExitAialog();
                break;
        }
    }
    //退出账户的方法
    private void popExitAialog() {
        new AlertDialog.Builder(getActivity()).setTitle("登出")
                .setMessage("确定登出？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //确定退出
                        BmobUser.logOut();   //清除缓存用户对象
                        startActivity(new Intent(getActivity(), Login.class));
                        Toast.makeText(getContext(),"退出",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}
