package com.android.baselibrary.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.baselibrary.R;
import com.android.baselibrary.base.ActivityManager;
import com.android.baselibrary.util.DisplayUtil;

/**
 * Created by gyq on 2016/8/3.
 */
public class DialogUtil {

    public static void showDialog(Context context, int layoutID){
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        View layout = inflaterDl.inflate(layoutID, null );

        //对话框
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = DisplayUtil.dp2px(context,250); // 宽度
        lp.height = DisplayUtil.dp2px(context,300); // 高度
        dialogWindow.setAttributes(lp);

        if(layout.findViewWithTag("cancle_button") != null){
            layout.findViewWithTag("cancle_button").setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    public static void showDialog(Context context, String text){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(text);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Activity activity = ActivityManager.getAppManager().currentActivity();
                /*if(!(activity instanceof MainActivity)){
                    activity.finish();
                }*/
            }
        });
        builder.show();
    }


    public static void showNormalDialog(Context context, String text){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage(text);
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Activity activity = ActivityManager.getAppManager().currentActivity();
                /*if(!(activity instanceof MainActivity)){
                    activity.finish();
                }*/
            }
        });
        builder.show();
    }

    public interface OnClickYesListener {
        abstract void onClickYes();
    }

    /**
     * 提问框的 Listener
     *
     */
    public interface OnClickNoListener {
        abstract void onClickNo();
    }


    public static void showDialog(Context context, String title,
                                  String text, final OnClickYesListener listenerYes,
                                  final OnClickNoListener listenerNo){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(text)
                .setPositiveButton(context.getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // 如果确定被电击
                                if (listenerYes != null) {
                                    listenerYes.onClickYes();
                                }
                            }
                        })
                .setNegativeButton(context.getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // 如果取消被点击
                                if (listenerNo != null) {
                                    listenerNo.onClickNo();
                                }
                            }
                        })
                .setCancelable(true);
        builder.create().show();

    }
}
