package com.baby.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.service.bean.mine.PayBean;
import com.android.baselibrary.service.bean.mine.VipBean;
import com.baby.app.R;
import com.baby.app.modules.mine.presenter.VipPresenter;
import com.baby.app.modules.mine.view.IVipView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/10.
 * 支付弹窗
 */

public class PayDialog extends Dialog {

    public PayDialog(@NonNull Context context) {
        super(context);
    }

    public PayDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.CENTER;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(20, 0, 20, 0);
        getWindow().setAttributes(layoutParams);

    }

    public static class Builder {

        private PayDialogDialogLisenter mPayDialogDialogLisenter;
        private View layout;
        private PayDialog dialog;

        private RecyclerView mRecyclerView;
        private PayAdapter mPayAdapter;
        private List<PayBean.Data>payBeanList = new ArrayList<>();

        private VipPresenter mVipPresenter;

        private Context mContext;

        public Builder(Context context) {
            this.mContext = context;
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            dialog = new PayDialog(context, com.android.baselibrary.R.style.MyDialog);
            dialog.setCancelable(false);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.pay_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public Builder setPayDialogDialogLisenter(PayDialogDialogLisenter payDialogDialogLisenter){
            this.mPayDialogDialogLisenter = payDialogDialogLisenter;
            return this;
        }

        public Builder setPayBeanList(List<PayBean.Data> payBeanList) {
            this.payBeanList = payBeanList;
            return this;
        }

        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        public PayDialog create() {

            layout.findViewById(R.id.pay_close_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
//            RelativeLayout pay_root_view = (RelativeLayout)layout.findViewById(R.id.pay_root_view);
//            pay_root_view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });

            mRecyclerView = (RecyclerView) layout.findViewById(R.id.pay_recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mPayAdapter = new PayAdapter(R.layout.item_pay_layout,payBeanList);
            mRecyclerView.setAdapter(mPayAdapter);
            mPayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    PayBean.Data data = payBeanList.get(position);
                    if (data.getPayType() == 1) { //支付宝
                        if (mPayDialogDialogLisenter!=null) {
                            mPayDialogDialogLisenter.onZfbClick(String.valueOf(data.getPayType()));
                        }
                    } else { //微信
                        if (mPayDialogDialogLisenter!=null) {
                            mPayDialogDialogLisenter.onWxClick(String.valueOf(data.getPayType()));
                        }
                    }
                    dialog.dismiss();
                }
            });

//            mVipPresenter = new VipPresenter(new IVipView() {
//                @Override
//                public void refreshVip(VipBean vipBean) {
//
//                }
//
//                @Override
//                public void refreshPayType(PayBean payBean) {
//                    payBeanList.clear();
//                    mPayAdapter.addData(payBean.getData());
//                }
//
//                @Override
//                public void showDialogLoading() {
//
//                }
//
//                @Override
//                public void showDialogLoading(String msg) {
//
//                }
//
//                @Override
//                public void hideDialogLoading() {
//
//                }
//
//                @Override
//                public void showPageLoading() {
//
//                }
//
//                @Override
//                public void hidePageLoading() {
//
//                }
//
//                @Override
//                public void refreshView() {
//
//                }
//
//                @Override
//                public void showNetError() {
//
//                }
//
//                @Override
//                public void showNetError(String msg, int icon) {
//
//                }
//
//                @Override
//                public void showEmptyView(String msg, int icon) {
//
//                }
//
//                @Override
//                public void showToast(String msg) {
//
//                }
//
//                @Override
//                public void showRequestOutData() {
//
//                }
//
//                @Override
//                public void gotoLogin() {
//
//                }
//
//                @Override
//                public void openActivity(Class<?> pClass) {
//
//                }
//
//                @Override
//                public void openActivity(Class<?> pClass, Bundle pBundle) {
//
//                }
//
//                @Override
//                public void showTopTip(String text, int res) {
//
//                }
//            });
//            mVipPresenter.fetchPayTypeList();

            dialog.setContentView(layout);
//            dialog.setCancelable(true);     //用户可以点击手机Back键取消对话框显示
            dialog.setCanceledOnTouchOutside(true);        //用户不能通过点击对话框之外的地方取消对话框显示
            return dialog;
        }
    }

    public interface PayDialogDialogLisenter {
        void onWxClick(String payType);
        void onZfbClick(String payType);
    }

}
