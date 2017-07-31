package com.apple.xhs.five_fragment.mine_activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.apple.xhs.custom_view.InfoSettingTitle;
import com.apple.xhs.custom_view.UserInfoRow;
import com.apple.xhs.R;
import com.base.BaseActivity;
import com.bean.City;
import com.bean.MyUser;
import com.data.UpdateDataBmob;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.process.CircleImageProcessor;
import me.xiaopan.sketch.request.DisplayOptions;

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
    @BindView(R.id.signatures_edit)
    UserInfoRow signatures;
    @BindView(R.id.override_head)
    View head;
    @BindView(R.id.img_user_head)
    SketchImageView head_icon;
    @BindView(R.id.area_choice)
    UserInfoRow area;
    @BindView(R.id.choose_skin)
    UserInfoRow skin;
    @BindView(R.id.choose_fransnana)
    UserInfoRow mombaby;
    String sex_dialog;
    String currentName;
    String currentId;
    String currentArea;
    String currentSignatures;
    boolean sexDialog = false;
    List<Map<Integer,String>> skinData = new ArrayList<>();
    Map<Integer,String> map = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeadIconCircle();
        refreshUserInfo();
        setViewListener();

    }
    public void setHeadIconCircle(){
        DisplayOptions displayOptions = new DisplayOptions();
        displayOptions.setImageProcessor(CircleImageProcessor.getInstance());
        head_icon.setOptions(displayOptions);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:

                break;
            case 1:
                String newname = data.getStringExtra("name");
                if(newname.equals("")){
                    return;
                }
                name.getName().setText(newname);
                UpdateDataBmob.UpdataNickname(newname);
                break;
            case 2:
                String newid = data.getStringExtra("id");
                if(newid.equals("")){
                    return;
                }
                id.getName().setText(newid);
                UpdateDataBmob.UpdataID(newid);
                break;
            case 3:
                String newArea = data.getStringExtra("area");
                if(newArea.equals("")){
                    return;
                }
                area.getName().setText(newArea);
                UpdateDataBmob.UpdataArea(newArea);
                break;
            case 4:
                String newsign = data.getStringExtra("sign");
                if(newsign.equals("")){
                    return;
                }
                signatures.getName().setText(newsign);
                UpdateDataBmob.UpdataSignature(newsign);
                break;
            case 5:
                String newMom = data.getStringExtra("momback");
                if(newMom.equals("")){
                    return;
                }
                mombaby.getName().setText(newMom);
                UpdateDataBmob.UpdataPregnant(newMom);
                break;
            default:
                String img_url=uriChange(data);
                head_icon.displayImage(img_url);

                BmobFile icon = new BmobFile(new File(img_url));
                UpdateDataBmob.UpdataHead(icon);
                break;
        }
    }

    //将图片URI转换成存储路径
    public String uriChange(Intent data){
        Uri originalUri=data.getData();
        String []imgs1={MediaStore.Images.Media.DATA};
        Cursor cursor=this.managedQuery(originalUri, imgs1, null, null, null);
        int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
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
        area.setOnClickListener(this);
        signatures.setOnClickListener(this);
        skin.setOnClickListener(this);
        mombaby.setOnClickListener(this);
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
                startActivityForResult(galleryIntent, 100);
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
            case R.id.area_choice:
                currentArea = area.getName().getText().toString();
                Intent intentArea = new Intent(this,MineSettingArea.class);
                intentArea.putExtra("area",currentArea);
                startActivityForResult(intentArea,3);
                break;
            //个性签名
            case R.id.signatures_edit:
                currentSignatures = signatures.getName().getText().toString();
                Intent intentSign = new Intent(this,MineSettingSign.class);
                intentSign.putExtra("sign",currentSignatures);
                startActivityForResult(intentSign,4);
                break;
            case R.id.choose_skin:
                chooseSkin();
                break;
            case R.id.choose_fransnana:
                String currentMom = mombaby.getName().getText().toString();
                Intent intentMom = new Intent(this,MineSettingMombaby.class);
                intentMom.putExtra("mom",currentMom);
                startActivityForResult(intentMom,5);
                break;

        }
    }

    private void chooseSkin() {
        new AlertDialog.Builder(this)
                .setTitle("选择肤质信息")
                .setMultiChoiceItems(new String[]{"敏感肌","痘痘肌","干皮","油皮","混干皮","混油皮","中性皮肤"}, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        getChooseItem(i,b);
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String str = "";
                        for(Map.Entry<Integer,String> entry: map.entrySet()){
                            str = str+entry.getValue()+"  ";
                        }
                        skin.getName().setText(str);
                        UpdateDataBmob.UpdataSkin(skinData);
                        map.clear();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    private void getChooseItem(int i, boolean b) {
        switch (i){
            case 0:
                if(b){map.put(0,"敏感肌");}
                else {map.remove(0);}
                break;
            case 1:
                if(b){map.put(1,"痘痘肌");}
                else {map.remove(1);}
                break;
            case 2:
                if(b){map.put(2,"干皮");}
                else {map.remove(2);}
                break;
            case 3:
                if(b){map.put(3,"油皮");}
                else {map.remove(3);}
                break;
            case 4:
                if(b){map.put(4,"混干皮");}
                else {map.remove(4);}
                break;
            case 5:
                if(b){map.put(5,"混油皮");}
                else {map.remove(5);}
                break;
            case 6:
                if(b){map.put(6,"中性皮肤");}
                else {map.remove(6);}
                break;
        }
        if(map.isEmpty()){
            return;
        }
        skinData.add(map);
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
                            sexDialog = false;
                        }else if(i == 1){
                            sex_dialog = "女";
                            sexDialog = true;
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UpdateDataBmob.UpdataSex(sexDialog);
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
        if(myUser.getHead()!=null){
            //加载头像
            head_icon.displayImage(myUser.getHead().getUrl());
        }

        //昵称
        String nickname_Bmob = myUser.getNickname();
        if (nickname_Bmob!=null){
            name.getName().setText(nickname_Bmob);
        }else {
            name.getName().setText("小红书");
        }

        //ID
        String id_Bmob = myUser.getCopyId();
        if (id_Bmob!=null){
            id.getName().setText(id_Bmob);
        }

        //性别
        Boolean sex_Bmob = myUser.getSex();
        if(sex_Bmob != null){
            sex.getName().setText(sex_Bmob ? "女" : "男");
        }

        //常住地
        City city_Bmob = myUser.getAddress();
        if(city_Bmob != null){
            BmobQuery<City> query = new BmobQuery<City>();
            query.getObject(city_Bmob.getObjectId(), new QueryListener<City>() {
                @Override
                public void done(City city, BmobException e) {
                    if(e==null){
                        area.getName().setText(city.getCityname());
                    }else{
                        Log.i("bmob","获取常住地失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
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

        //肤质
        List<Map<Integer,String>> skin_Bmob = myUser.getSkin();
        if (skin_Bmob != null){
            String str = "";
            for(Map.Entry<Integer,String> entry: skin_Bmob.get(0).entrySet()){
                str = str+entry.getValue()+"  ";
            }
            skin.getName().setText(str);
        }

        //母婴
        String mom_Bmob = myUser.getPregnant();
        if (mom_Bmob != null){
            mombaby.getName().setText(sign_Bmob);
        }
    }
}
