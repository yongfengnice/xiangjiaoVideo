package com.baby.app.modules.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.util.GlideUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/1.
 * 女优
 */

public class StarListAdapter extends BaseQuickAdapter <HomeStarBean,BaseViewHolder>{
    public StarListAdapter(int layoutResId, @Nullable List<HomeStarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeStarBean item) {
        ImageView imageView = helper.getView(R.id.star_img_view);
        TextView textView = helper.getView(R.id.star_text_view);
        GlideUtils
                .getInstance()
                .LoadContextCircleBitmap(mContext,
                        item.getHeadpic(),
                        imageView,
                        R.mipmap.loading,
                        R.mipmap.loading);
        textView.setText(item.getName());
    }
}
