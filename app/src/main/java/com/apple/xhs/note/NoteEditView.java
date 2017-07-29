package com.apple.xhs.note;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apple.xhs.CustomView.InfoSettingTitle;
import com.apple.xhs.R;
import com.base.BaseActivity;
import com.data.AddDataBmob;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.xiaopan.sketch.SketchImageView;

/**
 * Created by limeng on 2017/7/27.
 */

public class NoteEditView extends BaseActivity implements View.OnClickListener, TextWatcher {
    @BindView(R.id.send_note_title)
    InfoSettingTitle noteToolBar;
    @BindView(R.id.note_title)
    EditText noteTitle;
    @BindView(R.id.note_context)
    EditText noteContext;
    @BindView(R.id.note_num_limit)
    TextView limit;
    @BindView(R.id.note_add_pic)
    ImageView noteAddPic;

    @BindView(R.id.note_nanren)
    CheckBox noteNanren;
    @BindView(R.id.note_hufu)
    CheckBox noteHufu;
    @BindView(R.id.note_jujia)
    CheckBox noteJujia;
    @BindView(R.id.note_shishang)
    CheckBox noteShishang;
    @BindView(R.id.note_meishi)
    CheckBox noteMeishi;
    @BindView(R.id.note_yundong)
    CheckBox noteYundong;
    @BindView(R.id.note_lvxing)
    CheckBox noteLvxing;
    @BindView(R.id.note_caizhuang)
    CheckBox noteCaizhuang;
    @BindView(R.id.note_muying)
    CheckBox noteMuying;
    String title;
    String context;
    List<String> getCheckData = new ArrayList<>();
    List<CheckBox> checkItem = new ArrayList<>();
    String[] strings = {"男人","护肤","居家","时尚","美食","运动","旅行","彩妆","母婴"};
    List<String> picData = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewListener();
        initCheckItem();
    }

    private void initCheckItem() {
        checkItem.add(noteNanren);
        checkItem.add(noteHufu);
        checkItem.add(noteJujia);
        checkItem.add(noteShishang);
        checkItem.add(noteMeishi);
        checkItem.add(noteYundong);
        checkItem.add(noteLvxing);
        checkItem.add(noteCaizhuang);
        checkItem.add(noteMuying);
    }

    @Override
    public int getContentViewId() {
        return R.layout.note_edit_view;
    }

    private void initViewListener() {
        //顶栏
        noteToolBar.setImgListener(this);
        noteToolBar.setDoneListener(this);
        //文本图片
        noteTitle.setOnClickListener(this);
        noteContext.setOnClickListener(this);
        noteAddPic.setOnClickListener(this);

        noteTitle.addTextChangedListener(this);
        noteContext.addTextChangedListener(this);
        //复选框
        noteNanren.setOnClickListener(this);
        noteHufu.setOnClickListener(this);
        noteJujia.setOnClickListener(this);
        noteShishang.setOnClickListener(this);
        noteMeishi.setOnClickListener(this);
        noteYundong.setOnClickListener(this);
        noteLvxing.setOnClickListener(this);
        noteCaizhuang.setOnClickListener(this);
        noteMuying.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_setting_back:

                finish();
                break;
            case R.id.my_setting_done:
                title = noteTitle.getText().toString();
                context = noteContext.getText().toString();
                addCheckData();//返回数据到 getCheckData；
                AddDataBmob.addDataToNote(title,context,picData,getCheckData);
                finish();
                break;
            case R.id.note_add_pic:
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");//图片
                startActivityForResult(galleryIntent, 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("tag", "onActivityResult: "+requestCode+":"+resultCode);
        if(resultCode==0){
            return;
        }
        getResultPic(data);
    }

    private void getResultPic(Intent data) {
        Uri originalUri=data.getData();
        String []imgs1={MediaStore.Images.Media.DATA};//将图片URI转换成存储路径
        Cursor cursor=this.managedQuery(originalUri, imgs1, null, null, null);
        int index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String img_url=cursor.getString(index);
        if(picData.size()>9){
            Toast.makeText(this,"最多添加9张图片",Toast.LENGTH_SHORT).show();
            return;
        }
        picData.add(img_url);
        addView(img_url);
    }
    private void addView(String s) {
        LinearLayout linearLayout = findViewById(R.id.linearlayout);
        SketchImageView img = new SketchImageView(this);
        TextView textView = new TextView(this);
        img.setLayoutParams(new LinearLayout.LayoutParams(300,300));
        textView.setLayoutParams(new LinearLayout.LayoutParams(20,300));
        Bitmap bitmap = BitmapFactory.decodeFile(s);
        img.setImageBitmap(bitmap);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        linearLayout.addView(textView);
        linearLayout.addView(img);
    }

    private void addCheckData() {
        getCheckData.clear();
        for(int i = 0;i<checkItem.size();i++){
            if(checkItem.get(i).isChecked()){
                getCheckData.add(strings[i]);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        limit.setHint((20-noteTitle.getText().toString().length())+"");
    }
}
