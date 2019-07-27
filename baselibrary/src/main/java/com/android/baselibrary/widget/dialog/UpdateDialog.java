package com.android.baselibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.baselibrary.R;

/**
 * Created by yongqianggeng on 2018/8/25.
 *
 */

public class UpdateDialog extends Dialog {

    public UpdateDialog(Context context) {
        super(context);
    }

    public UpdateDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(20, 0, 20, 0);
        getWindow().setAttributes(layoutParams);

    }

    public static class Builder {
        private String title;
        private View contentView;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private String singleButtonText;
        private View.OnClickListener positiveButtonClickListener;
        private View.OnClickListener negativeButtonClickListener;
        private View.OnClickListener singleButtonClickListener;

        public UpdateDialog.UpdateDialogDialogButtonClickLisener updateDialogDialogButtonClickLisener;

        private View layout;
        private UpdateDialog dialog;

        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            dialog = new UpdateDialog(context, R.style.MyDialog);
            dialog.setCancelable(false);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_update_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public String getTitle() {
            return title;
        }

        public UpdateDialog.Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public UpdateDialog.Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        public UpdateDialog.Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public UpdateDialog.Builder setPositiveButton(String positiveButtonText, UpdateDialog.UpdateDialogDialogButtonClickLisener updateDialogDialogButtonClickLisener) {
            this.positiveButtonText = positiveButtonText;
            this.updateDialogDialogButtonClickLisener = updateDialogDialogButtonClickLisener;
            return this;
        }

        public UpdateDialog.Builder setNegativeButton(String negativeButtonText, UpdateDialog.UpdateDialogDialogButtonClickLisener updateDialogDialogButtonClickLisener) {
            this.negativeButtonText = negativeButtonText;
            this.updateDialogDialogButtonClickLisener = updateDialogDialogButtonClickLisener;
            return this;
        }


        public void creatButton() {
            layout.findViewById(R.id.update_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
//                    updateDialogDialogButtonClickLisener.click(dialog, 1);
                }
            });
            layout.findViewById(R.id.update_commit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateDialogDialogButtonClickLisener.click(dialog, 2);
                }
            });
        }

//        /**
//         * 创建单按钮对话框
//         * @return
//         */
//        public CustomDialog createSingleButtonDialog() {
//            showSingleButton();
//            layout.findViewById(R.id.singleButton).setOnClickListener(singleButtonClickListener);
//            //如果传入的按钮文字为空，则使用默认的“返回”
//            if (singleButtonText != null) {
//                ((Button) layout.findViewById(R.id.singleButton)).setText(singleButtonText);
//            } else {
//                ((Button) layout.findViewById(R.id.singleButton)).setText("返回");
//            }
//            create();
//            return dialog;
//        }

        /**
         * 创建双按钮对话框
         *
         * @return
         */
        public UpdateDialog createTwoButtonDialog() {
            create();
            return dialog;
        }

        public UpdateDialog.Builder hiddenCancel(){
            layout.findViewById(R.id.update_cancel).setVisibility(View.GONE);
            return this;
        }
        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        private void create() {

            if (message != null) {      //设置提示内容
                ((TextView) layout.findViewById(R.id.update_content)).setText(message);
            }

            if (contentView != null) {       //如果使用Builder的setContentview()方法传入了布局，则使用传入的布局
                ((LinearLayout) layout.findViewById(R.id.update_content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.update_content))
                        .addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            if (title != null) {
                ((TextView) layout.findViewById(R.id.update_version_text)).setText(title);
            }
            dialog.setContentView(layout);
            dialog.setCancelable(false);     //用户可以点击手机Back键取消对话框显示
            dialog.setCanceledOnTouchOutside(false);        //用户不能通过点击对话框之外的地方取消对话框显示
        }

    }

    public interface UpdateDialogDialogButtonClickLisener {
        void click(UpdateDialog dialog, int which);
    }
}