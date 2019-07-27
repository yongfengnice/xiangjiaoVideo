package com.baby.app.widget;

import android.content.Context;

import com.android.baselibrary.service.bean.mine.PayBean;
import com.baby.app.widget.dialog.AnnouncementDialog;
import com.baby.app.widget.dialog.CommonDialog;
import com.baby.app.widget.dialog.DimandDialog;
import com.baby.app.widget.dialog.LevelDialog;
import com.baby.app.widget.dialog.PayDialog;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/1.
 *
 */

public class MyDialogUtil {

    /**
    * 弹出公告
    * */
    public static void showAnnounceMentDialog(Context mContext,String title, String message){
        AnnouncementDialog.Builder builder = new AnnouncementDialog.Builder(mContext);
        final AnnouncementDialog mDialog = builder.setTitle(title)
                .setMessage(message).create();
        mDialog.show();
    }

    /**
     * 弹出等级
     * */
    public static void showLevelDialog(Context mContext, int imgSource){
        LevelDialog.Builder builder = new LevelDialog.Builder(mContext);
        final LevelDialog mDialog = builder.setImgSource(imgSource).create();
        mDialog.show();
    }

    /**
     * 普通弹窗
     * */
    public static void showCommonDialogDialog(Context mContext, String message, CommonDialog.CommonDialogLisenter commonDialogLisenter){
        CommonDialog.Builder builder = new CommonDialog.Builder(mContext);
        final CommonDialog mDialog = builder
                .setMessage(message).setCommonDialogLisenter(commonDialogLisenter).create();
        mDialog.show();
    }

    /**
     * 支付弹窗
     * */
    public static void showPayDialogDialog(Context mContext, List<PayBean.Data>dataList, PayDialog.PayDialogDialogLisenter payDialogDialogLisenter){
        PayDialog.Builder builder = new PayDialog.Builder(mContext);
        final PayDialog mDialog = builder
                .setPayBeanList(dataList).setPayDialogDialogLisenter(payDialogDialogLisenter).create();
        mDialog.show();
    }

    /**
     * 支付钻石弹窗
     * */
    public static void showDimandDialog(Context mContext, String message, DimandDialog.DimandDialogLisenter dimandDialogLisenter){
        DimandDialog.Builder builder = new DimandDialog.Builder(mContext);
        final DimandDialog mDialog = builder
                .setMessage(message).setDimandDialogLisenter(dimandDialogLisenter).create();
        mDialog.show();
    }

}
