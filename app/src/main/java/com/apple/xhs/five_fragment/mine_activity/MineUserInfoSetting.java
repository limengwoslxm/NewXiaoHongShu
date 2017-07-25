package com.apple.xhs.five_fragment.mine_activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.apple.initbmob.InitBmob;
import com.apple.xhs.CustomView.InfoSettingTitle;
import com.apple.xhs.CustomView.UserInfoRow;
import com.apple.xhs.R;
import com.base.BaseActivity;
import com.base.BaseCache;
import com.bean.MyUser;
import com.data.UpdateDataBmob;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by limeng on 2017/7/24.
 */

public class MineUserInfoSetting extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.infosetting)
    InfoSettingTitle back;
    @BindView(R.id.override_name)
    UserInfoRow name;
    @BindView(R.id.override_id)
    UserInfoRow id;
    @BindView(R.id.birthday_choice)
    UserInfoRow birthday;
    @BindView(R.id.sex_choice)
    UserInfoRow sex;
    @BindView(R.id.signatures)
    UserInfoRow signatures;
    @BindView(R.id.override_head)
    View head;
    @BindView(R.id.img_user_head)
    ImageView head_icon;
    String sex_dialog;
    String currentName;
    String currentId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshUserInfo();
        setViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:
                Uri originalUri=data.getData();
                String []imgs1={MediaStore.Images.Media.DATA};//将图片URI转换成存储路径
                Cursor cursor=this.managedQuery(originalUri, imgs1, null, null, null);
                int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String img_url=cursor.getString(index);
                BmobFile icon = new BmobFile(new File(img_url));
                UpdateDataBmob.UpdataHead(icon);
                break;
            case 1:
                String newname = data.getStringExtra("name");
                if(newname.equals("")){
                    return;
                }
                name.getName().setText(newname);
                break;
            case 2:
                String newid = data.getStringExtra("id");
                if(newid.equals("")){
                    return;
                }
                id.getName().setText(newid);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int getContentViewId() {
        return R.layout.mine_user_info;
    }
    private void setViewListener() {
        back.setImgListener(this);
        name.setOnClickListener(this);
        id.setOnClickListener(this);
        sex.setOnClickListener(this);
        head.setOnClickListener(this);
    }
    @OnClick(R.id.birthday_choice)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:
                finish();
                break;
            case R.id.override_head:
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");//图片
                startActivityForResult(galleryIntent, 0);
                break;
            case R.id.override_name:
                currentName = name.getName().getText().toString();
                Intent intentName = new Intent(this,MineSettingName.class);
                intentName.putExtra("currentname",currentName);
                startActivityForResult(intentName,1);
                break;
            case R.id.override_id:
                currentId = id.getName().getText().toString();
                Intent intentId = new Intent(this,MineSettingID.class);
                intentId.putExtra("currentid",currentId);
                startActivityForResult(intentId,2);
                break;
            case R.id.sex_choice:
                sexChoiceDialog();
                break;
            case R.id.birthday_choice:
                setBirthday();
                break;
            case R.id.signatures:
                break;
        }
    }

    private void setBirthday() {

    }
    //性别选择
    private void sexChoiceDialog() {
        new AlertDialog.Builder(this)
                .setTitle("请选择你的性别")
                .setSingleChoiceItems(new String[]{"男", "女"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            sex_dialog = "男";
                            UpdateDataBmob.UpdataSex(false);
                        }else if(i == 1){
                            sex_dialog = "女";
                            UpdateDataBmob.UpdataSex(true);
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sex.getName().setText(sex_dialog);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    //从数据库获取个人信息（创建和刷新时调用）
    private void refreshUserInfo(){
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        //加载头像
        if(myUser.getHead()!=null){
            String url = myUser.getHead().getUrl();
            ImageLoader loader = new ImageLoader(InitBmob.getRequestQueue(), new BaseCache());
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(head_icon,R.drawable.xy_walkthroughs_account,R.drawable.xy_walkthroughs_account);
            loader.get(url,listener);
        }

        //昵称
        String nickname_Bmob = myUser.getNickname();
        if (nickname_Bmob!=null){
            name.getName().setText(nickname_Bmob);
        }

        //性别
        Boolean sex_Bmob = myUser.getSex();
        if(sex_Bmob != null){
            sex.getName().setText(sex_Bmob ? "女" : "男");
        }

        //生日
        String birth_Bmob = myUser.getBirthday();
        if (birth_Bmob != null){
            birthday.getName().setText(birth_Bmob);
        }

        //个性签名
        String sign_Bmob = myUser.getSignature();
        if (sign_Bmob != null){
            signatures.getName().setText(sign_Bmob);
        }
    }
}
