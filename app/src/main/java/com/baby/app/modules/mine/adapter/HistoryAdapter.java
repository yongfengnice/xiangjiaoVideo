package com.baby.app.modules.mine.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.mine.HistoryBean;
import com.android.baselibrary.util.GlideUtils;
import com.bumptech.glide.Glide;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/22.
 */

public class HistoryAdapter extends BaseQuickAdapter<HistoryBean,BaseViewHolder> {

    private boolean isEdit = false;

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean getIsEdit() {
        return isEdit;
    }

    public HistoryAdapter(int layoutResId, @Nullable List<HistoryBean> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, final HistoryBean item) {
        ImageView imageView = helper.getView(R.id.lh_img_view);
        TextView lh_name_text = helper.getView(R.id.lh_name_text);
        TextView lh_count_view = helper.getView(R.id.lh_count_view);

        RelativeLayout check_button = helper.getView(R.id.check_button);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setSelected(true);
                notifyDataSetChanged();
            }
        });
        ImageView check_img_view = helper.getView(R.id.check_img_view);
        if (item.getSelected()) {
            check_img_view.setImageResource(R.mipmap.icon_checked);
        } else {
            check_img_view.setImageResource(R.mipmap.icon_uncheck);
        }
        if (isEdit) {
            check_button.setVisibility(View.VISIBLE);
        } else {
            check_button.setVisibility(View.GONE);
        }

        GlideUtils
                .getInstance()
                .LoadNewContextBitmap(mContext,
                        item.getVideoCover(),
                        imageView,
                        R.mipmap.video_cover,
                        R.mipmap.video_cover,
                        GlideUtils.LOAD_BITMAP);


        lh_name_text.setText(item.getVideoName());
        lh_count_view.setText(item.getViewTime());
    }
}
