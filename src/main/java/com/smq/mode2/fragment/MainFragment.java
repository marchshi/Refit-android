package com.smq.mode2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.activity.EditAskActivity;
import com.smq.mode2.activity.SearchActivity;
import com.smq.mode2.adapter.AskAdapter;
import com.smq.mode2.entity.AskViewEntity;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainFragment extends BaseFragment {

    private int page = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_pager,container,false );
        FrameLayout search = (FrameLayout) view.findViewById(R.id.bt_search_main);
        TextView tv_main_ask = (TextView) view.findViewById(R.id.tv_main_ask);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SearchActivity.class);
                intent.putExtra("mode","search");
                startActivityForResult(intent, Constant.SEARCH_CAT);
            }
        });
        tv_main_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditAskActivity.class);
                startActivity(intent);
            }
        });
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh(){
        initView();
    }

    private void initView(){
        Map params = new HashMap();
        params.put("page",page);
        params.put("t","top");
        new XRequest(activity,"ask",XRequest.GET,params).setOnRequestListener(new BaseRequestListener() {
            @Override
            protected void success(String t) {
                try{
                    JSONObject json = new JSONObject(t);
                    if("success".equals(json.getJSONObject("message").getString("type"))){
                        List<AskViewEntity> list= new Gson().fromJson(json.getString("data"),
                                new TypeToken<List<AskViewEntity>>() {}.getType());
                        if(list.isEmpty()){
                            activity.showToast("问题怎么会问空呢~");
                        }else{
                            listView(list);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            protected void fail(int code) {
                activity.showToast("网络异常");
            }
        }).execute();
    }

    private void listView(List<AskViewEntity> list){
        ListView lv = (ListView) activity.findViewById(R.id.lv_main_ask);
        lv.setVisibility(View.VISIBLE);
        AskAdapter adapter = new AskAdapter(activity,list);
        lv.setAdapter(adapter);
    }

}
