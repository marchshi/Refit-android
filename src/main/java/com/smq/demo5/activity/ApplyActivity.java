package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.adapter.ApplyAdapter;
import com.smq.demo5.bean.ApplyInfoBean;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ApplyActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tv_title;

    private Intent intent = new Intent();
    private AskInfoBean bean;
    private List<ApplyInfoBean> applyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_apply);
        bean = (AskInfoBean) getIntent().getSerializableExtra("askInfo");
        init();
        bindOnClick(new int[]{R.id.img_title_back},this);
        initview();
    }

    private void init(){
        findViewById(R.id.img_title_back).setVisibility(View.VISIBLE);
        tv_title.setTextSize(18);
        if(bean.isAnonymity()){
            tv_title.setText("匿名用户的提问");
        }else {
            tv_title.setText(bean.getUser().getName()+"的提问");
        }
    }

    private void initview(){
        //获取申请信息
        Map params = new HashMap();
        params.put("askId",bean.getAskId());
        new XRequest(this,"apply",XRequest.GET,params).setOnRequestListener(new ListObjectRequestListener() {
            @Override
            public void success(String t) {
                applyList = Utils.stringToArray(t,ApplyInfoBean[].class);
                listView(applyList);
            }

            @Override
            public void fail(String content) {

            }
        }).execute();
    }

    private void listView(List<ApplyInfoBean> list){
        ListView lv = (ListView) findViewById(R.id.lv_apply);
        lv.setVisibility(View.VISIBLE);
        ApplyAdapter adapter = new ApplyAdapter(this,bean,list);
        lv.setAdapter(adapter);
    }

    public void checkApply(){
        /**
         * 1,判断是否登陆 没有登陆要跳转到登陆界面
         * 2,判断用户是否有该类目 没有跳转至添加类目 自己不能申请自己
         * 3,发送申请
         */
        if(!Constant.isLogin){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            return;
        }
        if(bean.getUserId() == Constant.userId){
            showToast("自己不能申请自己");
            return;
        }
        int catId = -1;
        for (CatInfoBean cat: Constant.cats){
            if(cat.getNameId() == bean.getNameId()){
                catId = cat.getCatId();
                break;
            }
        }
        if(catId == -1){
            Intent intent = new Intent(this,EditCatActivity.class);
            intent.putExtra("category",bean.getNameId());
            startActivity(intent);
            return;
        }
        for(ApplyInfoBean apply:applyList){
            if(apply.getCatId() == catId){
                showToast("您已申请");
                return;
            }
        }
        apply(catId);
    }

    public void apply(int catId){
        ApplyInfoBean apply = new ApplyInfoBean();
        apply.setAskId(bean.getAskId());
        apply.setCatId(catId);
        apply.setApplyDate(System.currentTimeMillis());
        apply.setStatus(1);
        new XRequest(this,"apply",XRequest.POST,apply).setOnRequestListener(new ListObjectRequestListener() {
            @Override
            public void success(String s) {
                showToast("申请成功");
                applyList = Utils.stringToArray(s,ApplyInfoBean[].class);
                listView(applyList);
            }
            @Override
            public void fail(String content) {

            }
        }).execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                break;
        }
    }

}
