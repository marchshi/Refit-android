package com.smq.mode2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.entity.CatInfoEntity;
import com.smq.mode2.entity.CatTableEntity;
import com.smq.mode2.fragment.AdminFragment;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;
import com.smq.mode2.util.CatMateUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public class EditCatActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_cat_name)
    TextView tv_cat_name;
    @BindView(R.id.et_cat_label)
    EditText et_cat_label;
    @BindView(R.id.et_cat_desc)
    EditText et_cat_desc;
    @BindView(R.id.et_cat_price)
    EditText et_cat_price;
    @BindView(R.id.sw_cat_cont)
    Switch sw_cat_cont;

    @BindView(R.id.et_cat_smodel)
    EditText et_cat_smodel;

    private CatInfoEntity catOld ;
    private CatInfoEntity catNew = new CatInfoEntity();
    private CatTableEntity cat ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_edit_cat);
        CatInfoEntity catOld = (CatInfoEntity) getIntent().getSerializableExtra("catInfo");
        if(catOld != null){
            findViewById(R.id.ib_finish).setVisibility(View.VISIBLE);
            TextView title = (TextView) findViewById(R.id.tv_title);
            title.setText("修改");
            title.setTextSize(18);
            tv_cat_name.setText(catOld.getCategory());
            et_cat_label.setText(catOld.getLabel());
            et_cat_desc.setText(catOld.getCatDesc());
            if("1".equals(catOld.getCont())){
                sw_cat_cont.setChecked(true);
            }else{
                sw_cat_cont.setChecked(false);
            }
            et_cat_price.setText(catOld.getPrice());
            catNew.setCatId(catOld.getCatId());
        }
        String category = getIntent().getStringExtra("category");
        if(category !=null){
            tv_cat_name.setText(category);
            et_cat_label.requestFocus();
        }
        initview();
        bindOnClick(new int[]{R.id.ib_finish,R.id.ib_cat_append,R.id.tv_cat_name},this);
    }

    private void initview(){
        findViewById(R.id.ib_finish).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("添加");
        title.setTextSize(18);
        //对金钱输入的数字范围进行控制
        et_cat_price.setKeyListener(new DigitsKeyListener(false,false));
        et_cat_price.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                char[] numberChars={'1','2','3','4','5','6','7','8','9','0'};
                return numberChars;
            }
            @Override
            public int getInputType() {
                return android.text.InputType.TYPE_CLASS_PHONE;
            }
        });
    }

    private void catAppend(){
        String category =  tv_cat_name.getText().toString();
        String label = et_cat_label.getText().toString().trim();
        String catDesc = et_cat_desc.getText().toString();
        String smodel = "0";
        String cont = "1";
        if(!sw_cat_cont.isChecked()){
            cont = "0";
        }
        String price = et_cat_price.getText().toString();
        //判断用户是否将需要的信息全部输入
        if(TextUtils.isEmpty(category) || TextUtils.isEmpty(label) || TextUtils.isEmpty(catDesc) || TextUtils.isEmpty(price)){
            showToast("请将信息输入完整");
            return;
        }
        catNew.setCategory(category);
        catNew.setLabel(label);
        catNew.setCatDesc(catDesc);
        catNew.setCont(cont);
        catNew.setPrice(price);
        catNew.setSmodel(smodel);
        String json = new Gson().toJson(catNew);
        Map params = new HashMap();
        params.put("info",json);
        new XRequest(this,"editCat?a=save",XRequest.POST,params).setOnRequestListener(new BaseRequestListener() {
            @Override
            protected void success(String t) {
                try{
                    JSONObject json = new JSONObject(t);
                    if("success".equals(json.getJSONObject("message").getString("type"))){
                        showToast("保存成功");
                        addToList();
                        finish();
                    }else {
                        showToast("保存失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            protected void fail(int code) {
                showToast("保存失败");
            }
        }).execute();
    }

    public void addToList(){
        AdminFragment.list.add(catNew);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_finish:
                finish();
                break;
            case R.id.ib_cat_append:
                catAppend();
                break;
            case R.id.tv_cat_name:
                Intent intent = new Intent(this,SearchActivity.class);
                intent.putExtra("mode","choose");
                startActivityForResult(intent, Constant.CHOOSE_CAT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.CHOOSE_CAT && resultCode == 200){
                 cat = (CatTableEntity) data.getSerializableExtra("catTable");
                String catInfo = CatMateUtil.getParentName(cat)+" / "+cat.getName();
                tv_cat_name.setText(catInfo);
        }
    }
}
