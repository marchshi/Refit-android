package com.smq.mode2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.adapter.SearchHisAdapter;
import com.smq.mode2.adapter.SearchTipsAdapter;
import com.smq.mode2.entity.CatTableEntity;
import com.smq.mode2.util.CatMateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_search_his)
    ListView lv_search_his;
    @BindView(R.id.lv_search_tips)
    ListView lv_search_tips;
    @BindView(R.id.et_search_input)
    EditText et_search_input;
    @BindView(R.id.img_search_clear)
    ImageView img_search_clear;
    @BindView(R.id.tv_search_search)
    TextView tv_search_search;

    List<CatTableEntity> tipsList = new ArrayList<CatTableEntity>(6);

    private String mode = "";

    /**
     * 这个界面有2个模式：
     *  1，用于模糊搜索
     *  2，用于选择类目
     * 根据intent里的mode字段进行判断 将这个界面进行区分
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_search);
        findViewById(R.id.tab_rl_root).setVisibility(View.GONE);
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        if("choose".equals(mode)){
            et_search_input.setHint("类 目 名");
            tv_search_search.setText("确定");
        }else{
            tv_search_search.setText("搜索");
            bindOnClick(new int[]{R.id.tv_search_search},this);
        }
        bindOnClick(new int[]{R.id.ib_search_finish,R.id.img_search_clear,R.id.tv_search_clear_his},this);
        init();
    }

    public void init(){
        SearchHisAdapter adapter = new SearchHisAdapter(this, Constant.search_his);
        lv_search_his.setAdapter(adapter);

//        final ArrayAdapter tipsAdapter = new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,tipsList);;
        final SearchTipsAdapter tipsAdapter = new SearchTipsAdapter(this,tipsList);
        lv_search_tips.setAdapter(tipsAdapter);
        lv_search_tips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public   void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //text就是点击的类目名称
                CatTableEntity cat = (CatTableEntity) lv_search_tips.getAdapter().getItem(position);
                et_search_input.setText(cat.getName());
                //设置的etinput的光标位置为最后
                et_search_input.setSelection(cat.getName().length());
                lv_search_tips.setVisibility(View.GONE);
                //进行下一个界面的搜索及显示操作
                action(cat);
            }
        });
        lv_search_his.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //text就是点击的类目名称
                CatTableEntity cat = (CatTableEntity) lv_search_his.getAdapter().getItem(position);
                et_search_input.setText(cat.getName());
                //设置的etinput的光标位置为最后
                et_search_input.setSelection(cat.getName().length());
                lv_search_his.setVisibility(View.GONE);
                //进行下一个界面的搜索及显示操作
                action(cat);
            }
        });
        et_search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!"".equals(s.toString())) {
                    img_search_clear.setVisibility(View.VISIBLE);
                    lv_search_tips.setVisibility(View.VISIBLE);
                    //更新autoComplete数据
                    tipsList.clear();
                    CatMateUtil.getMarch(s+"");
                    tipsList.addAll(CatMateUtil.getMarch(s+""));
                    tipsAdapter.notifyDataSetChanged();
                } else {
                    img_search_clear.setVisibility(View.GONE);
                    lv_search_tips.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        if("search".equals(mode)){
            et_search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        search(et_search_input.getText().toString());
                    }
                    return false;
                }
            });
        }
    }

    public void action(CatTableEntity cat){
        if("search".equals(mode)){
            addHistory(cat);
            search(cat.getName());
        }else if ("choose".equals(mode)){
            Intent intent = new Intent();
            intent.putExtra("catTable",cat);
            setResult(200,intent);
            finish();
        }
    }

    public void search(String text){
        if(!TextUtils.isEmpty(text) && CatMateUtil.isContains(text)){
            Intent intent = new Intent(this,SearchOutActivity.class);
            intent.putExtra("category",text);
            startActivity(intent);
            finish();
        }else {
            showToast("请选择正确的类目");
        }
    }

    public void addHistory(CatTableEntity cat){
        if(!Constant.search_his.contains(cat)){
            Constant.search_his.add(cat);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_search_finish:
                finish();
                break;
            case R.id.tv_search_clear_his:
                Constant.search_his.clear();
                init();
                break;
            case R.id.tv_search_search:
                search(et_search_input.getText().toString());
                break;
            case R.id.img_search_clear:
                et_search_input.setText("");
                img_search_clear.setVisibility(View.GONE);
                break;
        }
    }
}
