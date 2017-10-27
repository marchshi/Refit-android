package com.smq.demo5.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.adapter.CatAdapter;

/**
 * Created by shimanqian on 2017/7/18.
 */

public class AdminFragment extends BaseFragment{

    CatAdapter adapter;
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin,container,false);
        lv = (ListView) view.findViewById(R.id.lv_cat_admin);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        title.setText("管理列表");
        adapter = new CatAdapter(activity, Constant.cats);
        lv.setAdapter(adapter);
        return view;
    }

    public void refresh(){
        adapter = new CatAdapter(activity, Constant.cats);
        lv.setAdapter(adapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            refresh();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }
}
