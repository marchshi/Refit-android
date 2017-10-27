package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.smq.demo5.Constant;
import com.smq.demo5.R;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.net.ObjectRequestListener;
import com.smq.demo5.net.XRequest;
import com.smq.demo5.util.MyTextWatcher;

import butterknife.BindView;

public class FirstTwoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_login_ref)
    EditText et_login_ref;
    @BindView(R.id.et_login_resume)
    EditText et_login_resume;
    @BindView(R.id.tv_login_ref_num)
    TextView tv_login_ref_num;
    @BindView(R.id.tv_login_resume_num)
    TextView tv_login_resume_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_first_two);
        init();
        bindOnClick(new int[]{R.id.tv_login_next_two,R.id.img_title_back},this);
        et_login_ref.addTextChangedListener(new MyTextWatcher(tv_login_ref_num,18));
        et_login_resume.addTextChangedListener(new MyTextWatcher(tv_login_resume_num,99));
    }

    private void init(){
        findViewById(R.id.img_title_back).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("完善信息");
        title.setTextSize(18);
    }

    private void firstFinish(){
        String profession = et_login_ref.getText().toString().trim();
        String resume = et_login_resume.getText().toString();
        final UserInfoBean bean = (UserInfoBean) getIntent().getSerializableExtra("bean");
        bean.setProfession(profession);
        bean.setResume(resume);
        if(!TextUtils.isEmpty(profession)){
            new XRequest(this,"info",XRequest.POST,bean).setOnRequestListener(new ObjectRequestListener<String>() {
                @Override
                public void success(String s) {

                    Constant.loginRestart = true;
                    Intent intent = new Intent(FirstTwoActivity.this,LoginActivity.class);
                    intent.putExtra("saveInfo",true);
                    startActivity(intent);


                }
                @Override
                public void fail(String content) {

                }
            }).execute();
        }else{
            showToast("职业不能为空");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login_next_two:
                firstFinish();
                break;
            case R.id.img_title_back:
                finish();
                break;
        }
    }
}
