package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.smq.demo5.R;
import com.smq.demo5.bean.UserInfoBean;
import com.smq.demo5.util.MyTextWatcher;

import butterknife.BindView;


public class FirstOneActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.et_login_name)
    EditText et_login_name;
    @BindView(R.id.tv_login_name_num)
    TextView tv_login_name_num;

    UserInfoBean bean ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_first_one);
        init();
        bindOnClick(new int[]{R.id.img_title_back, R.id.tv_login_next_one},this);
        bean = (UserInfoBean) getIntent().getSerializableExtra("bean");
    }
    private void init(){
        findViewById(R.id.img_title_back).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("完善信息");
        title.setTextSize(18);
        et_login_name.addTextChangedListener(new MyTextWatcher(tv_login_name_num,16));
    }

    private void next(){
        String name = et_login_name.getText().toString().trim();
        if(!TextUtils.isEmpty(name)){
            Intent intent = new Intent(FirstOneActivity.this,FirstTwoActivity.class);
            bean.setName(name);
            intent.putExtra("bean",bean);
            startActivity(intent);
        }else {
            showToast("姓名不能为空");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login_next_one:
                next();
                break;
            case R.id.img_title_back:
                finish();
                break;
        }
    }
}
