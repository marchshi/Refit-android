package com.smq.demo5.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.smq.demo5.R;

/**
 * Created by shimanqian on 2017/8/20.
 */

public class ChatLinearLayout extends ViewGroup{

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mXLastMove;

    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;

    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;

    /**
     * 删除界面的宽度
     */
    private int deleteWidth;

    public boolean scrollable = false;

    /**
     * 构造方法 最先调用 进行各种初始化
     * @param context
     * @param attrs
     */
    public ChatLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        //获取XML中的信息
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChatLinearLayout,0,0);
        scrollable = a.getBoolean(R.styleable.ChatLinearLayout_scrollable,false);
        a.recycle();//循环使用？
        // 第一步，创建Scroller的实例
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        setClickable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            View chatView = getChildAt(0);
            View deleteView = getChildAt(1);
            // 为ScrollerLayout中的每一个子控件在水平方向上进行布局
            chatView.layout(0, 0,chatView.getMeasuredWidth(), chatView.getMeasuredHeight());
            deleteView.layout(chatView.getMeasuredWidth(), 0,chatView.getMeasuredWidth() + deleteView.getMeasuredWidth(), deleteView.getMeasuredHeight());

            // 初始化左右边界值
            leftBorder = chatView.getLeft();
            rightBorder = deleteView.getRight();
            deleteWidth = deleteView.getMeasuredWidth();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(scrollable){
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mXDown = ev.getRawX();
                    mXLastMove = mXDown;
                    break;
                case MotionEvent.ACTION_MOVE:
                    mXMove = ev.getRawX();
                    float diff = Math.abs(mXMove - mXDown);
                    mXLastMove = mXMove;
                    // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                    if (diff > mTouchSlop) {
                        return true;
                    }
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(scrollable){
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    mXMove = event.getRawX();
                    int scrolledX = (int) (mXLastMove - mXMove);
                    if (getScrollX() + scrolledX < leftBorder) {
                        scrollTo(leftBorder, 0);
                        return true;
                    } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {
                        scrollTo(rightBorder - getWidth(), 0);
                        return true;
                    }
                    scrollBy(scrolledX, 0);
                    mXLastMove = mXMove;
                    break;
                case MotionEvent.ACTION_UP:
                    // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                    if(getScrollX() < deleteWidth/2 ){
                        // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                        mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
                    }else {
                        mScroller.startScroll(getScrollX(), 0, deleteWidth-getScrollX(), 0);
                    }
                    invalidate();
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public boolean getScrollable() {
        return this.scrollable;
    }
}
