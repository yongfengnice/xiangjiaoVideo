package com.baby.app.util;

import java.util.Locale;

/**
 * Created by 14254 on 2018/11/1.
 */

public class Utils {
    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    public static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fM", bytes / (1024.00 * 1024.00));
    }
}
