package com.baby.app.modules.mine.page;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.AppCommon;
import com.android.baselibrary.base.ActivityManager;
import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.BaseApplication;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.usermanger.LogoutEvent;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.util.FileUtils;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.home.MainActivity;
import com.baby.app.update.MultipleDownload;
import com.baby.app.widget.cache.CleanMessageUtil;
import com.baby.app.widget.cache.FileUtil;
import com.baby.app.widget.cache.MethodsCompat;

import org.simple.eventbus.Subscriber;

import java.io.File;

import butterknife.BindView;

/**
 * Created by yongqianggeng on 2018/9/22.
 * 设置
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class SettingActivity extends IBaseActivity {

    @BindView(R.id.setting_item1)
    RelativeLayout settingItem1;

    @BindView(R.id.setting_item2)
    RelativeLayout settingItem2;

    @BindView(R.id.setting_item4)
    RelativeLayout settingItem4;
    @BindView(R.id.cache_text_view)
    TextView cache_text_view;

    @BindView(R.id.version_name)
    TextView versionNameView;

    @BindView(R.id.version_back)
    RelativeLayout versionBack;
    private boolean mIsUpdate;

    private MultipleDownload updateManager;

    @BindView(R.id.new_verison_view)
    TextView new_verison_view;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                finish();
                break;
            case MIDDLE:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("设置")
                .setLeftDrawable(R.mipmap.ic_back_brown);
    }

    @Override
    public void initUiAndListener() {
        //账户管理
        settingItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserStorage.getInstance().isLogin() && UserStorage.getInstance().getUserType() == UserType.MARK_USER) {
                    openActivity(AcountActivity.class);
                } else {
                    jumpToLogin();
                }
            }
        });

        settingItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCommon.showDialog(mContext, "提示", "是否要清除缓存？", "确定", "取消", new DialogInterface.OnClickListener() { //设置确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        CleanMessageUtil.clearAllCache(SettingActivity.this);
                        cache_text_view.setText("0KB");
                        ToastUtil.showToast("清除成功");
                    }
                });



            }
        });

        settingItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ProtocolActivity.class);
            }
        });

        try {
          cache_text_view.setText(CleanMessageUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (UserStorage.getInstance().getVersionCode()!=null) {
            new_verison_view.setText("最新版本"+UserStorage.getInstance().getVersionCode());
            //转换为数组
            String currentVersion = BaseApplication.getInstance().getAppVersionName();
            String newVersion = UserStorage.getInstance().getVersionCode();
            String[]currentVs = currentVersion.split("\\.");
            String[]newVs = newVersion.split("\\.");
            Boolean isUpdate = false;
            if (currentVersion.equals(newVersion)) {
                isUpdate = false;
            } else {
                if (currentVs.length < newVs.length) {
                    Boolean isFlag = false; //判断当前版本中是否存在大于最新版本的字段
                    for (int i=0;i<currentVs.length;i++) {
                        int curentVer = Integer.parseInt(currentVs[i]);
                        int newVer = Integer.parseInt(newVs[i]);
                        if (curentVer < newVer) {
                            isUpdate = true;
                            isFlag = false;
                            break;
                        } else if (curentVer == newVer) {
                            continue;
                        } else {
                            isFlag = true;
                        }
                    }
                    if (!isFlag) {
                        isUpdate = true;
                    }


                } else {
                    Boolean isFlag = false; //判断最新版本中是否存在大于当前版本的字段
                    for (int i=0;i<newVs.length;i++) {
                        int curentVer = Integer.parseInt(currentVs[i]);
                        int newVer = Integer.parseInt(newVs[i]);
                        if (newVer < curentVer) {
                            isFlag = false;
                            break;
                        } else if (curentVer == newVer) {
                            continue;
                        } else {
                            isUpdate = true;
                            isFlag = true;
                        }
                    }
                    if (isFlag) {
                        isUpdate = true;
                    }
                }
            }
            mIsUpdate = isUpdate;
            if (isUpdate) {
                versionNameView.setText("更新");
            } else {
                versionNameView.setText("最新");
            }

        } else {
            versionNameView.setText(BaseApplication.getInstance().getAppVersionName());
        }

        versionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsUpdate) {
                    checkUpdate();
                } else {
                    ToastUtil.showToast("已是最新版本");
                }
            }
        });
//

    }

    public void checkUpdate() {
        Activity activity = ActivityManager.getAppManager().currentActivity();
        if (activity == null) {
            activity = SettingActivity.this;
        }
        if (updateManager == null) {
            synchronized (MainActivity.class) {
                if (updateManager == null) {
                    updateManager = new MultipleDownload(activity);
                    updateManager.checkedVersion();
                }
            }
        } else {
            if (!updateManager.isShowDialog()) {
                updateManager = new MultipleDownload(activity);
                updateManager.checkedVersion();
            }
        }
    }

    /**
     * 退出登录跳转到登录页面
     */
    @Subscriber(tag = LogoutEvent.TAG)
    public void jumpToLoginActivity(LogoutEvent event) {
        finish();
    }



}
