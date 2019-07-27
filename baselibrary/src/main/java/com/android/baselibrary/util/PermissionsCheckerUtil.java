package com.android.baselibrary.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by gyq on 2016/10/27.
 */

public class PermissionsCheckerUtil {

    // 判断权限集合
    public static boolean lacksPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(context,permission)) {
                continue;
            }else{
                return false;
            }
        }
        return true;
    }

    // 判断是否缺少权限
    public static  boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
