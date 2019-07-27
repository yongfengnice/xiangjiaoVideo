package com.baby.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.baby.app.R;

/**
 * Created by yongqianggeng on 2018/10/1.
 * 等级弹窗
 */

public class LevelDialog extends Dialog {

    private ImageView imageView;

    public LevelDialog(@NonNull Context context) {
        super(context);
    }

    public LevelDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

    }

    public static class Builder {

        private int imgSource;
        private View layout;
        private LevelDialog dialog;
        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            dialog = new LevelDialog(context, com.android.baselibrary.R.style.MyDialog);
            dialog.setCancelable(false);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.level_dialog_layout, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        public int getImgSource() {
            return imgSource;
        }

        public Builder setImgSource(int imgSource) {
            this.imgSource = imgSource;
            return this;
        }

        public void setDialog(LevelDialog dialog) {
            this.dialog = dialog;
        }

        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        public LevelDialog create() {

            ((ImageView) layout.findViewById(R.id.level_img_view)).setImageResource(imgSource);

            layout.findViewById(R.id.level_img_view).setOnClickListener(new View.OnClickListener() {
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



}
