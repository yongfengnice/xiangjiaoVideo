package com.baby.app.modules.home.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.HomeListBean;
import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.util.GlideUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/26.
 */

public class HomeManAdapter extends BaseQuickAdapter<HomeStarBean,BaseViewHolder> {
    public HomeManAdapter(int layoutResId, @Nullable List<HomeStarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeStarBean item) {

        RoundedImageView userImageView = helper.getView(R.id.user_img_view);
        TextView userNameTextView = helper.getView(R.id.user_name_text_view);
        TextView infoTextView = helper.getView(R.id.user_info_text_view);
        TextView view_num_text_view = helper.getView(R.id.view_num_text_view);
        GlideUtils
                .getInstance()
                .LoadContextCircleBitmap(mContext,
                        item.getHeadpic(),
                        userImageView,
                        R.mipmap.loading,
                        R.mipmap.loading);
        userNameTextView.setText(item.getName());
        infoTextView.setText(item.getBriefContext());
        view_num_text_view.setText(item.getVideoNum()+"部电影");

    }
}
