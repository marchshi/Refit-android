package com.smq.demo5.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.adapter.OrderGetGuideAdapter;
import com.smq.demo5.entity.MyOrderEntity;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetGuideFragment extends BaseFragment {

    ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_guide,container,false);
        lv = (ListView) view.findViewById(R.id.lv_order_get_guide);
        initview();
        return view;
    }

    public void initview(){
        Map params = new HashMap();
        params.put("userId", Constant.userId);
        new XRequest(activity,"orderGetGuide",XRequest.GET,params).setOnRequestListener(new ListObjectRequestListener() {
            @Override
            public void success(String s) {
                List<MyOrderEntity> list = Utils.stringToArray(s,MyOrderEntity[].class);
                OrderGetGuideAdapter adapter = new OrderGetGuideAdapter(GetGuideFragment.this,list);
                lv.setAdapter(adapter);
            }

            @Override
            public void fail(String content) {

            }
        }).execute();
    }
}
