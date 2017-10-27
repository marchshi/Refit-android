package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.AnimationHelper;
import com.smq.demo5.util.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public class AccountActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_account_3)
    TextView tv_account_3;
    @BindView(R.id.ll_account_1)
    LinearLayout ll_account_1;
    @BindView(R.id.ll_account_2)
    LinearLayout ll_account_2;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_title_right)
    TextView tv_title_right;
    @BindView(R.id.tv_account_money)
    TextView tv_account_money;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //使用动画
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
//        //退出时使用
//        getWindow().setExitTransition(slide);
//        //第一次进入时使用
//        getWindow().setEnterTransition(slide);
//        //再次进入时使用
//        getWindow().setReenterTransition(slide);
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_account);
        init();
        bindOnClick(new int[]{R.id.img_title_back,R.id.tv_title_right,R.id.rl_account_cz,R.id.rl_account_tx},this);
    }

    public void init(){
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorSecond),50);
        findViewById(R.id.title_bar).setBackgroundResource(R.color.colorSecond);
        findViewById(R.id.img_title_back).setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("账户");
        tv_title.setTextSize(18);
        tv_title.setTextColor(getResources().getColor(R.color.white));
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText("明细");
        tv_title_right.setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    protected void onStart() {
        super.onStart();
        Map params = new HashMap();
        params.put("userId", Constant.userId);
        new XRequest(this,"account",XRequest.GET,params).setOnRequestListener(new ObjectRequestListener<AccountInfoBean>() {
            @Override
            public void success(AccountInfoBean accountInfoBean) {
                tv_account_money.setText(Utils.moneyFacotry(accountInfoBean.getBalance()));
            }
            @Override
            public void fail(String content) {

            }
        }).execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                AnimationHelper.finish(this);
                break;
            case R.id.tv_title_right:

                break;
            case R.id.rl_account_cz:
                intent = new Intent(this,InpourActivity.class);
                startActivity(intent);
                AnimationHelper.start(this);
                break;
            case R.id.rl_account_tx:
                intent = new Intent(this,OutpourActivity.class);
                Pair account_1 = new Pair<View,String>(ll_account_1, ViewCompat.getTransitionName(ll_account_1));
                Pair account_2 = new Pair<View,String>(ll_account_2, ViewCompat.getTransitionName(ll_account_2));
                Pair account_3 = new Pair<View,String>(tv_account_3, ViewCompat.getTransitionName(tv_account_3));
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this,account_1,account_2,account_3).toBundle());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AnimationHelper.finish(this);
    }
}
