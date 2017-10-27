package com.smq.demo5.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smq.demo5.R;
import com.smq.demo5.adapter.StartPagerAdapter;
import com.smq.demo5.util.CacheManage;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager startPager;
    private List<View> viewList = new ArrayList<>();
    private StartPagerAdapter startPagerAdapter;

    private static final int[] startImages = {R.mipmap.start_1, R.mipmap.start_2, R.mipmap.start_3, R.mipmap.start_4};
    //底部小店图片
    private ImageView[] dots;
    //记录当前选中位置
    private int currentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //版本高于4.4 设置透明标题框
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initwidget();
    }
    private void initwidget() {
        startPager = (ViewPager) findViewById(R.id.start_pager);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        for (int i = 0; i < startImages.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(startImages[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            viewList.add(iv);
        }

        startPagerAdapter = new StartPagerAdapter(viewList);
        startPager.setAdapter(startPagerAdapter);
        startPager.setOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }
                    @Override
                    public void onPageSelected(int position) {
                        //设置底部小点选中状态
                        setCurDot(position);
                        if (position == startImages.length - 1) {
                            ImageView view = (ImageView) viewList.get(position);
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CacheManage.setFirstLoad(false);
                                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                }
        );
        initDots();
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        dots = new ImageView[startImages.length];
        //循环取得小点图片
        for (int i = 0; i < startImages.length; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);//都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为白色，即选中状态
        dots[currentIndex].setImageResource(R.mipmap.white_dot);
    }

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= startImages.length) {
            return;
        }
        startPager.setCurrentItem(position);
    }

    /**
     * 这只当前引导小点的选中
     */
    private void setCurDot(int positon) {
        if (positon < 0 || positon > startImages.length - 1 || currentIndex == positon) {
            return;
        }
        dots[positon].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = positon;
        dots[currentIndex].setImageResource(R.mipmap.white_dot);

        for (int i = 0; i < startImages.length; i++) {
            if (i != currentIndex) {
                dots[i].setImageResource(R.mipmap.dark_dot);
            }
        }
    }

    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }
}
