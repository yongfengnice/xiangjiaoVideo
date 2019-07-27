package com.android.baselibrary.base;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import com.android.baselibrary.usermanger.UserStorage;

import com.android.baselibrary.util.DisplayUtil;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.fm.openinstall.OpenInstall;
import com.orhanobut.logger.Logger;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.umeng.analytics.MobclickAgent;

import org.simple.eventbus.EventBus;
import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by gyq on 2016/7/5.
 *
 */

public class BaseApplication extends MultiDexApplication {

    public Context mContext;

    private IWXAPI mWeChatApi;
    private DbManager db;
    private final String DB_NAME = "game.db";
    public DbManager getDb() {
        return db;
    }
    private static BaseApplication instance = null;

    /***********debug emulation  release*****************/

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

//
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        instance = this;
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(this, config);
        x.Ext.init(this);
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName(DB_NAME)
                .setDbVersion(1)
                .setAllowTransaction(true)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        //开启WAL, 对写入加速提升巨大(作者原话)
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });
        db = x.getDb(daoConfig);

        if(isMainProcess()){
            OpenInstall.init(this);
        }

    }




    public void initAppconfig() {
        // 初始化log
        Logger.init();
        try {
            initUmeng();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initDisplayOpinion();

//        mWeChatApi = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APPID, true);
//        mWeChatApi.registerApp(Constants.WECHAT_APPID);

        //初始化个人信息
        UserStorage.getInstance().initUserInfo();

//        new IShareConfig.Builder()
//                .setWeixin(Constants.WECHAT_APPID, Constants.WEI_SECRET)
//                .init();
        EventBus.getDefault().register(this);
    }


    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dp(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dp(getApplicationContext(), dm.heightPixels);
    }


    public Context getContext() {
        return mContext;
    }

    public IWXAPI getWeChatApi() {
        return mWeChatApi;
    }

    private void initUmeng() throws PackageManager.NameNotFoundException {
        synchronized(this){
            MobclickAgent.UMAnalyticsConfig umAnalyticsConfig;
            ApplicationInfo appInfo = this.getPackageManager().getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);

            umAnalyticsConfig  = new MobclickAgent.UMAnalyticsConfig(this, "5ba9d411f1f556acd2000187", appInfo.metaData.getString("UMENG_CHANNEL"), MobclickAgent.EScenarioType.E_UM_NORMAL);
            MobclickAgent.startWithConfigure(umAnalyticsConfig);
            MobclickAgent.setDebugMode(true);
        }
    }


    /**
     * 返回当前程序版本名
     */
    public String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        android.app.ActivityManager activityManager = (android.app.ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }


}
