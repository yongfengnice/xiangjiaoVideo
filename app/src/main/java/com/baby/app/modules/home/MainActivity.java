package com.baby.app.modules.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.baselibrary.AppCommon;
import com.android.baselibrary.base.ActivityManager;
import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.usermanger.LogoutEvent;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.util.EventBusUtil;
import com.android.baselibrary.util.SPUtils;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.Time;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.channel.ChannelFragment;
import com.baby.app.modules.find.FindFragment;
import com.baby.app.modules.mine.DownEvent;
import com.baby.app.modules.mine.MineFragment;
import com.baby.app.modules.mine.page.PromoteActivity;
import com.baby.app.service.AnalysisComplete;
import com.baby.app.service.DownInfoModel;
import com.baby.app.service.DownLoadProgress;
import com.baby.app.service.DownLoadServer;
import com.baby.app.service.IDownLoadServer;
import com.baby.app.update.MultipleDownload;
import com.baby.app.util.Utils;
import com.baby.app.widget.MyDialogUtil;
import com.baby.app.widget.dialog.CommonDialog;
import com.baby.app.widget.dialog.PayDialog;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppWakeUpAdapter;
import com.fm.openinstall.model.AppData;

import org.simple.eventbus.Subscriber;

import java.util.List;

import hdl.com.lib.runtimepermissions.HPermissions;
import hdl.com.lib.runtimepermissions.PermissionsResultAction;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class MainActivity extends IBaseActivity implements View.OnClickListener {

    private long mExitTime;
    public int currSel = 0;// 当前标记
    private long firstClickTime = 0;
    private long secondClickTime = 0;

    private LinearLayout tab_view_1;
    private LinearLayout tab_view_2;
    private LinearLayout tab_view_3;
    private LinearLayout tab_view_4;

    private LinearLayout mainFooterView;

    private ImageView image_1;
    private ImageView image_2;
    private ImageView image_3;
    private ImageView image_4;

    private TextView text_1;
    private TextView text_2;
    private TextView text_3;
    private TextView text_4;
    private IDownLoadServer iDownLoadServer;
    //首页
    private HomeFragment mHomeFragment;
    //频道
    private ChannelFragment mChannelFragment;
    //发现
    private FindFragment mFindFragment;
    //我的
    private MineFragment mMineFragment;

    private FragmentTransaction mTransaction;

    private MultipleDownload updateManager;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {

        OpenInstall.getWakeUp(getIntent(), wakeUpAdapter);
        switch (clicked) {
            case LEFT:
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    //TODO:如果在通话中双击退出的话
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                    }
                    mExitTime = System.currentTimeMillis();

                } else {
                    ActivityManager.getAppManager().finishAllActivity();
                    //释放内存
                    /*Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.startActivity(intent);
                    System.exit(0);*/
                }

                break;
            case MIDDLE:
                break;
            case RIGHT:
                break;
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        setToolBarVisible(View.GONE);
        Intent intent = new Intent(this, DownLoadServer.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        requestPermission();
    }

    AppWakeUpAdapter wakeUpAdapter = new AppWakeUpAdapter() {
        @Override
        public void onWakeUp(AppData appData) {
            //获取渠道数据
            String channelCode = appData.getChannel();
            //获取绑定数据
            String bindData = appData.getData();
            Log.d("OpenInstall", "getWakeUp : wakeupData = " + appData.toString());
        }
    };


    private void requestPermission() {
        /*
         * 请求所有必要的权限----
         */
        HPermissions.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(String permission) {

            }
        });
    }
    @Override
    public void initUiAndListener() {

    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                iDownLoadServer = IDownLoadServer.Stub.asInterface(iBinder);
                iDownLoadServer.setAnalysis(analysisComplete);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    AnalysisComplete analysisComplete = new AnalysisComplete() {

        @Override
        public void analysis(String url) throws RemoteException {
            hideDialogLoading();
            EventBusUtil.postEventByEventBus(new DownEvent(),DownEvent.TAG);
        }
        @Override
        public IBinder asBinder() {
            return null;
        }
    };

    @Override
    protected boolean useEventBus() {
        return true;
    }

    /**
     * 开始下载
     *
     * @param url
     */

    public void startDownLoad(String url,String name,String cover,String id) {
        final DownInfoModel model = new DownInfoModel();
        if(model.getNoCachBean()!=null &&model.getNoCachBean().size() > 0){
            showToast("其他视频正在下载");
            return;
        }
        try {
            if (null != iDownLoadServer) {
                showDialogLoading("正在解析");
                iDownLoadServer.start(url,name,cover,id);
            }
        } catch (RemoteException e) {
            e.printStackTrace();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Time.check()) {
            System.exit(0);
        }
    }

    @Override
    public void initUiAndListener(Bundle savedInstanceState) {

        tab_view_1 = (LinearLayout) findViewById(R.id.bottom_view_1);
        tab_view_2 = (LinearLayout) findViewById(R.id.bottom_view_2);
        tab_view_3 = (LinearLayout) findViewById(R.id.bottom_view_3);
        tab_view_4 = (LinearLayout) findViewById(R.id.bottom_view_4);
        image_1 = (ImageView) findViewById(R.id.image_1);
        image_2 = (ImageView) findViewById(R.id.image_2);
        image_3 = (ImageView) findViewById(R.id.image_3);
        image_4 = (ImageView) findViewById(R.id.image_4);

        text_1 = (TextView) findViewById(R.id.text_1);
        text_2 = (TextView) findViewById(R.id.text_2);
        text_3 = (TextView) findViewById(R.id.text_3);
        text_4 = (TextView) findViewById(R.id.text_4);

        tab_view_1.setOnClickListener(this);
        tab_view_2.setOnClickListener(this);
        tab_view_3.setOnClickListener(this);
        tab_view_4.setOnClickListener(this);
        initFragment(savedInstanceState);
        bottomTabInt(savedInstanceState);
        checkUpdate();

//
//        //TODO:公告弹窗

//

        if (UserStorage.getInstance().getUser() != null && UserStorage.getInstance().getUser().getNoticeList() != null) {
            List<LoginBean.Notice> noticeList = UserStorage.getInstance().getUser().getNoticeList();
            for (int i = 0; i < noticeList.size(); i++) {
                LoginBean.Notice notice = noticeList.get(i);
                MyDialogUtil.showAnnounceMentDialog(this, notice.getNoticleTitle(), notice.getNoticContent());
            }
        }

    }

    //初始化底部高亮
    private void bottomTabInt(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (SPUtils.get(Constants.CURRENT_SEL, "") != null) {
                String currentSel = (String) SPUtils.get(Constants.CURRENT_SEL, "");
                changeRadioButtonStatu(Integer.parseInt(currentSel));
            } else {
                changeRadioButtonStatu(currSel);
            }
        } else {
            changeRadioButtonStatu(currSel);
        }
    }

    // 根据点击改变菜单状态
    private void changeRadioButtonStatu(int currSel) {
        switch (currSel) {
            case 0:
                image_1.setBackgroundResource(R.mipmap.p_main);
                text_1.setTextColor(this.getResources().getColor(R.color.footer_text_color_selected));
                image_2.setBackgroundResource(R.mipmap.n_channel);
                text_2.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                image_3.setBackgroundResource(R.mipmap.n_discover);
                text_3.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                image_4.setBackgroundResource(R.mipmap.main_bar_center_nopress);
                text_4.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));

                break;
            case 1:
                image_1.setBackgroundResource(R.mipmap.n_main);
                text_1.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                image_2.setBackgroundResource(R.mipmap.p_channel);
                text_2.setTextColor(this.getResources().getColor(R.color.footer_text_color_selected));
                image_3.setBackgroundResource(R.mipmap.n_discover);
                text_3.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                image_4.setBackgroundResource(R.mipmap.main_bar_center_nopress);
                text_4.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                break;
            case 2:
                image_1.setBackgroundResource(R.mipmap.n_main);
                text_1.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                image_2.setBackgroundResource(R.mipmap.n_channel);
                text_2.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                image_3.setBackgroundResource(R.mipmap.p_discover);
                text_3.setTextColor(this.getResources().getColor(R.color.footer_text_color_selected));
                image_4.setBackgroundResource(R.mipmap.main_bar_center_nopress);
                text_4.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                break;
            case 3:
                image_1.setBackgroundResource(R.mipmap.n_main);
                text_1.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                image_2.setBackgroundResource(R.mipmap.n_channel);
                text_2.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                image_3.setBackgroundResource(R.mipmap.n_discover);
                text_3.setTextColor(this.getResources().getColor(R.color.footer_text_color_normal));
                image_4.setBackgroundResource(R.mipmap.p_center);
                text_4.setTextColor(this.getResources().getColor(R.color.footer_text_color_selected));
                break;
        }
        //TODO:保存菜单状态到内存中，防止内存重启
        SPUtils.put(Constants.CURRENT_SEL, String.valueOf(currSel));
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState != null) {

            mHomeFragment = (HomeFragment) getSupportFragmentManager()
                    .findFragmentByTag(HomeFragment.TAG);
            mChannelFragment = (ChannelFragment) getSupportFragmentManager()
                    .findFragmentByTag(ChannelFragment.TAG);
            mFindFragment = (FindFragment) getSupportFragmentManager()
                    .findFragmentByTag(FindFragment.TAG);
            mMineFragment = (MineFragment) getSupportFragmentManager()
                    .findFragmentByTag(MineFragment.TAG);

        }
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
            transaction.add(R.id.fragment_container, mHomeFragment, HomeFragment.TAG);
        } else {
            transaction.show(mHomeFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bottom_view_1) {
            currSel = 0;
        } else if (view.getId() == R.id.bottom_view_2) {
            currSel = 1;
            mTitleBuilder.setMiddleTitleText("频道");
        } else if (view.getId() == R.id.bottom_view_3) {
            currSel = 2;
            mTitleBuilder.setMiddleTitleText("发现");
        } else if (view.getId() == R.id.bottom_view_4) {
            currSel = 3;
            mTitleBuilder.setMiddleTitleText("我的");
        }
        changeRadioButtonStatu(currSel);
        addFragmentToStack();
    }

    // 切换fragment
    private void addFragmentToStack() {
        mTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(mTransaction);
        switch (currSel) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    mTransaction.add(R.id.fragment_container, mHomeFragment, HomeFragment.TAG);
                } else {
                    mTransaction.show(mHomeFragment);
                }
                break;
            case 1:
                if (mChannelFragment == null) {
                    mChannelFragment = new ChannelFragment();
                    mTransaction.add(R.id.fragment_container, mChannelFragment, ChannelFragment.TAG);
                } else {
                    mTransaction.show(mChannelFragment);
                }
                break;
            case 2:
                if (mFindFragment == null) {
                    mFindFragment = new FindFragment();
                    mTransaction.add(R.id.fragment_container, mFindFragment, FindFragment.TAG);
                } else {
                    mTransaction.show(mFindFragment);
                }
                break;
            case 3:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    mTransaction.add(R.id.fragment_container, mMineFragment, MineFragment.TAG);
                } else {
                    mTransaction.show(mMineFragment);
                }
                break;
            default:
                break;
        }
        mTransaction.commitAllowingStateLoss();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }

        if (mChannelFragment != null) {
            transaction.hide(mChannelFragment);
        }

        if (mFindFragment != null) {
            transaction.hide(mFindFragment);
        }

        if (mMineFragment != null) {
            transaction.hide(mMineFragment);
        }
    }

    public void checkUpdate() {
        Activity activity = ActivityManager.getAppManager().currentActivity();
        if (activity == null) {
            activity = MainActivity.this;
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

    @Override
    protected void onDestroy() {
        try {
            iDownLoadServer.setAnalysis(null);
            analysisComplete = null;
            unbindService(conn);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.onDestroy();

        wakeUpAdapter=null;
    }
}
