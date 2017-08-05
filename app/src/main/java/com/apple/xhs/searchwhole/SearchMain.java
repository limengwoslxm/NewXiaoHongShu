package com.apple.xhs.searchwhole;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apple.initbmob.InitBmob;
import com.apple.xhs.R;
import com.apple.xhs.custom_view.HotSearchLable;
import com.apple.xhs.custom_view.HotSearchParent;
import com.base.BaseActivity;
import com.bean.Note;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by limeng on 2017/8/4.
 */

public class SearchMain extends BaseActivity implements View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {
    @BindView(R.id.historylable)
    LinearLayout historyParent;
    @BindView(R.id.hotlable)
    LinearLayout hotParent;
    @BindView(R.id.deletehistorysearch)
    ImageView delete;
    @BindView(R.id.getuserinput)
    EditText getuserinput;
    @BindView(R.id.cancel_search)
            TextView cancel_search;
    @BindView(R.id.searchlistview)
    ListView listView;
    @BindView(R.id.defindedresult)
            LinearLayout defindedresult;
    List<String> history = new ArrayList<>();
    List<String> hotlable = new ArrayList<>();
    List<String> relatedTitle = new ArrayList<>();
    List<Note> noteList = new ArrayList<>();
    String relatedcount ;
    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                relatedcount = msg.obj+"";
                relatedTitle.clear();
                relatedTitle.add("共有"+relatedcount+"条相关笔记");
                setListView(relatedTitle);
                listView.setVisibility(View.VISIBLE);
                defindedresult.setVisibility(View.INVISIBLE);
            }
        }
    };
    @Override
    public int getContentViewId() {
        return R.layout.searchwholemain;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        popInputSoft();
        addHistoryLable(history);
        addHotLable(hotlable);
        addViewListener();
    }

    private void popInputSoft() {
        getuserinput.setFocusable(true);
        getuserinput.setFocusableInTouchMode(true);
        getuserinput.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void addViewListener() {
        cancel_search.setOnClickListener(this);
        delete.setOnClickListener(this);
        //getuserinput.setOnKeyListener(this);
        getuserinput.addTextChangedListener(this);
        getuserinput.setOnEditorActionListener(this);
    }

    private void initData() {
        for(int i = 0; i < 12; i++){
            history.add("history"+i);
        }
        for(int i = 0; i < 16; i++){
            hotlable.add("hotlable"+i);
        }
//        for(int i = 0; i <16 ;i++){
//            if(i==0){
//                //relatedTitle.add()
//            }
//            relatedTitle.add(i+"测试");
//        }
    }

    private void addHistoryLable(List<String> history) {
        int width = 0;
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth()*5/7;
        HotSearchParent parent = null;
        HotSearchLable lable = null;
        for(int i = history.size()-1; i >= 0 ; i--){
            if(width == 0){
                parent = new HotSearchParent(this,null);
                lable = new HotSearchLable(this,null);
                lable.setTextView(history.get(i));
                historyParent.addView(parent);
                parent.addView(lable);
                lable.measure(w,h);
                width += lable.getMeasuredWidth();
            }else if(width > screenWidth){
                parent = new HotSearchParent(this,null);
                lable = new HotSearchLable(this,null);
                lable.setTextView(history.get(i));
                historyParent.addView(parent);
                parent.addView(lable);
                lable.measure(w,h);
                width = lable.getMeasuredWidth();
            }else {
                lable = new HotSearchLable(this,null);
                lable.setTextView(history.get(i));
                parent.addView(lable);
                lable.measure(w,h);
                width += lable.getMeasuredWidth();
            }
            lableAddListener(lable);
        }
    }

    private void addHotLable(List<String> hotlable) {
        int width = 0;
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth()*5/7;
        HotSearchParent parent = null;
        HotSearchLable lable = null;
        for(int i = hotlable.size()-1; i >= 0 ; i--){
            if(width == 0){
                parent = new HotSearchParent(this,null);
                lable = new HotSearchLable(this,null);
                lable.setTextView(hotlable.get(i));
                hotParent.addView(parent);
                parent.addView(lable);
                lable.measure(w,h);
                width += lable.getMeasuredWidth();
            }else if(width > screenWidth){
                parent = new HotSearchParent(this,null);
                lable = new HotSearchLable(this,null);
                lable.setTextView(hotlable.get(i));
                hotParent.addView(parent);
                parent.addView(lable);
                lable.measure(w,h);
                width = lable.getMeasuredWidth();
            }else {
                lable = new HotSearchLable(this,null);
                lable.setTextView(hotlable.get(i));
                parent.addView(lable);
                lable.measure(w,h);
                width += lable.getMeasuredWidth();
            }
            lableAddListener(lable);
        }
    }

    private void lableAddListener(final HotSearchLable lable) {
        lable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchMain.this,SearchWholeItem.class);
                intent.putExtra("lable",lable.getTextView());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.deletehistorysearch:

                break;
            case R.id.cancel_search:
                finish();
                break;
        }
    }

//    @Override
//    public boolean onKey(View view, int i, KeyEvent keyEvent) {
//
//        return true;
//    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!getuserinput.getText().toString().equals("")){
            final String ss = getuserinput.getText().toString();
            BmobQuery<Note> query = new BmobQuery<Note>();
            query.findObjects(new FindListener<Note>() {
                @Override
                public void done(List<Note> list, BmobException e) {
                    if (e==null){
                        List<Note> newList = new ArrayList<>();
                        for (Note note:list){
                            if (note.getTitle().contains(ss)){
                                newList.add(note);
                            }
                        }
                        if (newList.size()==0){
                            //Toast.makeText(InitBmob.getContext(),"结果不存在",Toast.LENGTH_SHORT).show();
                        }else {
                            //代码块

                            Message message = handler.obtainMessage();
                            message.what = 1;
                            message.obj = newList.size();
                            handler.sendMessage(message);
                        }
                    }else {
                        Log.i("bmob","模糊查询失败" + e.getErrorCode() + e.getMessage());
                    }
                }
            });
        }else {
            listView.setVisibility(View.INVISIBLE);
            defindedresult.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            return true;
        }
        return false;
    }
    public void setListView(List<String> relatedTitle){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.search_autotip,relatedTitle);
        listView.setAdapter(adapter);
    }
}
