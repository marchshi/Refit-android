package com.smq.mode2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.smq.mode2.R;

import butterknife.BindView;

public class FirstOneActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.et_first_name)
    EditText et_first_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_first_one);
        init();
        bindOnClick(new int[]{R.id.ib_finish, R.id.tv_first_next_one},this);
    }
    private void init(){
        findViewById(R.id.ib_finish).setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.tv_title);
        title.setText("完善信息");
        title.setTextSize(18);
    }

    private void next(){
        String name = et_first_name.getText().toString().trim();
        if(!TextUtils.isEmpty(name)){
            Intent intent = new Intent(FirstOneActivity.this,FirstTwoActivity.class);
            intent.putExtra("name",name);
            startActivity(intent);
        }else {
            showToast("姓名不能为空");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_first_next_one:
                next();
                break;
            case R.id.ib_finish:
                finish();
                break;
        }
    }
}
