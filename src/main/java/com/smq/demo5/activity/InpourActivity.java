package com.smq.demo5.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.AccountInfoBean;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.AnimationHelper;
import com.smq.demo5.util.EditPriceHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class InpourActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_title_back)
    ImageView img_title_back;
    @BindView(R.id.title_bar)
    RelativeLayout title_bar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_account_inpour)
    EditText et_account_inpour;
    @BindView(R.id.tv_account_inpour_btn)
    TextView tv_account_inpour_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_inpour);
        init();
    }

    public void init(){
        title_bar.setBackgroundColor(getResources().getColor(R.color.white));
        img_title_back.setVisibility(View.VISIBLE);
        img_title_back.setImageResource(R.drawable.icon_arrow_blue);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("充值");
        tv_title.setTextSize(18);
        tv_title.setTextColor(getResources().getColor(R.color.text_black));

        new EditPriceHelper(et_account_inpour){
            @Override
            public void myTextChangeLister() {
                if(et_account_inpour.getText().toString().length()==0)
                    tv_account_inpour_btn.setActivated(false);
                else
                    tv_account_inpour_btn.setActivated(true);
            }
        };
        et_account_inpour.requestFocus();
        if(et_account_inpour.getText().toString().length()==0)
            tv_account_inpour_btn.setActivated(false);
        else
            tv_account_inpour_btn.setActivated(true);

        bindOnClick(new int[]{R.id.img_title_back,R.id.tv_account_inpour_btn},this);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.white),50);
    }

    public void inpour(){
        String str = et_account_inpour.getText().toString().trim();
        long money = Long.parseLong(str) *100;
        Map params = new HashMap();
        params.put("userId", Constant.userId);
        params.put("money",money);
        new XRequest(this,"inpour",XRequest.POST,params).setOnRequestListener(new ObjectRequestListener<AccountInfoBean>() {
            @Override
            public void success(AccountInfoBean accountInfoBean) {
                showToast("充值成功");
                finish();
                AnimationHelper.finish(InpourActivity.this);
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
            case R.id.tv_account_inpour_btn:
                inpour();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AnimationHelper.finish(this);
    }
}
