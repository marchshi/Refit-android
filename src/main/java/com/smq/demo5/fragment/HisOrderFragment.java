package com.smq.demo5.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smq.demo5.R;
import com.smq.demo5.adapter.OrderHisAdapter;
import com.smq.demo5.entity.MyOrderEntity;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.Utils;

import java.util.List;

public class HisOrderFragment extends BaseFragment {

    ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_his_order,container,false);
        lv = (ListView) view.findViewById(R.id.lv_order_his);
        initview();
        return view;
    }

    public void initview(){
        new XRequest(activity,"orderHisGuide").setOnRequestListener(new ListObjectRequestListener() {
            @Override
            public void success(String s) {
                List<MyOrderEntity> list = Utils.stringToArray(s,MyOrderEntity[].class);
                OrderHisAdapter adapter = new OrderHisAdapter(HisOrderFragment.this,list);
                lv.setAdapter(adapter);
            }

            @Override
            public void fail(String content) {

            }
        }).execute();
    }
}
