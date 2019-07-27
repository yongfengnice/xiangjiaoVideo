package com.baby.app.splash;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.BaseApplication;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.BuildConfig;
import com.baby.app.advertise.AdvertiseActivity;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.home.MainActivity;
import com.baby.app.service.DownInfoModel;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppWakeUpAdapter;
import com.fm.openinstall.model.AppData;


/**
 * Created by yongqianggeng on 2017/11/15.
 *
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class WelComeActivity extends IBaseActivity implements SplashView {
    public static int REQUEST_CODE = 99;

    private android.os.Handler handler = new android.os.Handler();
    private SplashPresener mSplashPresener;

    private void jumpToMain(){
        DownInfoModel model = new DownInfoModel();
        model.updateStatus();
        if (BuildConfig.SERVER_DEGUB) {
            Intent intent = new Intent(WelComeActivity.this,
                    MainActivity.class);
            startActivity(intent);
        } else {
            if (UserStorage.getInstance().getUser()!=null) {
                if (UserStorage.getInstance().getUser().getBannerList()!=null && UserStorage.getInstance().getUser().getBannerList().size() > 0) {
                    Intent intent = new Intent(WelComeActivity.this,
                            AdvertiseActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(WelComeActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                }
            }
        }
        finish();
    }





    //TODO:获取权限(待定)
    private void fetchPermisson(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {



    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        setToolBarVisible(View.GONE);
    }

    @Override
    public void initUiAndListener() {
        BaseApplication.getInstance().initAppconfig();
        //TODO:防止重复创建的问题，第一次安装完成启动，和home键退出点击launcher icon启动会重复
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        mSplashPresener = new SplashPresener(this);
        mSplashPresener.fetchDeviceInfo();
    }

    @Override
    public void fetchDeviceInfo(LoginBean loginBean) {
        if (UserStorage.getInstance().isLogin()) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jumpToMain();
                }
            }, Constants.SPLASH_TIME);

        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jumpToLogin(true);
                    finish();
                }
            }, Constants.SPLASH_TIME);
        }
    }

    @Override
    public void faied() {
        if (UserStorage.getInstance().isLogin()) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jumpToMain();

                }
            }, Constants.SPLASH_TIME);

        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jumpToLogin(true);
                    finish();
                }
            }, Constants.SPLASH_TIME);
        }
    }
}
