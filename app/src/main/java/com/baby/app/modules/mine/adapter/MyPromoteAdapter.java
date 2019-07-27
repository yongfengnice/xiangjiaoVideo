package com.baby.app.modules.mine.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.mine.MyPromoteBean;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/20.
 */

public class MyPromoteAdapter extends BaseQuickAdapter<MyPromoteBean.Data,BaseViewHolder> {
    public MyPromoteAdapter(int layoutResId, @Nullable List<MyPromoteBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyPromoteBean.Data item) {
        TextView promote_user_name = helper.getView(R.id.promote_user_name);
        TextView promote_phone_name = helper.getView(R.id.promote_user_phone);
        TextView promote_time_name = helper.getView(R.id.promote_user_time);

        TextView promote_price = helper.getView(R.id.promote_price);
        TextView promote_income = helper.getView(R.id.promote_income);

        RelativeLayout view_righ_1 = helper.getView(R.id.view_righ_1);
        RelativeLayout view_righ_2 = helper.getView(R.id.view_righ_2);
        if (item.getNickName()!=null && item.getNickName().length() > 0) {
            view_righ_1.setVisibility(View.GONE);
            view_righ_2.setVisibility(View.GONE);

            promote_user_name.setText(item.getNickName());
            promote_phone_name.setText(item.getTel());
            promote_time_name.setText(item.getRegeditTime());

        } else {
            view_righ_1.setVisibility(View.VISIBLE);
            view_righ_2.setVisibility(View.VISIBLE);

            promote_user_name.setText(item.getName());
            promote_phone_name.setText(item.getTel());
            promote_time_name.setText(item.getRechargeDate());
            promote_price.setText(item.getRechargePrice()+"");
            promote_income.setText(item.getIncome()+"");
        }



    }
}
