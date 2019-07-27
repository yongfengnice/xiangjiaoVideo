package com.android.baselibrary.thirdpart;

/**
 * 配置 ishare 第三方平台参数, 在 App 入口处配置
 * Created by pengji on 16-6-30.
 */
public final class IShareConfig {

    private static String sQQAppId;
    private static String sQQAppKey;

    private static String sWeixinAppKey;
    private static String sWeixinAppSecret;

    private static String sWeiboAppKey;
    private static String sWeiboAppSecret;
    private static String sWeiboRedirectUrl;

    private static void init(Builder builder) {
        sQQAppId = builder.mQQAppId;
        sQQAppKey = builder.mQQAppKey;

        sWeixinAppKey = builder.mWeixinAppKey;
        sWeixinAppSecret = builder.mWeixinAppSecret;

        sWeiboAppKey = builder.mWeiboAppKey;
        sWeiboAppSecret = builder.mWeiboAppSecret;
        sWeiboRedirectUrl = builder.mWeiboRedirectUrl;
    }

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

    public static final class Builder {
        private String mQQAppId;
        private String mQQAppKey;

        private String mWeixinAppKey;
        private String mWeixinAppSecret;

        private String mWeiboAppKey;
        private String mWeiboAppSecret;
        private String mWeiboRedirectUrl = "https://api.weibo.com/oauth2/default.html";

        public Builder setQQ(String appId, String appKey) {
            mQQAppId = appId;
            mQQAppKey = appKey;
            return this;
        }

        public Builder setWeixin(String appKey, String appSecret) {
            mWeixinAppKey = appKey;
            mWeixinAppSecret = appSecret;
            return this;
        }

        public Builder setWeibo(String appKey, String appSecret) {
            mWeiboAppKey = appKey;
            mWeiboAppSecret = appSecret;
            return this;
        }

        public Builder setWeiboRedirectUrl(String redirectUrl) {
            mWeiboRedirectUrl = redirectUrl;
            return this;
        }

        public void init() {
            IShareConfig.init(this);
        }
    }

}
