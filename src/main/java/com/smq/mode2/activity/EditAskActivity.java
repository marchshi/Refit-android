package com.smq.mode2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.entity.AskInfoEntity;
import com.smq.mode2.entity.AskViewEntity;
import com.smq.mode2.entity.CatTableEntity;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;
import com.smq.mode2.util.CatMateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.smq.mode2.R.id.tv_title_right;

public class EditAskActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_ask_desc)
    EditText et_ask_desc;
    @BindView(R.id.tv_ask_cat)
    TextView tv_ask_cat;
    @BindView(R.id.et_ask_price)
    EditText et_ask_price;
    @BindView(R.id.sw_ask_sta)
    Switch sw_ask_sta;
    @BindView(R.id.sw_ask_no)
    Switch sw_ask_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_edit_ask);
        init();
        bindOnClick(new int[]{tv_title_right,R.id.ib_finish,R.id.tv_ask_cat},this);
        initview();
    }

    public void initview(){
        Intent intent = getIntent();
        AskViewEntity askView = (AskViewEntity) intent.getSerializableExtra("askView");
        if(askView!=null){
            et_ask_desc.setText(askView.getAskCont());
            tv_ask_cat.setText(askView.getCategory());
            et_ask_price.setText(askView.getRefPrice());
            if("0".equals(askView.getAskStatus())){
                sw_ask_sta.setChecked(false);
            }else{
                sw_ask_sta.setChecked(true);
            }
            if("1".equals(askView.getNoName())){
                sw_ask_no.setChecked(true);
            }else{
                sw_ask_no.setChecked(false);
            }
            TextView right = (TextView) findViewById(R.id.tv_title_right);
            right.setText("保存");
        }
    }

    public void init(){
        findViewById(R.id.ib_finish).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("添加");
        title.setTextSize(18);
        TextView right = (TextView) findViewById(tv_title_right);
        right.setVisibility(View.VISIBLE);
        right.setText("发布");
        //对金钱输入的数字范围进行控制
        et_ask_price.setSelection(et_ask_price.getText().toString().trim().length());
        et_ask_price.setKeyListener(new DigitsKeyListener(false,false));
        et_ask_price.setKeyListener(new NumberKeyListener() {
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
        //对提问的状态进行监听
        sw_ask_sta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    sw_ask_sta.setText("提问开启中");
                else
                    sw_ask_sta.setText("提问已关闭");
            }
        });
    }

    public void save(){
        String askCont = et_ask_desc.getText().toString();
        String category = tv_ask_cat.getText().toString();
        String refPrice = et_ask_price.getText().toString();
        String askStatus = sw_ask_sta.isChecked()?"1":"0";
        String noName = sw_ask_no.isChecked()?"1":"0";
        if(!TextUtils.isEmpty(askCont) && !TextUtils.isEmpty(category) && !TextUtils.isEmpty(refPrice)){
            Map params = new HashMap();
            params.put("a","add");
            AskInfoEntity entity = new AskInfoEntity();
            entity.setAskCont(askCont);
            entity.setCategory(category);
            entity.setRefPrice(refPrice);
            entity.setAskStatus(askStatus);
            entity.setNoName(noName);
            String info = new Gson().toJson(entity);
            params.put("info",info);
            new XRequest(this,"ask",XRequest.POST,params).setOnRequestListener(new BaseRequestListener() {
                @Override
                protected void success(String t) {
                    try {
                        JSONObject json = new JSONObject(t);
                        if("success".equals(json.getJSONObject("message").getString("type"))){
                            showToast("保存成功");
                            finish();
                        }else{
                            showToast("保存失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                protected void fail(int code) {
                    showToast("保存失败");
                }
            }).execute();
        }
    }

    @Override
    public void onClick(View v) {
        //保存发布的信息
        switch (v.getId()){
            case tv_title_right:
                save();
                break;
            case R.id.ib_finish:
                finish();
                break;
            case R.id.tv_ask_cat:
                Intent intent = new Intent(this,SearchActivity.class);
                intent.putExtra("mode","choose");
                startActivityForResult(intent, Constant.CHOOSE_CAT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.CHOOSE_CAT && resultCode == 200){
            CatTableEntity cat = (CatTableEntity) data.getSerializableExtra("catTable");
            String catInfo = CatMateUtil.getParentName(cat)+" / "+cat.getName();
            tv_ask_cat.setText(catInfo);
        }
    }
}
