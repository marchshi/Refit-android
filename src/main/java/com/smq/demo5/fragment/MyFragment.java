package com.smq.demo5.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.activity.AccountActivity;
import com.smq.demo5.activity.EditInfoActivity;
import com.smq.demo5.activity.LoginActivity;
import com.smq.demo5.activity.MyOrderActivity;
import com.smq.demo5.entity.MyFragmentEntity;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.AnimationHelper;
import com.smq.demo5.util.CacheManage;
import com.smq.demo5.util.Utils;
import com.smq.demo5.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shimanqian on 2017/7/18.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ly_my_account)
    LinearLayout ly_my_account;
    @BindView(R.id.ly_my_after)
    LinearLayout ly_my_after;
    @BindView(R.id.ly_my_before)
    LinearLayout ly_my_before;
    @BindView(R.id.tv_my_zhuxiao)
    TextView tv_my_zhuxiao;
    @BindView(R.id.ly_my_info_edit)
    LinearLayout ly_my_info_edit;
    @BindView(R.id.img_touxiang_my)
    CircleImageView img_touxiang_my;
    @BindView(R.id.tv_my_name)
    TextView tv_my_name;
    @BindView(R.id.tv_my_prof)
    TextView tv_my_prof;
    @BindView(R.id.tv_my_times)
    TextView tv_my_times;
    @BindView(R.id.tv_my_income)
    TextView tv_my_income;
    @BindView(R.id.tv_my_money)
    TextView tv_my_money;
    @BindView(R.id.tv_my_tel)
    TextView tv_my_tel;
    @BindView(R.id.ly_my_order_1)
    LinearLayout ly_my_order_1;
    @BindView(R.id.ly_my_order_2)
    LinearLayout ly_my_order_2;
    @BindView(R.id.ly_my_order_3)
    LinearLayout ly_my_order_3;
    @BindView(R.id.ly_my_order_4)
    LinearLayout ly_my_order_4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    //何时对界面进行刷新？
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            init();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init(){
        if(Constant.isLogin){//如果用户已经登录了，则显示个人信息
            ly_my_before.setVisibility(View.GONE);
            ly_my_after.setVisibility(View.VISIBLE);
            tv_my_zhuxiao.setOnClickListener(this);
            ly_my_info_edit.setOnClickListener(this);
            ly_my_account.setOnClickListener(this);
            initview();
        }else{//如果没有登录则将个人信息隐藏，显示匿名登录界面
            ly_my_before.setVisibility(View.VISIBLE);
            ly_my_after.setVisibility(View.GONE);
            ly_my_before.setOnClickListener(this);
            clearview();
        }
        ly_my_order_1.setOnClickListener(this);
        ly_my_order_2.setOnClickListener(this);
        ly_my_order_3.setOnClickListener(this);
        ly_my_order_4.setOnClickListener(this);
    }

    private void clearview(){
        tv_my_money.setText("0.00");
        tv_my_tel.setText("");
    }

    private void initview(){
        new XRequest(activity,"myFragment").setOnRequestListener(new ObjectRequestListener<MyFragmentEntity>() {
            @Override
            public void success(MyFragmentEntity entity) {
                if(entity.getTouxiang() != null){
                    Glide.with(activity).load(Constant.helperUrl+entity.getTouxiang()).into(img_touxiang_my);
                }else{
                    img_touxiang_my.setImageResource(R.drawable.icon_touxiang_zhuce);
                }
                tv_my_name.setText(entity.getName());
                tv_my_prof.setText(entity.getProfession());
                tv_my_times.setText(entity.getServerTimes()+"");
                tv_my_income.setText(Utils.moneyFacotry(entity.getServerIncome()));
                tv_my_money.setText(Utils.moneyFacotry(entity.getBalance()));
                tv_my_tel.setText(entity.getTel());
            }
            @Override
            public void fail(String content) {

            }
        }).execute();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.ly_my_account:
                intent = new Intent(activity,AccountActivity.class);
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                startActivity(intent);
                AnimationHelper.start(activity);
                break;
            case R.id.ly_my_before:
                intent = new Intent(activity, LoginActivity.class);
                startActivityForResult(intent,Constant.NEED_LOGIN);
                break;
            case R.id.ly_my_info_edit:
                intent = new Intent(activity, EditInfoActivity.class);
                startActivityForResult(intent,Constant.EDIT_INFO);
                break;
            case R.id.tv_my_zhuxiao:
                Constant.isLogin = false;
                CacheManage.setAuto(false);
                //TODO 在第二个用户注销时会报错 java.lang.UnsupportedOperationException
                Constant.cats.clear();
                init();
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
                break;
            case R.id.ly_my_order_1:
                if(!Constant.isLogin){
                    intent = new Intent(activity, LoginActivity.class);
                    startActivityForResult(intent,Constant.NEED_LOGIN);
                }else {
                    intent = new Intent(activity, MyOrderActivity.class);
                    intent.putExtra("page",0);
                    startActivity(intent);
                }
                break;
            case R.id.ly_my_order_2:
                if(!Constant.isLogin){
                    intent = new Intent(activity, LoginActivity.class);
                    startActivityForResult(intent,Constant.NEED_LOGIN);
                }else {
                    intent = new Intent(activity, MyOrderActivity.class);
                    intent.putExtra("page",1);
                    startActivity(intent);
                }
                break;
            case R.id.ly_my_order_3:
                if(!Constant.isLogin){
                    intent = new Intent(activity, LoginActivity.class);
                    startActivityForResult(intent,Constant.NEED_LOGIN);
                }else {
                    intent = new Intent(activity, MyOrderActivity.class);
                    intent.putExtra("page",2);
                    startActivity(intent);
                }
                break;
            case R.id.ly_my_order_4:
                if(!Constant.isLogin){
                    intent = new Intent(activity, LoginActivity.class);
                    startActivityForResult(intent,Constant.NEED_LOGIN);
                }else {
                    intent = new Intent(activity, MyOrderActivity.class);
                    intent.putExtra("page",3);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Constant.NEED_LOGIN && resultCode==Constant.LOGIN_SUCCESS){
            init();
        }else if(requestCode==Constant.EDIT_INFO && resultCode==Constant.EDIT_SUCCESS){
            init();
        }
    }
}
