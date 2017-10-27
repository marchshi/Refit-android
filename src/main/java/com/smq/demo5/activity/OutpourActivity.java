package com.smq.demo5.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.AnimationHelper;
import com.smq.demo5.util.EditFloatPriceHelper;
import com.smq.demo5.util.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class OutpourActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_account_outpour_btn)
    TextView tv_account_outpour_btn;
    @BindView(R.id.tv_account_outpour)
    TextView tv_account_outpour;
    @BindView(R.id.et_account_outpour)
    EditText et_account_outpour;
    @BindView(R.id.tv_title)
    TextView tv_title;

    AccountInfoBean accountInfoBean = new AccountInfoBean() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_outpour);
        init();
        bindOnClick(new int[]{R.id.img_title_back,R.id.tv_account_all,R.id.tv_account_outpour_btn},this);
    }

    public void init(){
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorSecond),50);
        findViewById(R.id.title_bar).setBackgroundColor(getResources().getColor(R.color.colorSecond));
        findViewById(R.id.img_title_back).setVisibility(View.VISIBLE);
        tv_title.setText("提现");
        tv_title.setTextSize(18);
        new EditFloatPriceHelper(et_account_outpour) {
            @Override
            public void myTextChangeLister() {
                //字符不为空
                String str = et_account_outpour.getText().toString();
                if(!TextUtils.isEmpty(str)){
                    tv_account_outpour_btn.setActivated(true);
                    if(Utils.stringToMoney(str) > accountInfoBean.getBalance()){
                        et_account_outpour.setText(Utils.moneyFacotry(accountInfoBean.getBalance()));
                        et_account_outpour.setSelection(Utils.moneyFacotry(accountInfoBean.getBalance()).length());
                    }
                }else {
                    tv_account_outpour_btn.setActivated(false);
                }
            }
        };
        if(TextUtils.isEmpty(et_account_outpour.getText().toString())){
            tv_account_outpour_btn.setActivated(false);
        }else {
            tv_account_outpour_btn.setActivated(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map params = new HashMap();
        params.put("userId", Constant.userId);
        new XRequest(this,"account",XRequest.GET,params).setOnRequestListener(new ObjectRequestListener<AccountInfoBean>() {
            @Override
            public void success(AccountInfoBean accountInfoBean) {
                tv_account_outpour.setText(Utils.moneyFacotry(accountInfoBean.getBalance()));
                OutpourActivity.this.accountInfoBean = accountInfoBean;
            }
            @Override
            public void fail(String content) {

            }
        }).execute();
    }

    public void outpour(){
        String str = et_account_outpour.getText().toString().trim();
        long money = Utils.stringToMoney(str);
        Map params = new HashMap();
        params.put("userId", Constant.userId);
        params.put("money",money);
        new XRequest(this,"outpour",XRequest.POST,params).setOnRequestListener(new ObjectRequestListener<String>() {
            @Override
            public void success(String s) {
                showToast("提现成功");
                finish();
                AnimationHelper.finish(OutpourActivity.this);
            }
            @Override
            public void fail(String content) {

            }
        }).execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                AnimationHelper.finish(this);
                break;
            case R.id.tv_account_all:
                et_account_outpour.setText(Utils.moneyFacotry(accountInfoBean.getBalance()));
                et_account_outpour.setSelection(et_account_outpour.getText().toString().length());
                break;
            case R.id.tv_account_outpour_btn:
                outpour();
                break;
        }
    }
}
