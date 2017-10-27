package com.smq.demo5.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.activity.EditAskActivity;
import com.smq.demo5.activity.LoginActivity;
import com.smq.demo5.activity.SearchActivity;
import com.smq.demo5.adapter.AskAdapter;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.Utils;
import com.smq.demo5.view.RefreshableView;

/**
 * Created by shimanqian on 2017/7/18.
 */

public class MainFragment extends BaseFragment {
    private int page = 0;
    RefreshableView refresh_main;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false );
        FrameLayout search = (FrameLayout) view.findViewById(R.id.fl_search_main);
        TextView tv_main_ask = (TextView) view.findViewById(R.id.tv_ask_main);
        refresh_main = (RefreshableView) view.findViewById(R.id.refresh_main);
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
                if(Constant.isLogin){
                    Intent intent = new Intent(activity, EditAskActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivityForResult(intent,Constant.NEED_LOGIN);
                }
            }
        });
        refresh_main.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refresh_main.finishRefreshing();
            }
        }, 0);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    public void refresh(){
        initView();
    }

    private void initView(){
        new XRequest(activity,"askList").setOnRequestListener(new ListObjectRequestListener() {
            @Override
            public void success(String t) {
                Constant.askList = Utils.stringToArray(t,AskInfoBean[].class);
                listView();
            }

            @Override
            public void fail(String content) {
                activity.showToast("网络异常");
            }

        }).execute();
    }

    private void listView(){
        ListView lv = (ListView) activity.findViewById(R.id.lv_ask_main);
        lv.setVisibility(View.VISIBLE);
        AskAdapter adapter = new AskAdapter(activity,Constant.askList);
        lv.setAdapter(adapter);
    }
}
