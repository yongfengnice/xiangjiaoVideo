package com.android.baselibrary.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.baselibrary.R;
import com.android.baselibrary.event.LoginEvent;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.switchlayout.BaseEffects;
import com.android.baselibrary.switchlayout.SwichLayoutInterFace;
import com.android.baselibrary.switchlayout.SwitchLayout;
import com.android.baselibrary.util.DeviceUtils;
import com.android.baselibrary.util.EventBusUtil;
import com.android.baselibrary.util.SPUtils;
import com.android.baselibrary.util.StatusbarColorUtils;
import com.android.baselibrary.util.StringUtils;
import com.android.baselibrary.util.SystemUtil;
import com.android.baselibrary.widget.DialogUtil;
import com.android.baselibrary.widget.TipTextView;
import com.android.baselibrary.widget.loading.VaryViewHelperController;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.example.com.statusbarutil.OSUtils;
import com.example.com.statusbarutil.StatusBarUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.umeng.analytics.MobclickAgent;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * Created by gyq on 2016/7/6.
 *
 */
public abstract class BaseActivity<T extends BasePresenter> extends SwipeBackActivity implements BaseView,
        SwichLayoutInterFace, TitleBuilder.TitleBuilderListener {

    public TitleBuilder mTitleBuilder;  //标题
    protected VaryViewHelperController mVaryViewHelperController; //网络加载控制器
    protected T mPresenter; //泛型 presenter

    private FrameLayout mTitle_container;
    /**
     * 侧滑布局
     */

    public Context mContext;

    private View rootView;

    private TipTextView mTipTextView;

    private YQApi annotation;

    private View titleShadow;

    private float mDonwX = 0;
    private float mDonwY = 0;

    private float mUpX = 0;
    private float mUpY = 0;

    private long mDonwTime = 0;
    private long mUpTime = 0;

    private final int REQUEST_CODE_CAMERA = 11;
    private SwipeBackLayout mSwipeBackLayout;

    //阴影
    public View getTitleShadow() {
        return titleShadow;
    }

    public void setTitleShadow(View titleShadow) {
        this.titleShadow = titleShadow;
    }

    protected boolean isFullscreen = false;//内容是否延伸到状态栏内：true:延伸到状态栏内

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (getClass().isAnnotationPresent(YQApi.class)) {

            ActivityManager.getAppManager().addActivity(this);
            initView();
//            setStatusBarColor(-1);
//            setStatusBar();

            initTitle();
            initUiAndListener();
            initUiAndListener(savedInstanceState);
            //com.example.aop.utils.App.setActivity(this);
            initSwipe(annotation);
            redirectToLogin(isRediectToLogin());
        } else {
            throw new RuntimeException("请配置YQApi.class");
        }
    }

    private void initSwipe(YQApi annotation) {
        if (annotation.swipeback()) {
            // 侧滑关闭
//        /*Logger.e("******initSwip*******is switch="+annotation.swipeback());
            mSwipeBackLayout = getSwipeBackLayout();
            mSwipeBackLayout.setScrimColor(Color.TRANSPARENT);
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
            mSwipeBackLayout.setEnabled(annotation.swipeback());
        }
    }

    public boolean getIsFullscreen() {
        return isFullscreen;
    }

    public void setStatusBarColor(int color){

        StatusBarUtil.setImmersiveStatusBar(this,false);
        if (color == -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarUtil.setStatusBarColor(this,Color.parseColor("#FFF4F4F4"));
            } else if (OSUtils.isMiui()) {
                StatusBarUtil.setStatusBarColor(this,Color.parseColor("#FFF4F4F4"));
            } else if (OSUtils.isFlyme()) {
                StatusBarUtil.setStatusBarColor(this,Color.parseColor("#FFF4F4F4"));
            } else {//其他情况下我们将状态栏设置为灰色，就不会看不见字体
                StatusBarUtil.setStatusBarColor(this,Color.LTGRAY);
            }
        } else {
            StatusBarUtil.setStatusBarColor(this,color);
        }
    }


    private void initView() {

        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        annotation = getClass().getAnnotation(YQApi.class);
        // 总布局
        int rootViewId = getLayoutView();
        setContentView(R.layout.activity_title_bar);


        titleShadow = findViewById(R.id.id_title_shadow);
        mTitle_container = (FrameLayout) findViewById(R.id.title_container);
        rootView = findViewById(R.id.base_rootView);
        statusTv = findViewById(R.id.status_tv);

        setContent(rootViewId);

        if (null != getChildPresenter()) {
            mPresenter = getChildPresenter();
        }
        if (getLoadingTargetView() != null) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }

        initAnimation(annotation);
    }


    private void initAnimation(YQApi annotation) {
        if (annotation.openAnimation() != -1) {
            setEnterSwichLayout(annotation.openAnimation());
        }
    }

    private void initTitle() {
        mTitleBuilder = new TitleBuilder(this);
        mTitleBuilder.initBar(rootView.findViewById(R.id.id_title_bar));
        mTitleBuilder.setTitleBuilderListener(this);
        initToolBar(mTitleBuilder);
    }

    public View getTitleView() {
        return rootView.findViewById(R.id.id_title_bar);
    }

    private void setContent(int resID) {

        View content_view = getLayoutInflater().inflate(resID, null);
        mTitle_container.addView(content_view);

        //TODO:添加绑定
        ButterKnife.bind(this,content_view);
    }

    /**
     * 设置title是否可见  默认是可见状态
     *
     * @param visible
     */
    public void setToolBarVisible(int visible) {
        findViewById(R.id.id_title_bar).setVisibility(visible);
        findViewById(R.id.title_line).setVisibility(visible);
    }

    public void setToolBarLineVisible(int visible) {
        findViewById(R.id.title_line).setVisibility(visible);
    }

    /**
     * 通过类名启动Activity add
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     */
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 通过类名启动Activity add
     */
    protected void openActivityForResult(Class<?> pClass, int requestCode) {
        openActivity(pClass, null, requestCode);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 通过类名启动Activity，并且含有string数据
     */
    protected void openActivity(Class<?> pClass, String type, String pBundle) {
        Intent intent = new Intent(this, pClass);
        if (StringUtils.isNotEmpty(pBundle)) {
            intent.putExtra(type, pBundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        StatusBarUtil.setImmersiveStatusBar(this,true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.cancelRequest();
        }
        ActivityManager.getAppManager().finishActivity(this);
        ToastUtil.dismissToast();
        if (annotation != null) {
            setExitSwichLayout(annotation.closAnimatione());
        }
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void showToast(String msg) {
//        DG_Toast.show(msg);
        ToastUtil.showToast(msg);
    }

    @Override
    public void showTopTip(String text, int res) {
        mTipTextView = (TipTextView) findViewById(R.id.ttv_read_more);
        mTipTextView.setText(text);
        Drawable nav_up = ContextCompat.getDrawable(mContext, res);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        mTipTextView.setCompoundDrawables(nav_up, null, null, null);
        mTipTextView.showTips();
    }

    private KProgressHUD hud;

    @Override
    public void showRequestOutData() {
        /*hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setCancellable(false);
        hud.show();*/
        DialogUtil.showDialog(mContext, "请求过期,请重新登录");
    }

    @Override
    public void showDialogLoading() {
        if (hasWindowFocus() && hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.setCancellable(false);
            hud.show();
        } else if (hud != null && !hud.isShowing() && hasWindowFocus()) {
            hud.show();
        }
    }

    @Override
    public void showDialogLoading(String msg) {
        if (hasWindowFocus() && hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.setCancellable(false);
            hud.setLabel(msg);
            hud.show();
        } else if (hud != null && !hud.isShowing() && hasWindowFocus()) {
            hud.show();
        }
    }

    @Override
    public void hideDialogLoading() {
        if (hud != null) {
            try {
                hud.dismiss();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        hud = null;
    }

    @Override
    public void hidePageLoading() {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//
//        }
//        mVaryViewHelperController.hideloading();
    }

    @Override
    public void showPageLoading() {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//        }
//        mVaryViewHelperController.showLoading();
    }

    @Override
    public void refreshView() {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//        }
//        mVaryViewHelperController.restore();
    }

    @Override
    public void showNetError() {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//        }
//        mVaryViewHelperController.showNetworkError(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onReloadClicked();
//            }
//        });
    }

    @Override
    public void showNetError(String msg, int icon) {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//        }
//        mVaryViewHelperController.showNetworkError(msg, icon, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //mPresenter.requestAgain();
//                onReloadClicked();
//            }
//        });
    }

    @Override
    public void gotoLogin() {

    }


    //是否未登录跳转到登录页面
    public boolean isRediectToLogin(){
        return true;
    }
    //跳转到登录页面
    private void redirectToLogin(boolean jump) {
//        if (jump) {
//            if (!UserStorage.getInstance().isLogin()) {
//                UserStorage.getInstance().logout(false);
//            }
//        }

    }



    public void showEmptyView(String msg, int icon) {
//        if (mVaryViewHelperController == null) {
//            throw new IllegalStateException("no ViewHelperController");
//        }
//        mVaryViewHelperController.showEmpty(msg, icon);
    }

//    protected ApplicationComponent getApplicationComponent() {
//        ApplicationComponent mApplicationComponent = BaseApplication.getInstance().getApplicationComponent();
//        if (BuildConfigUtil.BUILD_ENVIRONMENT.equals("debug")) {
//            mApplicationComponent =
//                    DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
//                            .build();
//        }
//        return mApplicationComponent;
//    }

    protected abstract int getLayoutView();

    protected abstract void onTitleClickListen(TitleBuilder.TitleButton clicked);

    /**
     * 获取子类presenter
     *
     * @return
     */
    protected T getChildPresenter() {
        return null;
    }

    /**
     * 获取loading/error/empty的目标页面
     *
     * @return
     */
    protected View getLoadingTargetView() {
        return null;
    }

//    /**
//     * 供子类初始化一些配置 (后期再研究)
//     *
//     * @return
//     */
//    public abstract void initInjector();

    /**
     * 子类设置标题
     *
     * @param mTitleBuilder
     */
    public abstract void initToolBar(TitleBuilder mTitleBuilder);

    /**
     * 子类设置ui
     *
     * @param
     */
    public abstract void initUiAndListener();

    public void initUiAndListener(Bundle savedInstanceState) {

    }

    /**
     * 失败后再次加载
     */
    public void onReloadClicked() {

    }

    /**
     * 获取rootView
     */

    public View getRootView() {
        return rootView;
    }

    @Override
    public void setEnterSwichLayout(int key) {
        switch (key) {
            case 0:
                SwitchLayout.get3DRotateFromLeft(this, false, null);
                break;
            case 1:
                SwitchLayout.getSlideFromBottom(this, false,
                        BaseEffects.getMoreSlowEffect());
                break;
            case 2:
                SwitchLayout.getSlideFromTop(this, false,
                        BaseEffects.getReScrollEffect());
                break;
            case 3:
                SwitchLayout.getSlideFromLeft(this, false,
                        BaseEffects.getLinearInterEffect());
                break;
            case 4:
                SwitchLayout.getSlideFromRight(this, false, null);
                break;
            case 5:
                SwitchLayout.getFadingIn(this);
                break;
            case 6:
                SwitchLayout.ScaleBig(this, false, null);
                break;
            case 7:
                SwitchLayout.FlipUpDown(this, false,
                        BaseEffects.getQuickToSlowEffect());
                break;
            case 8:
                SwitchLayout.ScaleBigLeftTop(this, false, null);
                break;
            case 9:
                SwitchLayout.getShakeMode(this, false, null, 10);
                break;
            case 10:
                SwitchLayout.RotateLeftCenterIn(this, false, null);
                break;
            case 11:
                SwitchLayout.RotateLeftTopIn(this, false, null);
                break;
            case 12:
                SwitchLayout.RotateCenterIn(this, false, null);
                break;
            case 13:
                SwitchLayout.ScaleToBigHorizontalIn(this, false, null);
                break;
            case 14:
                SwitchLayout.ScaleToBigVerticalIn(this, false, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void setExitSwichLayout(int key) {
        {
            switch (key) {
                case 0:
                    SwitchLayout.get3DRotateFromRight(this, true, null);
                    break;
                case 1:
                    SwitchLayout.getSlideToBottom(this, true,
                            BaseEffects.getMoreSlowEffect());
                    break;
                case 2:
                    SwitchLayout.getSlideToTop(this, true,
                            BaseEffects.getReScrollEffect());
                    break;
                case 3:
                    SwitchLayout.getSlideToLeft(this, true,
                            BaseEffects.getLinearInterEffect());
                    break;
                case 4:
                    SwitchLayout.getSlideToRight(this, true, null);
                    break;
                case 5:
                    SwitchLayout.getFadingOut(this, true);
                    break;
                case 6:
                    SwitchLayout.ScaleSmall(this, true, null);
                    break;
                case 7:
                    SwitchLayout.FlipUpDown(this, true,
                            BaseEffects.getQuickToSlowEffect());
                    break;
                case 8:
                    SwitchLayout.ScaleSmallLeftTop(this, true, null);
                    break;
                case 9:
                    SwitchLayout.getShakeMode(this, true, null, 10);
                    break;
                case 10:
                    SwitchLayout.RotateLeftCenterOut(this, true, null);
                    break;
                case 11:
                    SwitchLayout.RotateLeftTopOut(this, true, null);
                    break;
                case 12:
                    SwitchLayout.RotateCenterOut(this, true, null);
                    break;
                case 13:
                    SwitchLayout.ScaleToBigHorizontalOut(this, true, null);
                    break;
                case 14:
                    SwitchLayout.ScaleToBigVerticalOut(this, true, null);
                    break;
                default:
                    break;
            }

        }
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mDonwX = ev.getRawX();
//                mDonwY = ev.getRawY();
//                mDonwTime = System.currentTimeMillis();
//                break;
//            case MotionEvent.ACTION_UP:
//                mUpX = ev.getRawX();
//                mUpY = ev.getRawY();
//                mUpTime = System.currentTimeMillis();
//                break;
//        }
//
//        return super.dispatchTouchEvent(ev);
//    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 通过xml查找相应的ID，通用方法
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    public <T> T getParamsFromActivity(String key, T defaultValue) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.get(key) == null) {
                return defaultValue;
            } else {
                return (T) extras.get(key);
            }
        } else {
            return defaultValue;
        }
    }


    @Override
    public void onTitleButtonClicked(TitleBuilder.TitleButton clicked) {
        onTitleClickListen(clicked);
    }

    @Override
    public void onBackPressed() {
        onTitleButtonClicked(TitleBuilder.TitleButton.LEFT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus() && event.getAction() == MotionEvent.ACTION_UP) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


    protected View statusTv;
    protected boolean whiteStatus = true;//true:状态栏字体显示为黑色
    /**
     * 设置statusBar颜色
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarUpperAPI21();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarUpperAPI19();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarUpperAPI21() {
        Window window = getWindow();
        // android6.0及以上设置状态栏字体为深色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (whiteStatus)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // 魅族和小米设置状态栏字体为灰色
            SystemUtil.flymeSetStatusBarLightMode(window, whiteStatus);
            SystemUtil.MIUISetStatusBarLightMode(window, whiteStatus);
        } else {// 6.0以下并且不是魅族和小米设置状态栏为灰色适应状态栏白色字体
            boolean isFlyme = SystemUtil.flymeSetStatusBarLightMode(window, whiteStatus);
            boolean isMiui = SystemUtil.MIUISetStatusBarLightMode(window, whiteStatus);
            if (!isMiui && !isFlyme)
                window.setStatusBarColor(getResources().getColor(R.color.status_gray));
        }
    }

    private void setStatusBarUpperAPI19() {
        Window window = getWindow();
        boolean isFlyme = SystemUtil.flymeSetStatusBarLightMode(window, whiteStatus);
        boolean isMiui = SystemUtil.MIUISetStatusBarLightMode(window, whiteStatus);
        if (!isMiui && !isFlyme) {// 6.0以下并且不是魅族和小米设置状态栏为灰色一适应状态栏白色字体
            statusTv.setBackgroundResource(R.color.status_gray);
        }
    }



}
