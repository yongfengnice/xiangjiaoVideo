package com.android.baselibrary.base;

import android.os.Environment;

import com.android.baselibrary.BuildConfigUtil;


/**
 * Created by gyq on 2016/7/5.
 */
public class Constants {

    //首页菜单状态
    public static final String CURRENT_SEL   = "CURRENTSEL";

    //微信事件
    public static final String WX_PAY   = "PAY";
    public static final String WX_BIND  = "BIND";
    public static final String WX_LOGIN = "LOGIN";

//    /**
//    * 微信key
//    * */
//    public static final String WECHAT_APPID = "wxa9812582f4d84525";
//    public static final String WEI_STATE = "sleepApp_wx_login";
//    public static final String WEI_SECRET = "a55ff5a51bc6f1b89e45bde477b96671";

    /***
     * SDCard路径
     *****/
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    /***
     * app name
     *****/
    public static String APP_NAME = "mvApp";

    /***
     * update目录
     *****/
    public static String DIR_APP_UPDATE = "update";

    /***
     * temp目录
     *****/
    public static String DIR_APP_TEMP = "temp";

    /***
     * 照片选择/照相回来的结果
     *****/
    public static final int REQUEST_IMAGE = 0x000002;

    /***
     * 照片选择/照相回来的结果
     *****/
    public static final int TAKE_PICTURE = 0x000001;


    /***********
     * 开启其他Activity的key
     **************/
    public static final String KEY_INTENT_ACTIVITY = "KEY_INTENT_ACTIVITY";
    public static final String KEY_TYPE_ACTIVITY = "KEY_TYPE_ACTIVITY";

    /**********
     * 请求相机/相册
     **********/
    //请求相机
    public static final int REQUEST_CAPTURE = 100;
    //请求相册
    public static final int REQUEST_PICK = 101;
    //请求截图
    public static final int REQUEST_CROP_PHOTO = 102;

    /*************
     * 是dialogloading还是pageLoading
     ****************/
    public static final String DIALOG_LOADING = "DIALOG_LOADING";
    public static final String PAGE_LOADING = "PAGE_LOADING";


    /*************
     * 登录的个人信息 标记
     ****************/
    public static final String SP_LOGIN_OBJECT = "sp_login_object";

    /*************
     * 用户的个人信息 标记
     ****************/
    public static final String SP_USER_OBJECT = "sp_user_object";


    /*************
     * SP name 标记
     ****************/
    public static final String SP_NAME = " mvApp_share_data";


    /*************
     * 图片存储路径
     ****************/
    public static final String BASE_PATH = SD_PATH + "/mv/temp/";

    /*************
     * 缓存图片路径
     ****************/
    public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";


    public static final String SYSTEM_IMAGE_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/老湿在线/";

//    /*************
//     * 小米推送
//     ****************/
//    public static final String XIAOMI_APPID = "2882303761517729702";
//    public static final String XIAOMI_APPKEY = "5171772961702";
//    public static final String XIAOMI_APPSECRET = "mGKwNAnhg6w9Kc/Z5SPirg==";
//    /*************
//     * 融云appkey
//     ****************/
//    public static final String RONG_YUN_APPKEY = "n19jmcy5ndz89";
//    /*************
//     * 声网appid
//     ****************/
//    public static final String AGORA_APPID = "95ac2bb338e54961bde95fb10f554776";
//    /*************
//     * 权限
//     ****************/
//    public static final String CAMERA_PERMISSION = "CAMERA_PERMISSION";
//    public static final String PLAY_PERMISSION = "PLAY_PERMISSION";
//    public static final String RECORD_PERMISSION = "RECORD_PERMISSION";
//    public static final String READ_STORE = "READ_STORE";

    /*************
     * 首次登录
     ****************/
    //第一次打开app
    public static final String FIRST_TIME = "FIRST_TIME";
    //第一次登录
    public static final String FIRST_LOGIN = "FIRST_LOGIN";

    /*************
     * 进入app时间
     ****************/
    public static final long SPLASH_TIME = 900;

    public static final  String FROM_CODE = "android";

}
