package com.smq.demo5.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smq.demo5.R;
import com.smq.demo5.adapter.OrderPagerAdapter;
import com.smq.demo5.fragment.GetGuideFragment;
import com.smq.demo5.fragment.HisOrderFragment;
import com.smq.demo5.fragment.MyAskFragment;
import com.smq.demo5.fragment.ToGuideFragment;
import com.smq.demo5.listener.MyPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyOrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_title_back)
    ImageView img_title_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_order_page1)
    TextView tv_order_page1;
    @BindView(R.id.tv_order_page2)
    TextView tv_order_page2;
    @BindView(R.id.tv_order_page3)
    TextView tv_order_page3;
    @BindView(R.id.tv_order_page4)
    TextView tv_order_page4;

    public FragmentManager fm;
    public FragmentTransaction ft;

    MyAskFragment myAskFragment;
    GetGuideFragment getGuideFragment;
    ToGuideFragment toGuideFragment;
    HisOrderFragment hisOrderFragment;

    List<Fragment> listFragment = new ArrayList<Fragment>();

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setbodyView(R.layout.activity_my_order);
        init();
        initview();
        viewPager.setAdapter(new OrderPagerAdapter(fm,listFragment));
        bindOnClick(new int[]{R.id.img_title_back,R.id.tv_order_page1,R.id.tv_order_page2,R.id.tv_order_page3,R.id.tv_order_page4},this);
        viewPager.setOnPageChangeListener(new MyPageChangeListener(this,viewPager));
        int page = getIntent().getIntExtra("page",0);
        select(page);
    }

    public void init(){
        tv_title.setText("我的订单");
        tv_title.setTextSize(18);
        img_title_back.setVisibility(View.VISIBLE);
        fm = getSupportFragmentManager();
        myAskFragment = new MyAskFragment();
        getGuideFragment = new GetGuideFragment();
        toGuideFragment = new ToGuideFragment();
        hisOrderFragment = new HisOrderFragment();
        listFragment.add(myAskFragment);
        listFragment.add(getGuideFragment);
        listFragment.add(toGuideFragment);
        listFragment.add(hisOrderFragment);
        viewPager = (ViewPager) findViewById(R.id.vp_order_pager);
    }

    private void initview(){
        tv_order_page1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tv_order_page2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tv_order_page3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tv_order_page4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tv_order_page1.setTextColor(getResources().getColor(R.color.white));
        tv_order_page2.setTextColor(getResources().getColor(R.color.white));
        tv_order_page3.setTextColor(getResources().getColor(R.color.white));
        tv_order_page4.setTextColor(getResources().getColor(R.color.white));
    }

    public void select(int i){
        switch (i){
            case 0:
                viewPager.setCurrentItem(0,false);
                initview();
                tv_order_page1.setBackgroundColor(getResources().getColor(R.color.white));
                tv_order_page1.setTextColor(getResources().getColor(R.color.text_default));
                break;
            case 1:
                viewPager.setCurrentItem(1,false);
                initview();
                tv_order_page2.setBackgroundColor(getResources().getColor(R.color.white));
                tv_order_page2.setTextColor(getResources().getColor(R.color.text_default));
                break;
            case 2:
                viewPager.setCurrentItem(2,false);
                initview();
                tv_order_page3.setBackgroundColor(getResources().getColor(R.color.white));
                tv_order_page3.setTextColor(getResources().getColor(R.color.text_default));
                break;
            case 3:
                viewPager.setCurrentItem(3,false);
                initview();
                tv_order_page4.setBackgroundColor(getResources().getColor(R.color.white));
                tv_order_page4.setTextColor(getResources().getColor(R.color.text_default));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_title_back:
                finish();
                break;
            case R.id.tv_order_page1:
                select(0);
                break;
            case R.id.tv_order_page2:
                select(1);
                break;
            case R.id.tv_order_page3:
                select(2);
                break;
            case R.id.tv_order_page4:
                select(3);
                break;
        }
    }
}
