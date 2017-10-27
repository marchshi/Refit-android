package com.smq.mode2.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smq.mode2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.tv_order_rg0)
    TextView rg0;
    @BindView(R.id.tv_order_rg1)
    TextView rg1;
    @BindView(R.id.tv_order_rg2)
    TextView rg2;
    @BindView(R.id.tv_order_rg3)
    TextView rg3;

    public FragmentManager fm;
    public FragmentTransaction ft;

    private MyAskFragment myAskFragment;
    private GetGuideFragment getGuideFragment;
    private GuideOtherFragment guideOtherFragment;
    private HisOrderFragment hisOrderFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_pager,container,false );
        ButterKnife.bind(this,view);
        clear();
        init();
        rg0.setOnClickListener(this);
        rg1.setOnClickListener(this);
        rg2.setOnClickListener(this);
        rg3.setOnClickListener(this);
        setSelectCat(0);
        return view;
    }

    public void refresh(){
        clear();
        init();
        setSelectCat(0);
    }

    public void setSelectCat(int i){
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        hideFragment();
        clear();
        switch (i){
            case 0:
                if(myAskFragment == null){
                    myAskFragment = new MyAskFragment();
                    ft.add(R.id.fl_order,myAskFragment);
                } else {
                    ft.show(myAskFragment);
                    myAskFragment.refresh();
                }
                rg0.setActivated(true);
                rg0.setTextColor(Color.WHITE);
                break;
            case 1:
                if(getGuideFragment == null){
                    getGuideFragment = new GetGuideFragment();
                    ft.add(R.id.fl_order,getGuideFragment);
                } else {
                    ft.show(getGuideFragment);
                }
                rg1.setActivated(true);
                rg1.setTextColor(Color.WHITE);
                break;
            case 2:
                if(guideOtherFragment == null){
                    guideOtherFragment = new GuideOtherFragment();
                    ft.add(R.id.fl_order,guideOtherFragment);
                } else {
                    ft.show(guideOtherFragment);
                }
                rg2.setActivated(true);
                rg2.setTextColor(Color.WHITE);
                break;
            case 3:
                if(hisOrderFragment == null){
                    hisOrderFragment = new HisOrderFragment();
                    ft.add(R.id.fl_order,hisOrderFragment);
                } else {
                    ft.show(myAskFragment);
                }
                rg3.setActivated(true);
                rg3.setTextColor(Color.WHITE);
                break;
        }
        ft.commit();
    }

    public void hideFragment(){
        if(myAskFragment != null){
            ft.hide(myAskFragment);
        }
        if(getGuideFragment != null){
            ft.hide(getGuideFragment);
        }
        if(guideOtherFragment != null){
            ft.hide(guideOtherFragment);
        }
        if(hisOrderFragment != null){
            ft.hide(hisOrderFragment);
        }
    }

    public void clear(){
        rg0.setTextColor(0xFF111111);
        rg1.setTextColor(0xFF111111);
        rg2.setTextColor(0xFF111111);
        rg3.setTextColor(0xFF111111);
        rg0.setActivated(false);
        rg1.setActivated(false);
        rg2.setActivated(false);
        rg3.setActivated(false);
    }
    public void init(){
        TextView title = (TextView) activity.findViewById(R.id.tv_title);
        title.setVisibility(View.VISIBLE);
        title.setText("订单");
        title.setTextSize(20);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_order_rg0:
                setSelectCat(0);
                break;
            case R.id.tv_order_rg1:
                setSelectCat(1);
                break;
            case R.id.tv_order_rg2:
                setSelectCat(2);
                break;
            case R.id.tv_order_rg3:
                setSelectCat(3);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("aaa","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("aaa","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("aaa","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("aaa","onPause");
    }

}
