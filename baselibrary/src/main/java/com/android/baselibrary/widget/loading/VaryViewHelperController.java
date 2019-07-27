package com.android.baselibrary.widget.loading;


import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.R;


public class VaryViewHelperController {

    private IVaryViewHelper helper;

    private AnimationDrawable mDrawableAnim;

    private ImageView mImageView;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    public void showNetworkError(View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.pager_error);
        Button againBtn = (Button) layout.findViewById(R.id.pager_error_loadingAgain);
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener);
        }
        helper.showLayout(layout);
    }

    public void showNetworkError(String msg, int icon, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.pager_error);
        TextView textView = (TextView) layout.findViewById(R.id.tv_net_error);
        ImageView iv_no_data = (ImageView) layout.findViewById(R.id.iv_net_error);
        iv_no_data.setImageResource(icon);
        if (!TextUtils.isEmpty(msg)) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(msg);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }
        Button againBtn = (Button) layout.findViewById(R.id.pager_error_loadingAgain);
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener);
        }
        helper.showLayout(layout);
    }

    public void showEmpty(String emptyMsg, int icon) {
        View layout = helper.inflate(R.layout.page_no_data);
        TextView textView = (TextView) layout.findViewById(R.id.tv_no_data);
        ImageView iv_no_data = (ImageView) layout.findViewById(R.id.iv_no_data);
        iv_no_data.setImageResource(icon);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        }
        helper.showLayout(layout);
    }

    public void showLoading() {
        View layout = helper.inflate(R.layout.pager_loading);
        helper.showLayout(layout);
    }

    public void hideloading() {
        helper.restoreView();
    }

    public void restore() {
        helper.restoreView();
    }
}
