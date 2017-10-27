package com.smq.mode2.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.smq.mode2.R;

import butterknife.ButterKnife;


/**
 * 创建一个activity基类 将所有类中都要实现的操作抽取出来
 * 比如：ButterKnife的绑定和设置点击事件的方法
 */
public class BaseActivity extends AppCompatActivity {

    public FrameLayout mFlContent;
    public Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_pager);
        mContext = BaseActivity.this;
        initView();
    }

    private void initView(){
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
    }

    public void setbodyView(int layout){
        View view = LayoutInflater.from(getApplicationContext()).inflate(
                layout,null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mFlContent.addView(view,layoutParams);
        ButterKnife.bind(BaseActivity.this,view);

    }

    public void bindOnClick(int[] ids, View.OnClickListener listener){
        for(int id :ids){
            findViewById(id).setOnClickListener(listener);
        }
    }

    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
