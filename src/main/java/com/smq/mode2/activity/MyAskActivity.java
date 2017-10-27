package com.smq.mode2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smq.mode2.R;
import com.smq.mode2.adapter.MyAskItemAdapter;
import com.smq.mode2.entity.ApplyViewEntity;
import com.smq.mode2.entity.AskViewEntity;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MyAskActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.lv_my_ask_item)
    ListView lv;

    public AskViewEntity askView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_my_ask);
        init();
        bindOnClick(new int[]{R.id.ib_finish},this);
        Intent intent = getIntent();
        askView = (AskViewEntity) intent.getSerializableExtra("askView");
        Map params = new HashMap();
        params.put("askId",askView.getAskId());
        new XRequest(this,"apply",XRequest.GET,params).setOnRequestListener(new BaseRequestListener() {
            @Override
            protected void success(String t) {
                try{
                    JSONObject json = new JSONObject(t);
                    if("success".equals(json.getJSONObject("message").getString("type"))){
                        List<ApplyViewEntity> list = new Gson().fromJson(json.getString("data"),new TypeToken<List<ApplyViewEntity>>(){}.getType());
                        listview(list);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            protected void fail(int code) {

            }
        }).execute();
    }

    public void listview(List<ApplyViewEntity> list){
        MyAskItemAdapter adapter = new MyAskItemAdapter(this,askView,list);
        lv.setAdapter(adapter);
    }

    public void init(){
        findViewById(R.id.ib_finish).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("我的提问");
        title.setTextSize(18);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_finish:
                finish();
                break;
        }
    }
}
