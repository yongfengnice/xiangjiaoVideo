package com.baby.app.modules.mine.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.baby.app.R;
import com.baby.app.modules.mine.bean.PromoteFuliBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/10.
 * 福利任务
 */

public class PromoteFuliAdapter extends BaseQuickAdapter<PromoteFuliBean,BaseViewHolder> {
    public PromoteFuliAdapter(int layoutResId, @Nullable List<PromoteFuliBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PromoteFuliBean item) {
        View topLine = helper.getView(R.id.fuli_top_line);
//        View bottomLine = helper.getView(R.id.fuli_bottom_line);
        TextView titleView = helper.getView(R.id.fuli_title_view);

        TextView subTitleView = helper.getView(R.id.fuli_sub_msg);
        if (item.getIsTopHidden()) {
            topLine.setVisibility(View.INVISIBLE);
        } else {
            topLine.setVisibility(View.VISIBLE);
        }
//        if (item.getIsBottomHidden()) {
//            bottomLine.setVisibility(View.INVISIBLE);
//        } else {
//            bottomLine.setVisibility(View.VISIBLE);
//        }
        subTitleView.setText(item.getSpanned());
        titleView.setText(item.getTitle());

    }
}
