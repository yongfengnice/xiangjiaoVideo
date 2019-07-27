package com.baby.app.permisson;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import com.android.baselibrary.AppCommon;
import com.android.baselibrary.JumpPermissionManagement;
import com.android.baselibrary.PermissionUtils;
import com.android.baselibrary.widget.toast.ToastUtil;
/**
 * Created by yongqianggeng on 2018/4/14.
 */
/*
* 针对音视频的权限控制
* */
public class CallPermissonManager {

    private static final int REQUEST_CODE_PERMISSIONS = 2;

    private static final String[] VIDEO_ALL_CALL_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final String[] VIDEO_CALL_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    private static final String[] AUDIO_CALL_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /*只针对音频*/
    public static void checkAudioPermisson(final Context mContext, final CallPermissonCallBack callPermissonCallBack){

        //判断权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.checkMorePermissions(mContext,AUDIO_CALL_PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                @Override
                public void onHasPermission() {// 用户已授予权限
                    if (PermissionUtils.checkRecorderPermission(mContext)) {
                        if (callPermissonCallBack != null) {
                            callPermissonCallBack.hasPermisson();
                        }
                    }
                }
                @Override
                public void onUserHasAlreadyTurnedDown(String... permission) { //已拒绝
                    PermissionUtils.requestMorePermissions(mContext,AUDIO_CALL_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    if (callPermissonCallBack != null) {
                        callPermissonCallBack.hasNoPermisson();
                    }
                }

                @Override
                public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) { //用户之前已拒绝并勾选了不在询问、用户第一次申请权限
                    PermissionUtils.requestMorePermissions(mContext, AUDIO_CALL_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    try {

                        AppCommon.showDialog(mContext, "提示", "是否打开录音权限", "确定", "取消", new DialogInterface.OnClickListener() { //设置确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (which == 1) {
                                    JumpPermissionManagement.GoToSetting((Activity) mContext);
                                }

                            }
                        });

                    } catch (Exception e){
                        ToastUtil.showToast("请打开录音权限");
                    }
                }
            });
        } else {
            if (PermissionUtils.checkRecorderPermission(mContext)) {
                if (callPermissonCallBack != null) {
                    callPermissonCallBack.hasPermisson();
                }
            } else {
                try {
                    AppCommon.showDialog(mContext, "提示", "是否打开录音权限", "确定", "取消", new DialogInterface.OnClickListener() { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (which == 1) {
                                JumpPermissionManagement.GoToSetting((Activity) mContext);
                            }

                        }
                    });

                } catch (Exception e){

                }
            }
        }
    }

    /*只针对视频*/
    public static void checkVideoPermisson(final Context mContext, final CallPermissonCallBack callPermissonCallBack){

        //判断权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.checkMorePermissions(mContext,VIDEO_ALL_CALL_PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                @Override
                public void onHasPermission() {// 用户已授予权限
                    if (PermissionUtils.checkRecorderPermission(mContext) && PermissionUtils.cameraIsCanUse()) {
                        if (callPermissonCallBack != null) {
                            callPermissonCallBack.hasPermisson();
                        }
                    }
                }
                @Override
                public void onUserHasAlreadyTurnedDown(String... permission) { //已拒绝
                    PermissionUtils.requestMorePermissions(mContext,VIDEO_ALL_CALL_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    if (callPermissonCallBack != null) {
                        callPermissonCallBack.hasNoPermisson();
                    }
                }

                @Override
                public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) { //用户之前已拒绝并勾选了不在询问、用户第一次申请权限
                    PermissionUtils.requestMorePermissions(mContext, VIDEO_ALL_CALL_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    try {
                        AppCommon.showDialog(mContext, "提示", "是否打开录音摄像头权限", "确定", "取消", new DialogInterface.OnClickListener() { //设置确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (which == 1) {
                                    JumpPermissionManagement.GoToSetting((Activity) mContext);
                                }

                            }
                        });

                    } catch (Exception e){
                        ToastUtil.showToast("请打开录音和摄像头权限");
                    }
                }
            });
        } else {
            if (PermissionUtils.checkRecorderPermission(mContext) && PermissionUtils.cameraIsCanUse()) {
                if (callPermissonCallBack != null) {
                    callPermissonCallBack.hasPermisson();
                }
            } else {
                try {
                    AppCommon.showDialog(mContext, "提示", "是否打开录音摄像头权限", "确定", "取消", new DialogInterface.OnClickListener() { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (which == 1) {
                                JumpPermissionManagement.GoToSetting((Activity) mContext);
                            }

                        }
                    });

                } catch (Exception e){

                }
            }
        }
    }


    /*只针对音频*/
    public static void checkInAudioPermisson(final Context mContext, final CallPermissonCallBack callPermissonCallBack){

        //判断权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.checkMorePermissions(mContext,AUDIO_CALL_PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                @Override
                public void onHasPermission() {// 用户已授予权限
                    if (PermissionUtils.checkRecorderPermission(mContext)) {
                        if (callPermissonCallBack != null) {
                            callPermissonCallBack.hasPermisson();
                        }
                    }
                }
                @Override
                public void onUserHasAlreadyTurnedDown(String... permission) { //已拒绝
                    PermissionUtils.requestMorePermissions(mContext,AUDIO_CALL_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    if (callPermissonCallBack != null) {
                        callPermissonCallBack.hasNoPermisson();
                    }
                }

                @Override
                public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) { //用户之前已拒绝并勾选了不在询问、用户第一次申请权限
                    PermissionUtils.requestMorePermissions(mContext, AUDIO_CALL_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    if (callPermissonCallBack != null) {
                        callPermissonCallBack.hasNoPermisson();
                    }
                }
            });
        } else {
            if (PermissionUtils.checkRecorderPermission(mContext)) {
                if (callPermissonCallBack != null) {
                    callPermissonCallBack.hasPermisson();
                }
            } else {
                if (callPermissonCallBack != null) {
                    callPermissonCallBack.hasNoPermisson();
                }
            }
        }
    }


    /*只针对视频*/
    public static void checkInVideoPermisson(final Context mContext, final CallPermissonCallBack callPermissonCallBack){

        //判断权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.checkMorePermissions(mContext,VIDEO_CALL_PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                @Override
                public void onHasPermission() {// 用户已授予权限
                    if (PermissionUtils.cameraIsCanUse()) {
                        if (callPermissonCallBack != null) {
                            callPermissonCallBack.hasPermisson();
                        }
                    }
                }
                @Override
                public void onUserHasAlreadyTurnedDown(String... permission) { //已拒绝
                    PermissionUtils.requestMorePermissions(mContext,VIDEO_CALL_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    if (callPermissonCallBack != null) {
                        callPermissonCallBack.hasNoPermisson();
                    }
                }

                @Override
                public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) { //用户之前已拒绝并勾选了不在询问、用户第一次申请权限
                    PermissionUtils.requestMorePermissions(mContext, VIDEO_CALL_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    if (callPermissonCallBack != null) {
                        callPermissonCallBack.hasNoPermisson();
                    }
                }
            });
        } else {
            if (PermissionUtils.cameraIsCanUse()) {
                if (callPermissonCallBack != null) {
                    callPermissonCallBack.hasPermisson();
                }
            } else {
                if (callPermissonCallBack != null) {
                    callPermissonCallBack.hasNoPermisson();
                }
            }
        }
    }


}
