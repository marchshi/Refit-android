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
import com.smq.demo5.adapter.GuideFragmentAdapter;
import com.smq.demo5.entity.GuideFragmentEntity;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.Utils;

import java.util.List;

/**
 * Created by shimanqian on 2017/7/18.
 */

public class GuideFragment extends BaseFragment {

    ListView lv ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide,container,false);
        ((TextView)view.findViewById(R.id.tv_title)).setText("导购");
        ((TextView)view.findViewById(R.id.tv_title)).setTextSize(20);
        lv = (ListView) view.findViewById(R.id.lv_chat_list);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            init();
        }
    }

    public void init(){
        if(Constant.isLogin){
            new XRequest(activity,"guideFragment").setOnRequestListener(new ListObjectRequestListener() {
                @Override
                public void success(String s) {
                    List<GuideFragmentEntity> list = Utils.stringToArray(s,GuideFragmentEntity[].class);
                    //TODO 对list进行排序
                    GuideFragmentAdapter adapter = new GuideFragmentAdapter(activity,list,1);
                    lv.setAdapter(adapter);
                }

                @Override
                public void fail(String content) {

                }
            }).execute();
        }
    }

}
