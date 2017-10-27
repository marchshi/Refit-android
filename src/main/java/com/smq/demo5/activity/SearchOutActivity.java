package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.smq.demo5.R;
import com.smq.demo5.adapter.SearchAdapter;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class SearchOutActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_search_out)
    TextView tv_search_out;
    @BindView(R.id.lv_search_out)
    ListView lv_search_out;

    String text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_search_out);
        bindOnClick(new int[]{R.id.rl_search_main,R.id.tl_sort1,R.id.tl_sort2,R.id.tl_sort3,R.id.tl_sort4},this);
        init();
    }

    public void init(){
        text = getIntent().getStringExtra("text");
        tv_search_out.setText(text);
        Map params = new HashMap();
        params.put("text",text);
        //TODO 向后台传递排序模式
        params.put("sortMode",1);
        new XRequest(this,"search",XRequest.GET,params).setOnRequestListener(new ListObjectRequestListener() {
            @Override
            public void success(String t) {
                List<CatInfoBean> cats = Utils.stringToArray(t,CatInfoBean[].class);
                showToast("找到"+cats.size()+"条信息");
                listview(cats);
            }

            @Override
            public void fail(String content) {

            }
        }).execute();

    }

    public void listview(List<CatInfoBean> cats){
        SearchAdapter adapter = new SearchAdapter(this,cats);
        lv_search_out.setAdapter(adapter);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_search_main:
                Intent intent = new Intent(this,SearchActivity.class);
                intent.putExtra("mode","search");
                startActivity(intent);
                finish();
                break;
        }
    }
}
