package com.android.baselibrary.widget.title;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.R;


/**
 * name qiuxianfu
 * 2016/3/17.
 */
public class TitleBuilder implements View.OnClickListener {

    /**
     * title栏根布局
     */
    public View mTitleView;// titleView
    protected RelativeLayout mTitleBgColor;
    protected RelativeLayout mTitleLeftRelativeLayout;//title左边布局
    protected RelativeLayout mTitleMiddleRelativeLayout;//title中间布局
    protected RelativeLayout mTitleRightRelativeLayout;//title右边布局
    protected ImageView mTitleLeftImageView;//title左边icon显示
    protected TextView mTitleLeftTextView;//title左边文本显示
    protected TextView mTitleMiddleTextView;//title中间文本显示
    protected TextView mSubTitleTextView;//副标题文本显示
    protected TextView mTitleRightTextView;//title右边文本显示
    protected ImageView mTitleRightImageView;//title右边图片显示
    protected ImageView mTitleRightTips;//title右边小红点提示
    protected FrameLayout mLeftFrameLayout;//左边view
    protected FrameLayout mRightFrameLayout;//右边view
    private FragmentActivity activity;
    private TitleBuilderListener mListener;


    public enum TitleButton {
        LEFT, MIDDLE, RIGHT
    }
    /**
     * 第一种  初始化方式
     * 定义一个容器加载title xml和activity布局文件
     *
     * @param activity
     */
    public TitleBuilder(FragmentActivity activity) {
        this.activity = activity;
    }

    /**
     * 设置一个布局容器，装载title_layout和传进来的layoutId 返回一个View视图
     *
     * @param titleView
     * @return
     */
    public void initBar(View titleView) {
        this.mTitleView = titleView;
        mTitleBgColor = (RelativeLayout) mTitleView.findViewById(R.id.title_bar_id);
        mTitleLeftRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.left_relative_layout);
        mTitleMiddleRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.mid_relative_layout);
        mTitleRightRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.right_relative_layout);
        mTitleLeftImageView = (ImageView) mTitleView.findViewById(R.id.left_icon_title);
        mTitleLeftTextView = (TextView) mTitleView.findViewById(R.id.left_btn);
        mTitleMiddleTextView = (TextView) mTitleView.findViewById(R.id.middle_text);
        mSubTitleTextView = (TextView) mTitleView.findViewById(R.id.middle_sub_text);
        mTitleRightTextView = (TextView) mTitleView.findViewById(R.id.title_right_text);
        mTitleRightImageView = (ImageView) mTitleView.findViewById(R.id.title_right_image_view);
        mTitleRightTips = (ImageView) mTitleView.findViewById(R.id.right_new_info);
        mTitleRightTips = (ImageView) mTitleView.findViewById(R.id.right_new_info);
        mLeftFrameLayout = (FrameLayout) mTitleView.findViewById(R.id.title_left_view);
        mRightFrameLayout = (FrameLayout) mTitleView.findViewById(R.id.title_right_view);

        //RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) mRootView.getLayoutParams();//面板没有全屏
        //mTitleLeftText.setOnClickListener(this);
        mTitleLeftRelativeLayout.setOnClickListener(this);
        mTitleRightRelativeLayout.setOnClickListener(this);
        mTitleMiddleRelativeLayout.setOnClickListener(this);
    }

    /**
     * 第二种初始化方式
     * 这里是比如你用代码创建布局的时候使用
     *
     * @param context
     */
    public TitleBuilder(View context) {

        mTitleView = context.findViewById(R.id.title_bar_id);

//        mTitleLeftRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.left_relative_layout);
//        mTitleMiddleRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.mid_relative_layout);
//        mTitleRightRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.right_relative_layout);
        mTitleLeftImageView = (ImageView) mTitleView.findViewById(R.id.left_icon_title);
        mTitleLeftTextView = (TextView) mTitleView.findViewById(R.id.left_btn);
        mTitleMiddleTextView = (TextView) mTitleView.findViewById(R.id.middle_text);
        mTitleRightTextView = (TextView) mTitleView.findViewById(R.id.title_right_text);
        mTitleRightImageView = (ImageView) mTitleView.findViewById(R.id.title_right_image_view);
        mTitleRightTips = (ImageView) mTitleView.findViewById(R.id.right_new_info);


    }

    public RelativeLayout getmTitleRightRelativeLayout() {
        return mTitleRightRelativeLayout;
    }

    /**
     * 清除所有布局
     */
    public void clearAllView() {
        mTitleLeftImageView.setVisibility(View.INVISIBLE);
        mTitleLeftTextView.setVisibility(View.INVISIBLE);
        mTitleMiddleTextView.setVisibility(View.INVISIBLE);
        mTitleRightTextView.setVisibility(View.INVISIBLE);
        mTitleRightImageView.setVisibility(View.INVISIBLE);
        mTitleRightTips.setVisibility(View.INVISIBLE);
    }


    /**
     * 设置左侧菜单显示图片
     *
     * @param resId
     * @return
     */
    public TitleBuilder setLeftDrawable(int resId) {
        //mTitleLeftText.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        mTitleLeftImageView.setVisibility(resId > 0 ? View.VISIBLE : View.INVISIBLE);
        mTitleLeftImageView.setImageResource(resId);
        return this;
    }

    /**
     * 左边文字按钮
     *
     * @param text
     * @return
     */
    public TitleBuilder setLeftText(String text) {
//        mTitleLeftTextView.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTitleLeftTextView.setVisibility(View.GONE);
        mTitleLeftTextView.setText(text);
        return this;
    }

    /**
     * 左边添加View
     *
     * @return
     */
    public TitleBuilder setLeftView(View view) {
        mLeftFrameLayout.addView(view);
        mLeftFrameLayout.setVisibility(View.VISIBLE);
        mTitleLeftTextView.setVisibility(View.GONE);
        mTitleLeftImageView.setVisibility(View.GONE);
        return this;
    }

    /**
     * 右边添加View
     *
     * @return
     */
    public TitleBuilder setRightView(View view) {
        mRightFrameLayout.addView(view);
        mRightFrameLayout.setVisibility(View.VISIBLE);
        mTitleRightTextView.setVisibility(View.GONE);
        mTitleRightImageView.setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置左边文本的颜色
     *
     * @param colorId 颜色值的ID
     * @return
     */
    public TitleBuilder setLeftTextColor(int colorId) {
        mTitleLeftTextView.setTextColor(colorId);
        return this;
    }

    /**
     * 设置左边文本字体大小
     *
     * @param size 颜色值的ID
     * @return
     */
    public TitleBuilder setLeftTextSize(float size) {
        mTitleLeftTextView.setTextSize(size);
        return this;
    }

    /**
     * 设置左边文本的样式
     *
     * @param type 颜色值的ID
     * @return
     */
    public TitleBuilder setLeftTextType(Typeface type) {
        mTitleLeftTextView.setTypeface(type);
        return this;
    }

    /**
     * 获取左边View
     *
     * @return
     */
    public TextView getMiddleTextView() {

        return (mTitleMiddleTextView.getVisibility() == View.GONE) ? mSubTitleTextView : mTitleMiddleTextView;
    }

    /**
     * 获取右边View
     *
     * @return
     */
    public ImageView getRightImageView() {

        return mTitleRightImageView;
    }


    /**
     * 设置右边文本的颜色
     *
     * @param colorId 颜色值的ID
     * @return
     */
    public TitleBuilder setRightTextColor(int colorId) {
        mTitleRightTextView.setTextColor(colorId);
        return this;
    }

    /**
     * 设置右边文本是否可以点击
     *
     * @param clickEnable 颜色值的ID
     * @return
     */
    public TitleBuilder setRightTextClickEnable(boolean clickEnable) {
        mTitleRightTextView.setEnabled(clickEnable);
        mTitleRightRelativeLayout.setEnabled(clickEnable);
        mTitleRightImageView.setEnabled(clickEnable);
        return this;
    }

    /**
     * 设置中间文本框右边的图标
     *
     * @param drawable 图片
     * @return
     */
    public TitleBuilder setMiddleTextRgihtDrawable(int drawable) {
        mTitleMiddleTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);
        mTitleMiddleTextView.setCompoundDrawablePadding(8);
        return this;
    }

    /**
     * 设置右边文本字体大小
     *
     * @param size 颜色值的ID
     * @return
     */
    public TitleBuilder setLeftRightSize(float size) {
        mTitleRightTextView.setTextSize(size);
        return this;
    }

    /**
     * 设置右边文本的样式
     *
     * @param type 颜色值的ID
     * @return
     */
    public TitleBuilder setLeftRightType(Typeface type) {
        mTitleRightTextView.setTypeface(type);
        return this;
    }

    /**
     * 设置中间文本字体大小
     *
     * @param size 颜色值的ID
     * @return
     */
    public TitleBuilder setLeftMiddleSize(float size) {
        mTitleMiddleTextView.setTextSize(size);
        return this;
    }

    /**
     * 设置中间文本的样式
     *
     * @param type 颜色值的ID
     * @return
     */
    public TitleBuilder setLeftMiddleType(Typeface type) {
        mTitleMiddleTextView.setTypeface(type);
        return this;
    }

    /**
     * 设置中间文本的样式
     *
     * @param
     * @return
     */
    public TitleBuilder setLeftMiddleColor(int colorId) {
        mTitleMiddleTextView.setTextColor(colorId);
        return this;
    }


    /**
     * title 的背景色
     */

    public TitleBuilder seTitleBgRes(int resId) {
        mTitleView.setBackgroundResource(resId);

        return this;
    }

    /**
     * title 的背景色
     */

    public TitleBuilder seTitleBgColor(int color) {
        mTitleView.setBackgroundColor(color);

        return this;
    }

    /**
     * title的文本
     *
     * @param text
     * @return
     */
    public TitleBuilder setMiddleTitleText(String text) {

        mTitleMiddleTextView.setVisibility(TextUtils.isEmpty(text) ? View.GONE
                : View.VISIBLE);
        mSubTitleTextView.setVisibility(View.GONE);
        mTitleMiddleTextView.setText(text);

        return this;
    }

    public TitleBuilder setMiddleTitleTextColor(int color) {

        mTitleMiddleTextView.setTextColor(color);

        return this;
    }

    /**
     * 设置副标题
     *
     * @param subTitleText
     * @return
     */
    public TitleBuilder setSubTitleText(String subTitleText) {
        mSubTitleTextView.setVisibility(TextUtils.isEmpty(subTitleText) ? View.GONE
                : View.VISIBLE);
        mTitleMiddleTextView.setVisibility(View.GONE);
        mSubTitleTextView.setText(subTitleText);
        return this;
    }

    /**
     * 设置副标题
     *
     * @return
     */
    public TitleBuilder setSubTitleRightDrawable(int drawable) {
        mSubTitleTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);
        mSubTitleTextView.setCompoundDrawablePadding(8);
        return this;
    }

    /**
     * 设置左边的事件
     */
    public TitleBuilder setLeftTextOrImageListener(View.OnClickListener listener) {

        /*if (mTitleLeftImageView.getVisibility() == View.VISIBLE) {

            mTitleLeftImageView.setOnClickListener(listener);

        } else if (mTitleLeftText.getVisibility() == View.VISIBLE) {

            mTitleLeftText.setOnClickListener(listener);

        }*/

        mTitleLeftRelativeLayout.setOnClickListener(listener);

        return this;
    }

    /**
     * right
     */
    /**
     * 图片按钮
     *
     * @param resId
     * @return
     */
    public TitleBuilder setRightImageRes(int resId) {

        mTitleRightImageView.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        if(resId > 0){
            mTitleRightImageView.setBackgroundResource(resId);
        }
        return this;
    }

    /**
     * 左边文字按钮
     *
     * @param text
     * @return
     */
    public TitleBuilder setRightText(String text) {
        mTitleRightTextView.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTitleRightTextView.setText(text);
        return this;
    }

    /**
     * 设置tips图片
     *
     * @param resId
     * @return
     */
    public TitleBuilder setTipsDrawable(int resId) {
        mTitleRightTips.setVisibility(resId == 0 ? View.GONE : View.VISIBLE);
        mTitleRightTips.setBackgroundResource(resId);
        return this;
    }

    public TitleBuilder setTipsVisible(int visible) {
        if (mTitleRightTips.getVisibility() == visible) {
            return this;
        } else {
            mTitleRightTips.setVisibility(visible);
        }

        return this;
    }

    /**
     * 设置左边的事件
     */
    public TitleBuilder setRightTextOrImageListener(View.OnClickListener listener) {

        if (mTitleRightImageView.getVisibility() == View.VISIBLE) {

            mTitleRightImageView.setOnClickListener(listener);

        } else if (mTitleRightTextView.getVisibility() == View.VISIBLE) {

            mTitleRightTextView.setOnClickListener(listener);

        }

        return this;
    }

    /**
     * 设置按钮是否可以使用
     *
     * @param enable
     * @return
     */
    public TitleBuilder setLeftButtonEnabled(boolean enable) {
        mTitleLeftTextView.setEnabled(enable);
        return this;
    }

    /**
     * 设置title中间不可以操作
     *
     * @param enable
     * @return
     */
    public TitleBuilder setMiddleTextViewEnabled(boolean enable) {
        mTitleMiddleRelativeLayout.setEnabled(enable);
        return this;
    }

    /**
     * 设置title中间不可以操作
     *
     * @param enable
     * @return
     */
    public TitleBuilder setRightViewEnabled(boolean enable) {
        mTitleRightRelativeLayout.setEnabled(enable);
        return this;
    }

    /**
     * 设置监听事件
     *
     * @param mTitleBuilderListener
     */
    public void setTitleBuilderListener(TitleBuilderListener mTitleBuilderListener) {
        this.mListener = mTitleBuilderListener;
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        if (view == mTitleLeftImageView || view == mTitleLeftTextView || view == mTitleLeftRelativeLayout) {
            mListener.onTitleButtonClicked(TitleButton.LEFT);
        } else if (view == mTitleMiddleTextView || view == mTitleMiddleRelativeLayout) {
            mListener.onTitleButtonClicked(TitleButton.MIDDLE);
        } else if (view == mTitleRightRelativeLayout || view == mTitleRightImageView || view == mTitleRightTextView) {
            mListener.onTitleButtonClicked(TitleButton.RIGHT);
        }
    }

    public interface TitleBuilderListener {
        void onTitleButtonClicked(TitleButton clicked);
    }
}
