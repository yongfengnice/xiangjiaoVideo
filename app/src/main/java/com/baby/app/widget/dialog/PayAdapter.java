package com.baby.app.widget.dialog;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.mine.PayBean;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/19.
 *
 */

public class PayAdapter extends BaseQuickAdapter<PayBean.Data,BaseViewHolder> {

    public PayAdapter(int layoutResId, @Nullable List<PayBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayBean.Data item) {
//        支付方式 1=支付宝 2=微信
        ImageView pay_img_view = helper.getView(R.id.pay_img_view);
        TextView textView = helper.getView(R.id.pay_text_view);
        if (item.getPayType() == 1) {
            GlideUtils
                    .getInstance()
                    .LoadNewContextBitmap(mContext,
                            item.getPayImag(),
                            pay_img_view,
                            R.mipmap.zfb,
                            R.mipmap.zfb,
                            GlideUtils.LOAD_BITMAP);
        } else if (item.getPayType() == 2) {
            GlideUtils
                    .getInstance()
                    .LoadNewContextBitmap(mContext,
                            item.getPayImag(),
                            pay_img_view,
                            R.mipmap.wx,
                            R.mipmap.wx,
                            GlideUtils.LOAD_BITMAP);
        }

        textView.setText(item.getPayTypeName());
    }
}
