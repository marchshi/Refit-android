package com.smq.demo5.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.bean.CatInfoBean;
import com.smq.demo5.bean.CatNameBean;
import com.smq.demo5.bean.OrderInfoBean;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.MoneyFactory;
import com.smq.demo5.util.Utils;
import com.smq.demo5.view.CircleImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class SubmitOrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_title_back)
    ImageView img_title_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.cimg_order_touxiang)
    CircleImageView cimg_order_touxiang;
    @BindView(R.id.tv_order_name)
    TextView tv_order_name;
    @BindView(R.id.tv_order_label)
    TextView tv_order_label;
    @BindView(R.id.tv_cat_FirstCatName)
    TextView tv_cat_FirstCatName;
    @BindView(R.id.tv_cat_SecondCatName)
    TextView tv_cat_SecondCatName;
    @BindView(R.id.tv_order_stimes)
    TextView tv_order_stimes;
    @BindView(R.id.tv_order_cont)
    TextView tv_order_cont;
    @BindView(R.id.tv_order_smodel)
    TextView tv_order_smodel;
    @BindView(R.id.tv_order_desc)
    TextView tv_order_desc;
    @BindView(R.id.tv_order_price)
    TextView tv_order_price;

    CatInfoBean cat;
    UserInfoBean user;
    CatNameBean catName;
    AccountInfoBean account;

    private PopupWindow popWindow;
    private View contentView;

    TextView balance;
    TextView price;
    ImageView img_oval_yue;
    ImageView img_oval_zfb;
    ImageView img_oval_weixin;
    private int payMode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_submit_order);
        cat = (CatInfoBean) getIntent().getSerializableExtra("cat");
        user = cat.getUser();
        init();
        bindOnClick(new int[]{R.id.ll_order_look_form,R.id.ll_order_pay_btn,R.id.img_title_back},this);
    }

    public void init(){
        img_title_back.setVisibility(View.VISIBLE);
        tv_title.setText("向"+user.getName()+"咨询");
        tv_title.setTextSize(18);
        Glide.with(this).load(Constant.helperUrl+user.getTouxiang()).into(cimg_order_touxiang);
        tv_order_name.setText(user.getName());
        tv_order_label.setText(cat.getLabel());
        if(cat.getCatName()==null){
            catName = Utils.getCatNameById(cat.getNameId());
        }else{
            catName = cat.getCatName();
        }
        tv_cat_FirstCatName.setText(catName.getParentName());
        tv_cat_SecondCatName.setText(catName.getChildName());
        if(cat.isCont())//TODO 优质服务数量
            tv_order_cont.setVisibility(View.VISIBLE);
        else
            tv_order_cont.setVisibility(View.GONE);
        switch (cat.getServerModel()){
            case 1:
                tv_order_smodel.setText("实时回答");
                break;
            case 2:
                tv_order_smodel.setText("闲时回答");
                break;
            case 3:
                tv_order_smodel.setText("预约回答");
                break;
        }
        tv_order_desc.setText(cat.getCatDesc());

        tv_order_price.setText(MoneyFactory.longToIntString(cat.getPrice()));

        initPay();

    }

    public void initPay(){
        contentView = View.inflate(this,R.layout.fragment_order_pay, null);
        popWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.setContentView(contentView);
        popWindow.setAnimationStyle(R.style.OrderAnim);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.in_translate_order);
        contentView.findViewById(R.id.ll_order_pay).setAnimation(animation);

        contentView.findViewById(R.id.img_order_pay_finish).setOnClickListener(this);
        contentView.findViewById(R.id.view_order_background).setOnClickListener(this);
        contentView.findViewById(R.id.ll_order_yue).setOnClickListener(this);
        contentView.findViewById(R.id.ll_order_zfb).setOnClickListener(this);
        contentView.findViewById(R.id.ll_order_weixin).setOnClickListener(this);
        contentView.findViewById(R.id.ll_order_submit).setOnClickListener(this);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                StatusBarUtil.setColor(SubmitOrderActivity.this,getResources().getColor(R.color.colorPrimary),50);
            }
        });
        img_oval_yue = (ImageView) contentView.findViewById(R.id.img_order_oval_yue);
        img_oval_zfb = (ImageView) contentView.findViewById(R.id.img_order_oval_zfb);
        img_oval_weixin = (ImageView) contentView.findViewById(R.id.img_order_oval_weixin);
        balance = (TextView) contentView.findViewById(R.id.tv_order_pay_balance);
        price = (TextView) contentView.findViewById(R.id.tv_order_pay_price);
        price.setText(MoneyFactory.longToIntString(cat.getPrice()));
        Map params = new HashMap();
        params.put("userId",Constant.userId);
        new XRequest(this,"account",XRequest.GET,params).setOnRequestListener(new ObjectRequestListener<AccountInfoBean>() {
            @Override
            public void success(AccountInfoBean accountInfoBean) {
                account = accountInfoBean;
                balance.setText(Utils.moneyFacotry(accountInfoBean.getBalance()));
            }
            @Override
            public void fail(String content) {

            }
        }).execute();

    }

    public void showPopupWindow(){
        contentView = View.inflate(this,R.layout.fragment_order_pay, null);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.in_translate_order);
//        contentView.findViewById(R.id.ll_order_pay).clearAnimation();
//        contentView.findViewById(R.id.ll_order_pay).setAnimation(animation);
        View rootview = View.inflate(this,R.layout.activity_submit_order, null);
        popWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        StatusBarUtil.setColor(this, Color.BLACK);
    }

    public void chooseOval(int i){
        payMode = i;
        img_oval_yue.setImageResource(R.drawable.draw_order_oval);
        img_oval_zfb.setImageResource(R.drawable.draw_order_oval);
        img_oval_weixin.setImageResource(R.drawable.draw_order_oval);
        switch (i){
            case 0:
                img_oval_yue.setImageResource(R.drawable.icon_ok);
                break;
            case 1:
                img_oval_zfb.setImageResource(R.drawable.icon_ok);
                break;
            case 2:
                img_oval_weixin.setImageResource(R.drawable.icon_ok);
                break;
        }
    }

    public void submitOrder(){
        switch (payMode){
            case 0:
                if(account.getBalance() <cat.getPrice()){
                    showToast("余额不足，请充值或选择其他支付方式");
                }else {
                    OrderInfoBean order = new OrderInfoBean();
                    order.setPayUserId(Constant.userId);
                    //TODO 这里的catId有问题
                    order.setCatId(cat.getCatId());
                    order.setNameId(cat.getNameId());
                    order.setPrice(cat.getPrice());
                    order.setCont(cat.isCont());
                    order.setServerModel(cat.getServerModel());
                    order.setScheTime(cat.getScheTime());
                    order.setForm(cat.getForm());
                    //TODO 设置订单来源
                    //发送一个网络请求
                    new XRequest(this,"order",XRequest.POST,order).setOnRequestListener(new ObjectRequestListener<String>() {
                        @Override
                        public void success(String s) {
                            showToast("支付成功，开始导购吧");
                            Intent intent = new Intent(SubmitOrderActivity.this,MainActivity.class);
                            intent.putExtra("page",1);
                            startActivity(intent);
                        }
                        @Override
                        public void fail(String content) {

                        }
                    }).execute();
                }
                break;
            case 1:
                showToast("支付宝支付");
                break;
            case 2:
                showToast("微信支付");
                break;
            default:
                showToast("请选择支付方式");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                break;
            case R.id.ll_order_look_form:
                Intent intent = new Intent(this,FillFormActivity.class);
                intent.putExtra("form",cat.getForm());
                startActivity(intent);
                break;
            case R.id.ll_order_pay_btn:
                //TODO 判断是否已经提交过订单 不能自己给自己导购
                showPopupWindow();
                break;
            case R.id.img_order_pay_finish:
                popWindow.dismiss();
                break;
            case R.id.view_order_background:
                popWindow.dismiss();
                break;
            case R.id.ll_order_yue:
                chooseOval(0);
                break;
            case R.id.ll_order_zfb:
                chooseOval(1);
                break;
            case R.id.ll_order_weixin:
                chooseOval(2);
                break;
            case R.id.ll_order_submit:
                submitOrder();
                break;
        }
    }

}
