package com.smq.demo5.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.LoginVerifyBean;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.CacheManage;
import com.smq.demo5.util.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_login_sjh)
    EditText et_login_sjh;
    @BindView(R.id.tv_login_get)
    TextView tv_login_get;
    @BindView(R.id.et_login_yzm)
    EditText et_login_yzm;
    @BindView(R.id.tv_login_login)
    TextView tv_login_login;

    LoginVerifyBean loginVerifyBean = new LoginVerifyBean();
    //控制验证码是否能获取的标识
    boolean canGet = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_login);
        bindOnClick(new int[]{R.id.tv_login_get,R.id.tv_login_login,R.id.img_title_back},this);
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(Constant.loginRestart){
            login();
        }
    }

    private void initView(){
        findViewById(R.id.img_title_back).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("登 陆");
        title.setTextSize(18);
        tv_login_login.setActivated(false);
        et_login_sjh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Utils.isTel(s.toString())){
                    tv_login_get.setTextColor(Color.WHITE);
                    tv_login_get.setActivated(true);
                }else{
                    tv_login_get.setTextColor(getResources().getColor(R.color.text_gray));
                    tv_login_get.setActivated(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_login_yzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_login_yzm.getText().length() == 6 && Utils.isTel(et_login_sjh.getText().toString())){
                    tv_login_login.setTextColor(Color.WHITE);
                    tv_login_login.setActivated(true);
                }else{
                    tv_login_login.setTextColor(getResources().getColor(R.color.text_default));
                    tv_login_login.setActivated(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getVerify(){
        String tel = et_login_sjh.getText().toString().trim();
        if(Utils.isTel(tel)){
            loginVerifyBean.setTel(tel);
            loginVerifyBean.setUuid(Constant.uid);
                new XRequest(this,"verify",XRequest.POST,loginVerifyBean).setOnRequestListener(new ObjectRequestListener<LoginVerifyBean>() {
                @Override
                public void success(LoginVerifyBean loginVerifyBean) {
                    LoginActivity.this.loginVerifyBean = loginVerifyBean;
                    tv_login_get.setActivated(false);
                    tv_login_get.setTextColor(getResources().getColor(R.color.text_gray));
                    et_login_yzm.setText(loginVerifyBean.getVerify());
                    canGet = false;
                    new CountDownTimer(60 * 1000, 1000) {
                        @Override
                        public void onTick(long l) {
                            tv_login_get.setText((l / 1000) + "秒");
                        }
                        @Override
                        public void onFinish() {
                            canGet = true;
                            tv_login_get.setText("重新发送");
                            tv_login_get.setTextColor(getResources().getColor(R.color.white));
                            tv_login_get.setActivated(true);
                        }
                    }.start();
                }
                @Override
                public void fail(String content) {

                }
            }).execute();
        }
    }

    private void login(){
        String tel = et_login_sjh.getText().toString().trim();
        String verify = et_login_yzm.getText().toString().trim();
        if(Utils.isTel(tel)&&verify.length()==6){
            loginVerifyBean.setTel(tel);
            loginVerifyBean.setVerify(verify);
            loginVerifyBean.setUuid(Constant.uid);
            Map params = new HashMap();
            params.put("bean",new Gson().toJson(loginVerifyBean));
            params.put("loginer",1);
            new XRequest(this,"login",XRequest.POST,params).setOnRequestListener(new ObjectRequestListener<UserInfoBean>() {
                @Override
                public void success(UserInfoBean userInfoBean) {
                    Utils.initBean(userInfoBean);
                    Constant.isLogin = true;
                    CacheManage.setAuto(true);
                    CacheManage.setUserId(loginVerifyBean.getUserId());
                    CacheManage.setTel(loginVerifyBean.getTel());
                    CacheManage.setVerify(loginVerifyBean.getVerify());
                    CacheManage.setUuid(loginVerifyBean.getUuid());

                    setResult(Constant.LOGIN_SUCCESS);

                    //登陆成功后登陆聊天服务器
                    EMClient.getInstance().login(userInfoBean.getUserId()+"", userInfoBean.getUserId()+"", new EMCallBack() {
                                @Override
                                public void onSuccess() {  //登录成功
                                    Log.i("aaa","登录成功");
                                    finish();
                                }

                                @Override
                                public void onError(int i, String s) {  //登录错误
                                    Log.i("aaa","登录失败"+i+" , "+s);
                                    finish();
                                }
                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });

                }
                @Override
                public void fail(String content) {
                    if("first".equals(content)){
                        Constant.loginRestart = false;
                        Intent intent = new Intent(LoginActivity.this,FirstOneActivity.class);
                        UserInfoBean bean = new UserInfoBean();
                        bean.setUserId(loginVerifyBean.getUserId());
                        bean.setTel(loginVerifyBean.getTel());
                        intent.putExtra("bean",bean);
                        startActivity(intent);
                    }
                }
            }).execute();

        }else {
            showToast("请输入正确的手机号和验证码");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login_get:
                if(canGet)
                    getVerify();
                break;
            case R.id.tv_login_login:
                login();
                break;
            case R.id.img_title_back:
                Constant.loginRestart = true;
                finish();
                break;
        }
    }

}
