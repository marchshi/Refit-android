package com.smq.demo5.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.adapter.MyAskAdapter;
import com.smq.demo5.bean.ApplyInfoBean;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MyAskFragment extends BaseFragment {

    ListView lv_ask_my;
    MyAskAdapter adpter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_ask,container,false);
        lv_ask_my = (ListView) view.findViewById(R.id.lv_ask_my);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            refresh();
        }
    }

    public void refresh(){
        final List<AskInfoBean> asks = new ArrayList<>();
        for(final AskInfoBean ask : Constant.asks){
            Map params = new HashMap();
            params.put("askId",ask.getAskId());
            new XRequest(activity,"apply",XRequest.GET,params).setOnRequestListener(new ListObjectRequestListener() {
                @Override
                public void success(String s) {
                    List<ApplyInfoBean> applys = Utils.stringToArray(s,ApplyInfoBean[].class);
                    ask.setApplys(new HashSet<ApplyInfoBean>(applys));
                    asks.add(ask);
                    if(asks.size() == Constant.asks.size())
                        initView();
                }
                @Override
                public void fail(String content) {

                }
            }).execute();
        }
    }

    public void initView(){
        adpter = new MyAskAdapter(activity,Constant.asks);
        lv_ask_my.setAdapter(adpter);
    }

}
