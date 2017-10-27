package com.smq.mode2.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smq.mode2.Constant;
import com.smq.mode2.R;
import com.smq.mode2.entity.CatTableEntity;
import com.smq.mode2.fragment.AdminFragment;
import com.smq.mode2.fragment.MainFragment;
import com.smq.mode2.fragment.MyFragment;
import com.smq.mode2.fragment.OrderFragment;
import com.smq.mode2.net.BaseRequestListener;
import com.smq.mode2.net.XRequest;
import com.smq.mode2.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.fragment_content)
    FrameLayout fragmentContent;
    @BindView(R.id.rg_group_home_im)
    ImageView rgGroupHomeIm;
    @BindView(R.id.rg_group_home_tv)
    TextView rgGroupHomeTv;
    @BindView(R.id.rg_group_home)
    LinearLayout rgGroupHome;
    @BindView(R.id.rg_group_order_im)
    ImageView rgGroupOrderIm;
    @BindView(R.id.rg_group_order_tv)
    TextView rgGroupOrderTv;
    @BindView(R.id.rg_group_order)
    LinearLayout rgGroupOrder;
    @BindView(R.id.rg_group_admin_im)
    ImageView rgGroupAdminIm;
    @BindView(R.id.rg_group_admin_tv)
    TextView rgGroupAdminTv;
    @BindView(R.id.rg_group_admin)
    LinearLayout rgGroupAdmin;
    @BindView(R.id.rg_group_my_im)
    ImageView rgGroupMyIm;
    @BindView(R.id.rg_group_my_tv)
    TextView rgGroupMyTv;
    @BindView(R.id.rg_group_my)
    LinearLayout rgGroupMy;

    private FragmentTransaction ft;
    private FragmentManager fragmentManager;

    private MainFragment mainFragment;
    private AdminFragment adminFragment;
    private OrderFragment orderFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO 在首界面临时初始化类目表
        new XRequest(this,"catTable").setOnRequestListener(new BaseRequestListener() {
            @Override
            protected void success(String t) {
                try {
                    JSONObject json = new JSONObject(t);
                    if("success".equals(json.getJSONObject("message").getString("type"))){
                        Constant.category = new Gson().fromJson(json.getString("data"),new TypeToken<List<CatTableEntity>>(){}.getType());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void fail(int code) {

            }
        }).execute();

        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_main);
        initView();
        setSelectTab(3);
        initUuid();
    }
    public void initUuid(){
        //Hsia 针对Android6.0的动态获取权限做适配
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }else{
                getDeviceID();
            }
        } else {
            getDeviceID();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void getDeviceID() {
        Constant.uid = Utils.getUuid(MainActivity.this);
        Constant.xkey = Utils.getMD5(Constant.uid + Constant.app + "yzdg2017smq^$^");
        getExt();
    }

    private void getExt(){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
        }
    }

    private void hideFragment(FragmentTransaction ft) {
        if (mainFragment != null) {
            ft.hide(mainFragment);
        }
        if (orderFragment != null) {
            ft.hide(orderFragment);
        }
        if (adminFragment != null) {
            ft.hide(adminFragment);
        }
        if (myFragment != null) {
            ft.hide(myFragment);
        }
    }

    private void clearColor() {
        //隐藏全部文字
        rgGroupHomeTv.setVisibility(View.GONE);
        rgGroupOrderTv.setVisibility(View.GONE);
        rgGroupAdminTv.setVisibility(View.GONE);
        rgGroupMyTv.setVisibility(View.GONE);
        //清除imageView 背景
        rgGroupHomeIm.setImageResource(R.drawable.icon_label_home);
        rgGroupOrderIm.setImageResource(R.drawable.icon_label_order);
        rgGroupAdminIm.setImageResource(R.drawable.icon_label_admin);
        rgGroupMyIm.setImageResource(R.drawable.icon_label_my);
    }

    /**
     * 根据用户行为决定显示内容
     * 点击的时候进行判断 主界面不显示标题栏 其他界面显示标题栏。 同时每次点击都要对界面进行刷新
     * @param i
     */
    public void setSelectTab(int i) {
        fragmentManager = getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();
        hideFragment(ft);
        clearColor();
        switch (i) {
            case 0:
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    ft.add(R.id.fragment_content, mainFragment);
                } else {
                    ft.show(mainFragment);
                    mainFragment.refresh();
                }
                findViewById(R.id.tab_rl_root).setVisibility(View.GONE);
                rgGroupHomeTv.setVisibility(View.VISIBLE);
                rgGroupHomeIm.setImageResource(R.drawable.icon_label_home_big);
                break;
            case 1:
                if (orderFragment == null) {
                    orderFragment = new OrderFragment();
                    ft.add(R.id.fragment_content, orderFragment);
                } else {
                    ft.show(orderFragment);
                    orderFragment.refresh();
                }
                findViewById(R.id.tab_rl_root).setVisibility(View.VISIBLE);
                rgGroupOrderTv.setVisibility(View.VISIBLE);
                rgGroupOrderIm.setImageResource(R.drawable.icon_label_order_big);
                break;

            case 2:
                if (adminFragment == null) {
                    adminFragment = new AdminFragment();
                    ft.add(R.id.fragment_content, adminFragment);
                } else {
                    ft.show(adminFragment);
                    adminFragment.refresh();
                }
                findViewById(R.id.tab_rl_root).setVisibility(View.VISIBLE);
                rgGroupAdminTv.setVisibility(View.VISIBLE);
                rgGroupAdminIm.setImageResource(R.drawable.icon_label_admin_big);
                break;

            case 3:
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    ft.add(R.id.fragment_content, myFragment);
                } else {
                    ft.show(myFragment);
                    myFragment.refresh();
                }
                findViewById(R.id.tab_rl_root).setVisibility(View.VISIBLE);
                rgGroupMyTv.setVisibility(View.VISIBLE);
                rgGroupMyIm.setImageResource(R.drawable.icon_label_my_big);
                break;
        }
        ft.commit();
    }

    public void initView() {
        //新建全部的fragment对象
//        initFragment();
        //初始imageView
        clearColor();
        initEvent();
    }

    public void initFragment(){
        mainFragment = new MainFragment();
        orderFragment = new OrderFragment();
        adminFragment = new AdminFragment();
        myFragment = new MyFragment();
        fragmentManager = getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_content, mainFragment);
        ft.add(R.id.fragment_content, orderFragment);
        ft.add(R.id.fragment_content, adminFragment);
        ft.add(R.id.fragment_content, myFragment);
        ft.commit();
    }

    private void initEvent() {
        rgGroupHome.setOnClickListener(this);
        rgGroupOrder.setOnClickListener(this);
        rgGroupAdmin.setOnClickListener(this);
        rgGroupMy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rg_group_home:
                setSelectTab(0);
                break;
            case R.id.rg_group_order:
                setSelectTab(1);
                break;
            case R.id.rg_group_admin:
                setSelectTab(2);
                break;
            case R.id.rg_group_my:
                setSelectTab(3);
                break;
        }
    }

}
