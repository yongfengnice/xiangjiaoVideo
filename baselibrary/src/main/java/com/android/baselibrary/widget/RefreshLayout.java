package com.android.baselibrary.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by digua
 *
 * 解决banner和SwipeRefreshLayout冲突的问题
 *
 * on 2016/11/9.
 */

public class RefreshLayout extends SwipeRefreshLayout {
    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    float lastx = 0;
    float lasty = 0;
    boolean ismovepic = false;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {



        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            lastx = ev.getX();
            lasty = ev.getY();
            ismovepic = false;
            return super.onInterceptTouchEvent(ev);
        }

        final int action = MotionEventCompat.getActionMasked(ev);

        int x2 = (int) Math.abs(ev.getX() - lastx);
        int y2 = (int) Math.abs(ev.getY() - lasty);

        //滑动图片最小距离检查
        if (x2>y2){
            if (x2>=100)ismovepic = true;
            return false;
        }

        //是否移动图片(下拉刷新不处理)
        if (ismovepic){
            return false;
        }

        boolean isok = super.onInterceptTouchEvent(ev);

        return isok;
    }
}
