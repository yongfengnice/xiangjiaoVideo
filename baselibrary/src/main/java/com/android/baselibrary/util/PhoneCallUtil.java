package com.android.baselibrary.util;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.android.baselibrary.AppCommon;
import com.android.baselibrary.PermissionUtils;
import com.android.baselibrary.R;
import com.android.baselibrary.widget.toast.ToastUtil;

import java.lang.ref.SoftReference;

/**
 * Created by yongqianggeng on 2018/6/19.
 */

public class PhoneCallUtil {

    /**
     * 直接拨打电话
     *
     * @param context
     * @param number
     */
    public static void callPhone(final Context context, final String number) {
        final SoftReference<Context> w_context = new SoftReference<Context>(context);
        final Context softContext = w_context.get();
        if(softContext != null){
            if (StringUtils.isNotEmpty(number)) {
                if (PermissionUtils.checkPermission(softContext,Manifest.permission.CALL_PHONE)) {

                    AppCommon.showDialog(softContext, "", number, "呼叫", "取消", new DialogInterface.OnClickListener() { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + number));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            softContext.startActivity(intent);
                        }
                    }, new DialogInterface.OnClickListener() {//设置取消按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                } else {
                    ToastUtil.showToast("请打开拨打权限");
                }
            } else {
                ToastUtil.showToast("号码为空");
            }
        }
    }
}