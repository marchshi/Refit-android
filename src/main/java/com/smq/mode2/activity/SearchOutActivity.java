package com.smq.mode2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smq.mode2.R;
import com.smq.mode2.adapter.SearchAdapter;
import com.smq.mode2.entity.CatViewEntity;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class SearchOutActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_search_out)
    TextView tv_search_out;
    @BindView(R.id.lv_search_out)
    ListView lv_search_out;

    String category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_search_out);
        bindOnClick(new int[]{R.id.bt_search_out,R.id.tl_sort1,R.id.tl_sort2,R.id.tl_sort3,R.id.tl_sort4},this);
        init();
    }

    public void init(){
        findViewById(R.id.tab_rl_root).setVisibility(View.GONE);
        category = getIntent().getStringExtra("category");
        tv_search_out.setText(category);
        Map params = new HashMap();
        params.put("category",category);
        //TODO 向后台传递排序模式
        params.put("sortMode",1);
        new XRequest(this,"search",XRequest.GET,params).setOnRequestListener(new BaseRequestListener() {

            @Override
            protected void success(String t) {
                try {
                    JSONObject json = new JSONObject(t);
                    if("success".equals(json.getJSONObject("message").getString("type"))){
                        List list = new Gson().fromJson(json.getString("data"),new TypeToken<List<CatViewEntity>>(){}.getType());
                        showToast("找到"+list.size()+"条信息");
                        listview(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void fail(int code) {

            }
        }).execute();

    }

    public void listview(List<CatViewEntity> list){
        SearchAdapter adapter = new SearchAdapter(this,list);
        lv_search_out.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search_out:
                Intent intent = new Intent(this,SearchActivity.class);
                intent.putExtra("category",category);
                startActivity(intent);
                break;

        }
    }
}
