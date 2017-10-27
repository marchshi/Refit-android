package com.smq.mode2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.entity.UserInfoEntity;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class FirstTwoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_first_prof)
    EditText et_first_prof;
    @BindView(R.id.et_first_resume)
    EditText et_first_resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_first_two);
        init();
        bindOnClick(new int[]{R.id.tv_first_finish,R.id.ib_finish},this);
    }

    private void init(){
        findViewById(R.id.ib_finish).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("完善信息");
        title.setTextSize(18);
    }

    private void firstFinish(){
        String profession = et_first_prof.getText().toString().trim();
        String resume = et_first_resume.getText().toString();
        String name = getIntent().getStringExtra("name");
        if(!TextUtils.isEmpty(profession)){
            UserInfoEntity entity = new UserInfoEntity();
            entity.setName(name);
            entity.setprofession(profession);
            if(!TextUtils.isEmpty(resume)){
                entity.setResume(resume);
            }
            String info = new Gson().toJson(entity);
            Map<String,Object> params = new HashMap<>();
            params.put("info",info);
            new XRequest(this,"info?a=save",XRequest.POST,params).setOnRequestListener(new BaseRequestListener() {
                @Override
                protected void success(String t) {
                    try {
                        JSONObject json = new JSONObject(t);
                        if("success".equals(json.getJSONObject("message").getString("type"))){
                            Constant.isLogin = true;
                            Intent intent = new Intent(FirstTwoActivity.this,LoginActivity.class);
                            startActivity(intent);
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
        }else{
            showToast("职业不能为空");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_first_finish:
                firstFinish();
                break;
            case R.id.ib_finish:
                finish();
                break;
        }
    }
}
