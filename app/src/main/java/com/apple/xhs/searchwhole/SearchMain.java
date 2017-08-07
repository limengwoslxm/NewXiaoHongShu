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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apple.initbmob.InitBmob;
import com.apple.xhs.R;
import com.apple.xhs.custom_view.HotSearchLable;
import com.apple.xhs.custom_view.HotSearchParent;
import com.apple.xhs.note.NoteScan;
import com.base.BaseActivity;
import com.bean.Hot;
import com.bean.MyUser;
import com.bean.Note;
import com.data.AddDataBmob;
import com.data.DeleteDataBmob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by limeng on 2017/8/4.
 */

public class SearchMain extends BaseActivity implements View.OnClickListener, TextWatcher, TextView.OnEditorActionListener {
    //历史记录栏
    @BindView(R.id.historybar)
    RelativeLayout historybar;
    //历史记录标签父容器
    @BindView(R.id.historylable)
    LinearLayout historyParent;
    //热门搜索标签父容器
    @BindView(R.id.hotlable)
    LinearLayout hotParent;
    //清空历史记录
    @BindView(R.id.deletehistorysearch)
    ImageView delete;
    //用户输入editview
    @BindView(R.id.getuserinput)
    EditText getuserinput;
    //取消搜索
    @BindView(R.id.cancel_search)
            TextView cancel_search;
    //搜索提示的listview
    @BindView(R.id.searchlistview)
    ListView listView;
    //显示搜索标签的view
    @BindView(R.id.defindedresult)
            LinearLayout defindedresult;

    List<String> history = new ArrayList<>();
    List<Hot> hotlable = new ArrayList<>();
    List<String> gethot = new ArrayList<>();
    List<String> relatedTitle = new ArrayList<>();
    List<Note> noteList = new ArrayList<>();
    List<Note> newList ;
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
        listView.setVisibility(View.GONE);
    }

    private void initData() {
        BmobQuery<Hot> query2 = new BmobQuery<Hot>();
        query2.order("-number");
        query2.setLimit(16);
        query2.findObjects(new FindListener<Hot>() {
            @Override
            public void done(List<Hot> list, BmobException e) {
                if (e==null){
                    hotlable = list;
                    for(Hot hot : hotlable){
                        gethot.add(hot.getName());
                    }
                    addHotLable(gethot);
                }else {
                    Log.i("bmob","热门搜索查询失败" + e.getErrorCode() + e.getMessage());
                }
            }
        });
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        history = user.getHistory();
        if(history != null){
            addHistoryLable( history);
        }else {
            historybar.setVisibility(View.GONE);
        }

    }
    //历史搜素标签
    private void addHistoryLable(List<String> history) {
        int width = 0;
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth()*5/7;
        HotSearchParent parent = null;
        HotSearchLable lable = null;
        if(history == null){
            historyParent.setVisibility(View.GONE);
            return;
        }
        historyParent.setVisibility(View.VISIBLE);
        for(int i = history.size() - 1; i >= 0  ; i--){
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
    //热门搜索标签
    private void addHotLable(List<String> hotlable) {
        int width = 0;
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth()*5/7;
        HotSearchParent parent = null;
        HotSearchLable lable = null;
        for(int i = 0; i < hotlable.size() ; i++){
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
                historyParent.removeAllViews();
                historybar.setVisibility(View.GONE);
                DeleteDataBmob.deleteHistory();
                break;
            case R.id.cancel_search:
                finish();
                break;
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
        if(!getuserinput.getText().toString().equals("")){
            final String ss = getuserinput.getText().toString();
            BmobQuery<Note> query1 = new BmobQuery<Note>();
            query1.include("author");
            query1.findObjects(new FindListener<Note>() {
                @Override
                public void done(List<Note> list, BmobException e) {
                    if (e==null){
                        newList = new ArrayList<>();
                        for (Note note:list){
                            if (note.getTitle().contains(ss)){
                                newList.add(note);
                            }
                        }
                        if (newList.size()==0){
                            //Toast.makeText(InitBmob.getContext(),"结果不存在",Toast.LENGTH_SHORT).show();
                        }else {
                            //代码块
                            noteList = newList;
                            relatedTitle.clear();
                            relatedTitle.add("共有"+noteList.size()+"条相关笔记");
                            for(int i = 0 ; i < noteList.size();i++){
                                relatedTitle.add(noteList.get(i).getTitle());
                            }
                            setListView(relatedTitle);
                            listView.setVisibility(View.VISIBLE);
                            defindedresult.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        Log.i("bmob","模糊查询失败" + e.getErrorCode() + e.getMessage());
                    }
                }
            });
        }else {
            listView.setVisibility(View.GONE);
            defindedresult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        String ss = getuserinput.getText().toString();
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            AddDataBmob.addHistory(ss);
            AddDataBmob.addHot(ss);
            Intent intent = new Intent(SearchMain.this,SearchWholeItem.class);
            intent.putExtra("lable",getuserinput.getText().toString());
            startActivity(intent);
            return true;
        }
        return false;
    }
    public void setListView(List<String> relatedTitle){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.search_autotip,relatedTitle);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    Intent intent = new Intent(SearchMain.this, SearchWholeItem.class);
                    intent.putExtra("notelist", (Serializable) noteList);
                    intent.putExtra("lable","allrelated");
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SearchMain.this, NoteScan.class);
                    intent.putExtra("userdata",noteList.get(i-1));
                    startActivity(intent);
                }
            }
        });
    }
}
