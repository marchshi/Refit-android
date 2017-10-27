package com.smq.mode2.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;
import com.smq.mode2.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_sjh)
    EditText et_sjh;
    @BindView(R.id.tv_get)
    TextView tv_get;
    @BindView(R.id.et_yzm)
    EditText et_yzm;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    private String tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_login);
        bindOnClick(new int[]{R.id.tv_get,R.id.tv_login,R.id.ib_finish},this);
        initView();
        onRestart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(Constant.isLogin){//如果已经登陆了 则退出该界面 即退出登陆模块
            setResult(Constant.LOGIN_SUCCESS);
            finish();

        }
    }

    public void initView(){
        findViewById(R.id.ib_finish).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("登陆");
        title.setTextSize(18);
        tv_login.setActivated(true);
        et_sjh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Utils.isTel(s.toString())){
                    tv_get.setTextColor(Color.WHITE);
                    tv_get.setActivated(true);
                }else{
                    tv_get.setTextColor(getResources().getColor(R.color.text_default));
                    tv_get.setActivated(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_yzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_yzm.getText().length() == 6 && Utils.isTel(et_sjh.getText().toString())){
                    tv_login.setTextColor(Color.WHITE);
                    tv_login.setActivated(false);
                }else{
                    tv_login.setTextColor(getResources().getColor(R.color.text_default));
                    tv_login.setActivated(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void initUuid(){
        //Hsia 针对Android6.0的动态获取权限做适配
        //TODO 获取完权限后 继续发送请求
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }else{
                getDeviceID();
            }
        } else {
            getDeviceID();
        }
    }

    private void getDeviceID() {
        Constant.uid = Utils.getUuid(LoginActivity.this);
        Constant.xkey = Utils.getMD5(Constant.uid + Constant.app + "yzdg2017smq^$^");
    }

    public void getVerify(){
        tel = et_sjh.getText().toString().trim();
        if(Utils.isTel(tel)){
            Map<String,Object> map = new HashMap();
            map.put("tel", tel);
            if(Constant.uid == null){
                initUuid();
            }else{
                new XRequest(this,"verify",XRequest.GET,map).setOnRequestListener(new BaseRequestListener(){
                    @Override
                    protected void success(String t) {
                        try{
                            tv_get.setActivated(false);
                            //TODO 倒计时
                            tv_get.setText("60s后重试");
                            JSONObject json = new JSONObject(t);
                            if("success".equals(json.getJSONObject("message").getString("type"))){
                                String verify = json.getString("data");
                                et_yzm.setText(verify);
                            }else{
                                showToast(json.getJSONObject("message").getString("content"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    protected void fail(int code) {
                        showToast("登陆异常");
                    }
                }).execute();
            }
        }else {
            showToast("请输入正确的手机号");
        }
    }

    public void login(){
        String tel = et_sjh.getText().toString().trim();
        String verify = et_yzm.getText().toString().trim();
        if(Utils.isTel(tel)&&verify.length()==6){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("tel", tel);
            map.put("verify", verify);
            new XRequest(this,"login",XRequest.GET,map).setOnRequestListener(new BaseRequestListener(){
                @Override
                protected void success(String t) {
                    try{
                        JSONObject json = new JSONObject(t);
                        if("success".equals(json.getJSONObject("message").getString("type"))){
                            if ("firstLogin".equals(json.getString("data"))) { //如果第一次注册则跳入填写个人信息界面
                                Intent intent = new Intent(LoginActivity.this,FirstOneActivity.class);
                                startActivity(intent);
                            }else {
                                Constant.isLogin = true;
                                setResult(Constant.LOGIN_SUCCESS);
                                finish();
                            }
                        }else{
                            showToast(json.getJSONObject("message").getString("content"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                protected void fail(int code) {
                    showToast("登陆失败");
                }
            }).execute();
        }else {
            showToast("请输入正确的手机号和验证码");
        }
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
            case R.id.tv_get:
                getVerify();
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.ib_finish:
                finish();
                break;
        }
    }

}
