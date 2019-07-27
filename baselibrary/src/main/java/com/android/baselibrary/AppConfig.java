package com.android.baselibrary;

/**
 * Created by gyq on 2017/6/21.
 */

public class AppConfig {

    public static void initDebug(String channelID, int versionCode, String versionName) {

        // Fields from build type: debug
        BuildConfigUtil.DEBUG = true;
        BuildConfigUtil.BUILD_ENVIRONMENT = "debug";
        BuildConfigUtil.VERSION_CODE = versionCode;
        BuildConfigUtil.VERSION_NAME = versionName;
    }

    public static void initEmulation(String channelID, int versionCode, String versionName) {
        BuildConfigUtil.DEBUG = true;
        // Fields from build type: debug
        BuildConfigUtil.BUILD_ENVIRONMENT = "emulation";
        BuildConfigUtil.VERSION_CODE = versionCode;
        BuildConfigUtil.VERSION_NAME = versionName;
    }

    public static void initRelease(String channelID, int versionCode, String versionName) {
        BuildConfigUtil.DEBUG = false;
        // Fields from build type: debug
        BuildConfigUtil.BUILD_ENVIRONMENT = "release";
        BuildConfigUtil.VERSION_CODE = versionCode;
        BuildConfigUtil.VERSION_NAME = versionName;
    }
}
