package com.smq.demo5.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by shimanqian on 2017/8/1.
 */

public class OrderPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list;

    public OrderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public OrderPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
