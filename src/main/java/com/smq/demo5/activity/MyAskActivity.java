package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.adapter.MyAskItemAdapter;
import com.smq.demo5.bean.ApplyInfoBean;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class MyAskActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_my_ask_item)
    ListView lv;

    public int askId;

    public AskInfoBean askInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_my_ask);
        init();
        bindOnClick(new int[]{R.id.img_title_back},this);
        Intent intent = getIntent();
        askId = intent.getIntExtra("askId",0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    public void initView(){
        for(AskInfoBean ask:Constant.asks){
            if(ask.getAskId() ==askId){
                askInfo = ask;
                break;
            }
        }
        Map params = new HashMap();
        params.put("askId",askInfo.getAskId());
        new XRequest(this,"apply",XRequest.GET,params).setOnRequestListener(new ListObjectRequestListener() {
            @Override
            public void success(String s) {
                List<ApplyInfoBean> applys = Utils.stringToArray(s,ApplyInfoBean[].class);
                listview(applys);
            }
            @Override
            public void fail(String content) {

            }
        }).execute();
    }

    public void listview(List<ApplyInfoBean> list){
        MyAskItemAdapter adapter = new MyAskItemAdapter(this,askInfo,list);
        lv.setAdapter(adapter);
    }

    public void init(){
        findViewById(R.id.img_title_back).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("我的提问");
        title.setTextSize(18);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                break;
        }
    }
}
