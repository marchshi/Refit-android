package com.smq.demo5.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**引导页界面adpter
 * Created by shiweiwei on 2015/10/31.
 */
public class StartPagerAdapter extends PagerAdapter {

    private List<View> viewList = new ArrayList<View>();
    public StartPagerAdapter(List<View> viewList)
    {
        this.viewList = viewList;
    }
    @Override
    public int getCount() {
        return viewList.size();
    }
    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(viewList.get(arg1), 0);
        return viewList.get(arg1);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(viewList.get(arg1));
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}
