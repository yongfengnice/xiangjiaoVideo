package com.android.baselibrary.base;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.baselibrary.R;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.loading.VaryViewHelperController;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import org.simple.eventbus.EventBus;
import java.lang.reflect.Field;
import java.util.Timer;


public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements
        BaseView, TitleBuilder.TitleBuilderListener {

    protected Activity mContext;                                                                    //宿主activity

    public VaryViewHelperController mVaryViewHelperController;                                     //视图加载控制器

    protected View mRootView;

    protected T mPresenter;

    public FrameLayout mTitle_container;

    public TitleBuilder mTitleBuilder;  //标题

    private int recLen = 0;

    private Timer timer;

    private String activityName;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this.mContext = (Activity) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getClass().isAnnotationPresent(YQApi.class)) {
            if (null == mRootView) {
                YQApi annotation = getClass().getAnnotation(YQApi.class);
                // 总布局
                int rootViewId = getLayoutView();
                this.mContext = getActivity();
                mRootView = inflater.inflate(R.layout.activity_title_bar, container, false);
                mTitle_container = (FrameLayout) mRootView.findViewById(R.id.title_container);
                setContent(rootViewId);
                initTitle();
                // startTimer();
                if (useEventBus()) {
                    EventBus.getDefault().unregister(this);
                    EventBus.getDefault().register(this);
                }
//                initInjector();
                if (getLoadingTargetView() != null) {
                    mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
                }
                initUiAndListener(mRootView);
                baseInit();
                if (null != getChildPresenter()) {
                    mPresenter = getChildPresenter();
                }
            } else {
                ViewGroup parent = (ViewGroup) mRootView.getParent();
                if (null != parent) {
                    parent.removeView(mRootView);
                }
            }
        } else {
            throw new RuntimeException("请配置YQApi.class");
        }

        return mRootView;
    }

    private void setContent(int resID) {
        View content_view = getActivity().getLayoutInflater().inflate(resID, null);
        mTitle_container.addView(content_view);
    }

    private void initTitle() {
        mTitleBuilder = new TitleBuilder(getActivity());
        mTitleBuilder.initBar(mRootView.findViewById(R.id.id_title_bar));
        mTitleBuilder.setTitleBuilderListener(this);
        initToolBar(mTitleBuilder);
    }

    /**
     * 设置title是否可见  默认是可见状态
     *
     * @param visible
     */
    public void setToolBarVisible(int visible) {
        mRootView.findViewById(R.id.id_title_bar).setVisibility(visible);
        mRootView.findViewById(R.id.title_line).setVisibility(visible);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
        Intent intent = new Intent(getActivity(), pClass);
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
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /****************
     * begain  网络请求相关
     ***************/
    private KProgressHUD hud;

    @Override
    public void showRequestOutData() {

    }

    @Override
    public void showDialogLoading() {
        if (getActivity() != null && !getActivity().isFinishing() && hud == null) {
            hud = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.show();
        } else if (hud != null && !hud.isShowing()) {
            hud.show();
        }
    }

    @Override
    public void showDialogLoading(String msg) {
        if (getActivity() != null && !getActivity().isFinishing() && hud == null) {
            hud = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.setLabel(msg);
            hud.show();
        } else if (hud != null && !hud.isShowing()) {
            hud.show();
        }
    }

    @Override
    public void showToast(String msg) {
        //DG_Toast.show(msg);
        ToastUtil.showToast(msg);
    }

    @Override
    public void showTopTip(String text, int res) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            ((BaseActivity) activity).showTopTip(text, res);
        } else {
            ToastUtil.showToast(text);
        }

    }

    @Override
    public void hideDialogLoading() {
        if (hud != null)
            try {
                hud.dismiss();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        hud = null;
    }

    @Override
    public void hidePageLoading() {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//        } else {
//            mVaryViewHelperController.hideloading();
//        }

    }

    @Override
    public void showPageLoading() {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//        } else {
//            mVaryViewHelperController.showLoading();
//        }

    }

    @Override
    public void refreshView() {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//        } else {
//            mVaryViewHelperController.restore();
//        }

    }

    @Override
    public void showNetError() {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//        } else {
//            mVaryViewHelperController.showNetworkError(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //mPresenter.requestAgain();
//                    onReloadClicked();
//                }
//            });
//        }
    }

    @Override
    public void showNetError(String msg, int icon) {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//        } else {
//            mVaryViewHelperController.showNetworkError(msg, icon, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //mPresenter.requestAgain();
//                    onReloadClicked();
//                }
//            });
//        }

    }

    @Override
    public void gotoLogin() {

    }

    public void showEmptyView(String msg, int icon) {
//        if (mVaryViewHelperController == null) {
//            if (BuildConfigUtil.DEBUG) {
//                throw new IllegalStateException("no ViewHelperController");
//            }
//        } else {
//            mVaryViewHelperController.showEmpty(msg, icon);
//        }

    }

    /****************end  网络请求相关***************/
    /**
     * 注入Injector
     */
//    public abstract void initInjector();

    /****************end dagger相关***************/

    protected abstract int getLayoutView();

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

    /**
     * 供子类初始化一些配置，包括dagger等东西
     *
     * @return
     */
    protected void baseInit() {

    }

    /**
     * init UI && Listener
     */
    public abstract void initUiAndListener(View view);

    /**
     * 子类设置标题
     *
     * @param mTitleBuilder
     */
    public abstract void initToolBar(TitleBuilder mTitleBuilder);

    /**
     * init UI && Listener
     */
    //public abstract void setButterKnife();

    /**
     * 失败后再次加载
     */
    public void onReloadClicked() {

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("fragment1", this.getClass().getSimpleName() + "onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("fragment1", this.getClass().getSimpleName() + "onPause");
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        Log.e("fragment1", this.getClass().getSimpleName() + "onDestroyOptionsMenu");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // endTimer();
        //ButterKnife.unbind(this);
        Log.e("fragment1", this.getClass().getSimpleName() + "onDestroy");
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        Glide.with(this).pauseRequests();
    }

    public String getActivityTag() {
        return "";
    }

    /**
     * 根据 Fragment 生命周期
     * 判断 当前 fragment 是否还存在于Activity中
     *
     * @return
     */
    public boolean hasExist() {
        return isAdded() && !isDetached();
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }


    protected <T extends View> T $(int id) {
        if (mRootView == null) {
            return null;
        }

        return (T) mRootView.findViewById(id);
    }

    @Override
    public void onTitleButtonClicked(TitleBuilder.TitleButton clicked) {


    }


}