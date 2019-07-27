package com.baby.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.baby.app.R;

/**
 * Created by yongqianggeng on 2018/12/14.
 */

public class DimandDialog extends Dialog {

    public DimandDialog(@NonNull Context context) {
        super(context);
    }

    public DimandDialog(Context context, int theme) {
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
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

    }

    public static class Builder {

        private String message;
        private DimandDialog.DimandDialogLisenter mDimandDialogLisenter;
        private View layout;
        private DimandDialog dialog;
        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            dialog = new DimandDialog(context, com.android.baselibrary.R.style.MyDialog);
            dialog.setCancelable(false);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dimand_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public DimandDialog.Builder setDimandDialogLisenter(DimandDialog.DimandDialogLisenter dimandDialogLisenter){
            this.mDimandDialogLisenter = dimandDialogLisenter;
            return this;
        }

        public DimandDialog.Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        public DimandDialog create() {

            if (message != null) {      //设置提示内容
                ((TextView) layout.findViewById(R.id.messge_content_view)).setText(message);
            }
            layout.findViewById(R.id.dimand_btn_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (mDimandDialogLisenter != null) {
                        mDimandDialogLisenter.onCommitClick();
                    }
                }
            });
            layout.findViewById(R.id.dimand_btn_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (mDimandDialogLisenter != null) {
                        mDimandDialogLisenter.onNoCommitClick();
                    }
                }
            });
            layout.findViewById(R.id.dimand_btn_3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.setContentView(layout);
            dialog.setCancelable(false);     //用户可以点击手机Back键取消对话框显示
            dialog.setCanceledOnTouchOutside(false);        //用户不能通过点击对话框之外的地方取消对话框显示
            return dialog;
        }
    }

    public interface DimandDialogLisenter {
        void onCommitClick();
        void onNoCommitClick();
    }
}
