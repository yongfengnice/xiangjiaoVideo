package com.android.baselibrary.thirdpart;

/**
 * Created by gyq on 2016/8/25.
 */
public class PlatformConfig {


    private static String sQQAppId;
    private static String sQQAppKey;

    private static String sWeixinAppKey = "wxa9812582f4d84525";
    private static String sWeixinAppSecret = "a55ff5a51bc6f1b89e45bde477b96671";

    private static String sWeiboAppKey;
    private static String sWeiboAppSecret;
    private static String sWeiboRedirectUrl = "https://api.weibo.com/oauth2/default.html";


    public static String getQQAppId() {
        return sQQAppId;
    }

    public static String getQQAppKey() {
        return sQQAppKey;
    }

    public static String getWeiboAppKey() {
        return sWeiboAppKey;
    }

    public static String getWeiboAppSecret() {
        return sWeiboAppSecret;
    }

    public static String getWeiboRedirectUrl() {
        return sWeiboRedirectUrl;
    }

    public static String getWeixinAppKey() {
        return sWeixinAppKey;
    }

    public static String getWeixinAppSecret() {
        return sWeixinAppSecret;
    }

    public static  void setQQ(String appId, String appKey) {
        sQQAppId = appId;
        sQQAppKey = appKey;
    }

    public static void setWeixin(String appKey, String appSecret) {
        sWeixinAppKey = appKey;
        sWeixinAppSecret = appSecret;
    }

    public static void setWeibo(String appKey, String appSecret) {
        sWeiboAppKey = appKey;
        sWeiboAppSecret = appSecret;
    }
}
