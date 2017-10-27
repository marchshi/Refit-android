package com.smq.mode2.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.activity.EditAskActivity;
import com.smq.mode2.adapter.MyAskAdapter;
import com.smq.mode2.entity.AskViewEntity;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAskFragment extends BaseFragment {

    @BindView(R.id.ask_nothing)
    LinearLayout ask_nothing;
    @BindView(R.id.lv_my_ask)
    ListView lv_my_ask;
    @BindView(R.id.tv_add_ask)
    TextView tv_add_ask;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_ask, container, false);
        ButterKnife.bind(this,view);
        tv_add_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditAskActivity.class);
                activity.startActivity(intent);
            }
        });
        if(Constant.isLogin) {
            init();
        }else{
            ask_nothing.setVisibility(View.VISIBLE);
            lv_my_ask.setVisibility(View.GONE);
        }
        return view;
    }

    public void refresh(){
        if(Constant.isLogin) {
            init();
        }else{
            ask_nothing.setVisibility(View.VISIBLE);
            lv_my_ask.setVisibility(View.GONE);
        }
    }

    public void init(){
        Map params = new HashMap();
        params.put("t","my");
        new XRequest(activity,"ask",XRequest.GET,params).setOnRequestListener(new BaseRequestListener() {
            @Override
            protected void success(String t) {
                try{
                    JSONObject json = new JSONObject(t);
                    if("success".equals(json.getJSONObject("message").getString("type"))){
                        List<AskViewEntity> list = new Gson().fromJson(json.getString("data"),new TypeToken<List<AskViewEntity>>(){}.getType());
                        listview(list);
                    }else{

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

    public void listview(List<AskViewEntity> list){
        if(!list.isEmpty()){
            lv_my_ask.setVisibility(View.VISIBLE);
            ask_nothing.setVisibility(View.GONE);
            MyAskAdapter adapter = new MyAskAdapter(activity,list);
            lv_my_ask.setAdapter(adapter);
        }
    }
}
