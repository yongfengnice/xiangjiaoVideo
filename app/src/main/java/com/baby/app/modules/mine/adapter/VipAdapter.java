package com.baby.app.modules.mine.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.mine.VipBean;
import com.baby.app.R;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/8.
 * vip
 */

public class VipAdapter extends BaseQuickAdapter<VipBean.Data,BaseViewHolder> {

    private VipAdapterLisnter mVipAdapterLisnter;

    public void setmVipAdapterLisnter(VipAdapterLisnter mVipAdapterLisnter) {
        this.mVipAdapterLisnter = mVipAdapterLisnter;
    }

    public VipAdapter(int layoutResId, @Nullable List<VipBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final VipBean.Data item) {
        helper.setText(R.id.vip_price_view,item.getPrice());
        helper.setText(R.id.vip_sub_title_view,item.getDescribe());
        ImageView imageView = helper.getView(R.id.vip_img_view);

        switch (item.getCardType()) {
            case 1:
                imageView.setBackgroundResource(R.mipmap.card_30);
                break;
            case 2:
                imageView.setBackgroundResource(R.mipmap.card_90);
                break;
            case 3:
                imageView.setBackgroundResource(R.mipmap.card_365);
                break;
        }
        final  VipBean.Data item1 = item;
        helper.getView(R.id.buy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mVipAdapterLisnter.gotoBuy(item1);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVipAdapterLisnter.gotoBuy(item1);
            }
        });
    }

    public interface VipAdapterLisnter{
        void gotoBuy(VipBean.Data data);
    }
}
