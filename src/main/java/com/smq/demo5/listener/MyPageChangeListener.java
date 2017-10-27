package com.smq.demo5.listener;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;

import com.smq.demo5.activity.MyOrderActivity;

import java.lang.reflect.Field;

/**
 * Created by shimanqian on 2017/8/1.
 */

public class MyPageChangeListener implements ViewPager.OnPageChangeListener {

    private EdgeEffectCompat leftEdge;
    private EdgeEffectCompat rightEdge;

    MyOrderActivity order;

    public MyPageChangeListener(MyOrderActivity order, ViewPager mViewPager){
        this.order = order;
        try {
            Field leftEdgeField = mViewPager.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = mViewPager.getClass().getDeclaredField("mRightEdge");
            if(leftEdgeField != null && rightEdgeField != null){
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat) leftEdgeField.get(mViewPager);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(mViewPager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(leftEdge != null && rightEdge != null){
            leftEdge.finish();
            rightEdge.finish();
            leftEdge.setSize(0, 0);
            rightEdge.setSize(0, 0);
        }
    }

    @Override
    public void onPageSelected(int position) {
        order.select(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}