package com.apple.xhs.searchwhole;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apple.xhs.R;
import com.apple.xhs.custom_view.HotSearchLable;
import com.apple.xhs.custom_view.HotSearchParent;
import com.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    List<String> history = new ArrayList<>();
    List<String> hotlable = new ArrayList<>();
    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
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

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            return true;
        }
        return false;
    }
}
