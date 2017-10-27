package com.smq.mode2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.activity.EditCatActivity;
import com.smq.mode2.activity.LoginActivity;
import com.smq.mode2.adapter.CatAdapter;
import com.smq.mode2.entity.CatInfoEntity;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class AdminFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_add_cate)
    TextView tv_add_cate;

    public static List<CatInfoEntity> list =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_admin_pager,container,false );
        view.findViewById(R.id.tv_add_cate).setOnClickListener(this);
        initview();
        return view;
    }

    public void refresh(){
        initview();

    }

    @Override
    public void onStart() {
        /**
         * 1，默认显示添加界面
         * 2，如果用户登录了，则根据获取到的内容刷新界面
         */
        if(Constant.isLogin){
            Map params = new HashMap();
            params.put("a","get");
            new XRequest(activity,"editCat",XRequest.GET,params).setOnRequestListener(new BaseRequestListener() {
                @Override
                protected void success(String t) {
                    /**
                     * 这里要根据返回的结果决定网页显示什么内容
                     * http请求是异步的
                     */
                    try{
                        JSONObject json = new JSONObject(t);
                        if("success".equals(json.getJSONObject("message").getString("type"))){
                            Log.d("aaa", json.getString("data"));
                            list= new Gson().fromJson(json.getString("data"),
                                    new TypeToken<List<CatInfoEntity>>() {}.getType());
                            if(list.isEmpty()){
                                activity.showToast("添加一个导购类目吧~");
                            }else{
                                listView();
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
        }else{
            activity.findViewById(R.id.admin_nothing).setVisibility(View.VISIBLE);
            ListView lv = (ListView) activity.findViewById(R.id.admin_catlist);
            lv.setVisibility(View.GONE);
        }
        super.onStart();
    }

    private void listView(){
        activity.findViewById(R.id.admin_nothing).setVisibility(View.GONE);
        ListView lv = (ListView) activity.findViewById(R.id.admin_catlist);
        lv.setVisibility(View.VISIBLE);
        CatAdapter adapter = new CatAdapter(activity,list);
        lv.setAdapter(adapter);
    }

    public void initview(){
        activity.findViewById(R.id.tab_rl_root).setVisibility(View.VISIBLE);
        TextView title = (TextView) activity.findViewById(R.id.tv_title);
        title.setText("管理");
        title.setTextSize(20);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_cate:
                if(Constant.isLogin) {
                    Intent intent = new Intent(activity, EditCatActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                }
        }
    }
}
