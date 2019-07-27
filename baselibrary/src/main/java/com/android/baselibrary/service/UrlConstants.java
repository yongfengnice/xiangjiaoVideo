package com.android.baselibrary.service;

/**
 * Created by yongqianggeng on 2018/6/17.
 */

public class UrlConstants {


    /*********************
     * begain 请求返回的code
     ***********************/
    public static final int SUCCESS_CODE = 200;
    public static final String REG_CODE = "1";
    //token 失效
    public static final int REQUEST_OUTDATA = 801;

    /**
     * 请求时长 15 秒
     * **/
    public static final int CONNECT_TIMEOUT = 20;

    /*****
     * base url
     **************/
//    private static String BASE_URL = "http://xg.xag55.com";
    private static String BASE_URL = "http://manager.vdpromotional.com:8080";
    public static final String NEW_BASE_URL = BASE_URL + "/";
    public static final String DIAMOND_URL = "http://www.baidu.com";
    public static final String FEED_BAKK_URL = "http://www.baidu.com";
}
