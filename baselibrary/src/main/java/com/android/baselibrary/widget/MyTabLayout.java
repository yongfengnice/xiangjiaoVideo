package com.android.baselibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

/**
 * Created by yongqianggeng on 2018/9/18.
 */

public class MyTabLayout extends TabLayout {
    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public void draw(Canvas canvas) {
//        super.draw(canvas);
//
//        if (mIndicatorLeft >= 0 && mIndicatorRight > mIndicatorLeft) {
//            canvas.drawRect(mIndicatorLeft+dpToPx(25), getHeight() - mSelectedIndicatorHeight,
//                    mIndicatorRight-dpToPx(25), getHeight(), mSelectedIndicatorPaint);
//        }
//    }
}
