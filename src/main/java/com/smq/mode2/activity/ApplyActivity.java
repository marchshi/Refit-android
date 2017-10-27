package com.smq.mode2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.adapter.ApplyAdapter;
import com.smq.mode2.entity.ApplyViewEntity;
import com.smq.mode2.entity.AskViewEntity;
import com.smq.mode2.entity.CatInfoEntity;
import com.smq.mode2.fragment.AdminFragment;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplyActivity extends BaseActivity implements View.OnClickListener {

    private Intent intent = new Intent();

    private AskViewEntity entity;
    private List<ApplyViewEntity> applyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_apply);
        init();
        bindOnClick(new int[]{R.id.ib_finish},this);
        initview();
    }

    private void init(){
        findViewById(R.id.ib_finish).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("匿名用户的提问");
        title.setTextSize(18);
    }

    private void initview(){
        intent = getIntent();
        entity = (AskViewEntity) intent.getSerializableExtra("askView");
        //获取申请信息
        Map params = new HashMap();
        params.put("askId",entity.getAskId());
        new XRequest(this,"apply",XRequest.GET,params).setOnRequestListener(new BaseRequestListener() {
            @Override
            protected void success(String t) {
                try{
                    JSONObject json = new JSONObject(t);
                    if("success".equals(json.getJSONObject("message").getString("type"))){
                        applyList= new Gson().fromJson(json.getString("data"),
                                new TypeToken<List<ApplyViewEntity>>() {}.getType());
                        listView(applyList);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            protected void fail(int code) {
                showToast("网络异常");
            }
        }).execute();
    }

    private void listView(List<ApplyViewEntity> list){
        ListView lv = (ListView) findViewById(R.id.lv_apply);
        lv.setVisibility(View.VISIBLE);
        ApplyAdapter adapter = new ApplyAdapter(this,entity,list);
        lv.setAdapter(adapter);
    }

    public void checkApply(){
        /**
         * 1,判断是否登陆 没有登陆要跳转到登陆界面
         * 2,判断用户是否有该类目 没有跳转至添加类目
         * 3,发送申请
         */
        if(!Constant.isLogin){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            return;
        }
        String catId = "-1";
        String userId = "-1";
        for (CatInfoEntity cat: AdminFragment.list){
            if(cat.getCategory().equals(entity.getCategory())){
                catId = cat.getCatId();
                userId = cat.getUserId();
            }
        }
        if("-1".equals(catId)){
            Intent intent = new Intent(this,EditCatActivity.class);
            intent.putExtra("category",entity.getCategory());
            startActivity(intent);
            return;
        }
        for(ApplyViewEntity apply:applyList){
            if(userId.equals(apply.getUserId())){
                showToast("您已申请");
                return;
            }
        }
        apply(catId);
    }

    public void apply(String catId){
        Map params = new HashMap();
        params.put("askId",entity.getAskId());
        params.put("catId",catId);
        new XRequest(this,"apply",XRequest.POST,params).setOnRequestListener(new BaseRequestListener() {
            @Override
            protected void success(String t) {
                try {
                    JSONObject json = new JSONObject(t);
                    if("success".equals(json.getJSONObject("message").getString("type"))){
                        showToast("申请成功");
                        //TODO 更新申请人列表
                        initview();
                    }else{
                        showToast("申请失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            protected void fail(int code) {
                showToast("申请失败");
            }
        }).execute();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_finish:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constant.NEED_LOGIN:
//                checkApply();
                break;
            case Constant.NEED_APPLY_CAT:
//                checkApply();
                break;
        }
    }
}
