package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.smq.demo5.R;
import com.smq.demo5.fragment.AdminFragment;
import com.smq.demo5.fragment.GuideFragment;
import com.smq.demo5.fragment.MainFragment;
import com.smq.demo5.fragment.MyFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.fl_main_content)
    FrameLayout fl_main_content;
    @BindView(R.id.img_main_bottom_main)
    ImageView img_main_bottom_main;
    @BindView(R.id.tv_main_bottom_main)
    TextView tv_main_bottom_main;
    @BindView(R.id.img_main_bottom_guide)
    ImageView img_main_bottom_guide;
    @BindView(R.id.tv_main_bottom_guide)
    TextView tv_main_bottom_guide;
    @BindView(R.id.img_main_bottom_admin)
    ImageView img_main_bottom_admin;
    @BindView(R.id.tv_main_bottom_admin)
    TextView tv_main_bottom_admin;
    @BindView(R.id.img_main_bottom_my)
    ImageView img_main_bottom_my;
    @BindView(R.id.tv_main_bottom_my)
    TextView tv_main_bottom_my;

    private FragmentManager fm;
    private FragmentTransaction ft;

    private MainFragment mainFragment = new MainFragment();
    private GuideFragment guideFragment = new GuideFragment();
    private AdminFragment adminFragment = new AdminFragment();
    private MyFragment myFragment = new MyFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_main);
        init();
        bindOnClick(new int[]{R.id.ly_main_bottom_main,R.id.ly_main_bottom_guide,R.id.ly_main_bottom_admin,R.id.ly_main_bottom_my},this);
        setSelectTab(0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int page = getIntent().getIntExtra("page",-1);
        if(page!=-1){
            setSelectTab(page);
            getIntent().removeExtra("page");
        }
    }

    /**
     * TODO 一起加载会造成卡顿 如何才能在luncher界面进行加载
     */
    private void init(){
        initBottom();
        initFragment();
    }

    private void initBottom(){
        img_main_bottom_main.setImageResource(R.drawable.rg_group_home_im);
        img_main_bottom_guide.setImageResource(R.drawable.rg_group_order_im);
        img_main_bottom_admin.setImageResource(R.drawable.rg_group_admin_im);
        img_main_bottom_my.setImageResource(R.drawable.rg_group_me_im);
        tv_main_bottom_main.setVisibility(View.GONE);
        tv_main_bottom_guide.setVisibility(View.GONE);
        tv_main_bottom_admin.setVisibility(View.GONE);
        tv_main_bottom_my.setVisibility(View.GONE);
    }

    private void initFragment(){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fl_main_content,mainFragment);
        ft.add(R.id.fl_main_content,guideFragment);
        ft.add(R.id.fl_main_content,adminFragment);
        ft.add(R.id.fl_main_content,myFragment);
        hideFragment(ft);
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft){
        ft.hide(mainFragment);
        ft.hide(guideFragment);
        ft.hide(adminFragment);
        ft.hide(myFragment);
    }

    /**
     * TODO 如果已经显示了 就不要重复(隐藏-显示)的动作
     */
    public void setSelectTab(int i){
        ft = fm.beginTransaction();
        hideFragment(ft);
        initBottom();
        switch (i){
            case 0:
                tv_main_bottom_main.setVisibility(View.VISIBLE);
                img_main_bottom_main.setImageResource(R.drawable.rg_group_home_im_big);
                ft.show(mainFragment);
                break;
            case 1:
                tv_main_bottom_guide.setVisibility(View.VISIBLE);
                img_main_bottom_guide.setImageResource(R.drawable.rg_group_order_im_big);
                ft.show(guideFragment);
                break;
            case 2:
                tv_main_bottom_admin.setVisibility(View.VISIBLE);
                img_main_bottom_admin.setImageResource(R.drawable.rg_group_admin_im_big);
                ft.show(adminFragment);
                adminFragment.refresh();
                break;
            case 3:
                tv_main_bottom_my.setVisibility(View.VISIBLE);
                img_main_bottom_my.setImageResource(R.drawable.rg_group_me_im_big);
                ft.show(myFragment);
                break;
        }
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly_main_bottom_main:
                setSelectTab(0);
                break;
            case R.id.ly_main_bottom_guide:
                setSelectTab(1);
                break;
            case R.id.ly_main_bottom_admin:
                setSelectTab(2);
                break;
            case R.id.ly_main_bottom_my:
                setSelectTab(3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i("aaa","退出登录成功");
            }
            @Override
            public void onError(int i, String s) {
                Log.i("aaa","退出登录失败"+i+" , "+s);
            }
            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
