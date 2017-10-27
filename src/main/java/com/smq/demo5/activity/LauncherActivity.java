package com.smq.demo5.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.AskInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.bean.LoginVerifyBean;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.net.ListObjectRequestListener;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.SetObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.CacheManage;
import com.smq.demo5.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * LauncherActivity的主要作用是对应用进行初始化
 * 1，获取权限，初始化uuid和xkey
 * 2，网络请求初始化类目表
 * 3，读取配置信息
 * 4，初始化主界面
 *      不需要登陆： 首页的提问信息   类目表
 *      需要登陆：   个人信息    类目信息    提问信息  账户信息  （订单信息）    (聊天信息)
 */
public class LauncherActivity extends BaseActivity {

    private Handler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏完全的透明 FLAG_FULLSCREEN  半透明FLAG_TRANSLUCENT_STATUS
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        initCache();

        myHandler = new Handler();
        myHandler.postDelayed(myRunnable, 3000);
        //1,获取权限 初始化uuid和xkey
        initUuid();
        initAsk();
    }

    private void initCache(){
        //判断导购历史是否为空 为空则添加一个空的list
        if(TextUtils.isEmpty(CacheManage.getSearchHis())){
            List list = new ArrayList();
            list.clear();
            CacheManage.setSearchHis(new Gson().toJson(list));
        }
    }

    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            myHandler.removeCallbacks(myRunnable);
            nextActivity();
        }
    };

    public void initAsk(){
        new XRequest(this,"askList").setOnRequestListener(new SetObjectRequestListener<AskInfoBean>() {
            @Override
            public void success(Set<AskInfoBean> list) {
                Constant.askList = new ArrayList<AskInfoBean>(list);
            }
            @Override
            public void fail(String content) {

            }
        }).execute();
    }

    public void initUuid(){
        //Hsia 针对Android6.0的动态获取权限做适配
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(LauncherActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }else{
                getDeviceID();
            }
        } else {
            getDeviceID();
        }
    }

    private void getDeviceID() {
        Constant.uid = Utils.getUuid(LauncherActivity.this);
        Constant.xkey = Utils.getMD5(Constant.uid + Constant.app + "yzdg2017smq^$^");
        //2,初始化类目表
        initCategory();
    }

    private void initCategory(){
        new XRequest(this,"catName").setOnRequestListener(new ListObjectRequestListener() {
            @Override
            public void success(String s) {
                Constant.catList = Utils.stringToArray(s, CatNameBean[].class);
            }

            @Override
            public void fail(String content) {
                showToast("请求失败");
            }
        }).execute();
    }

    private void nextActivity(){
        if(CacheManage.isFirstLoad()){
            Intent intent = new Intent(LauncherActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }else{
            if(CacheManage.isAuto()){
                //1,组装LoginVerifyBean
                LoginVerifyBean bean = new LoginVerifyBean();
                bean.setUserId(CacheManage.getUserId());
                bean.setTel(CacheManage.getTel());
                bean.setVerify(CacheManage.getVerify());
                bean.setUuid(CacheManage.getUuid());
                Map params = new HashMap();
                params.put("loginer",2);
                params.put("bean",new Gson().toJson(bean));
                new XRequest(this,"login",XRequest.POST,params).setOnRequestListener(new ObjectRequestListener<UserInfoBean>() {
                    @Override
                    public void success(UserInfoBean userInfoBean) {
                        Utils.initBean(userInfoBean);
                        Constant.isLogin = true;

                        //登陆成功后登陆聊天服务器
                        EMClient.getInstance().login(userInfoBean.getUserId()+"", userInfoBean.getUserId()+"", new EMCallBack() {
                            @Override
                            public void onSuccess() {  //登录成功
                                Log.i("aaa","登录成功");
                                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                                startActivity(intent);
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
                        Constant.isLogin = false;
                        showToast("自动登录失败");
                        Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).execute();
            }else{
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initUuid();
    }
}
