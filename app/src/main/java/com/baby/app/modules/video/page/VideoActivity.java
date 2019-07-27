package com.baby.app.modules.video.page;


import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.page.CommonWebViewActivity;
import com.android.baselibrary.service.bean.home.HomeBannerBean;
import com.android.baselibrary.service.bean.home.HomeListBean;
import com.android.baselibrary.service.bean.video.VideoCommentBean;
import com.android.baselibrary.service.bean.video.VideoDetailBean;
import com.android.baselibrary.service.bean.video.VideoInComeBean;
import com.android.baselibrary.service.bean.video.VideoLikeBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.util.GlideUtils;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.bumptech.glide.Glide;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.login.LoginActivity;
import com.baby.app.modules.mine.page.ErActivity;
import com.baby.app.modules.mine.page.PromoteActivity;
import com.baby.app.modules.mine.page.VipActivity;
import com.baby.app.modules.video.adapter.VideoTypeAdapter;
import com.baby.app.modules.video.bean.VideoTypeBean;
import com.baby.app.modules.video.iview.IVideoView;
import com.baby.app.modules.video.presenter.VideoPresenter;
import com.baby.app.service.AnalysisComplete;
import com.baby.app.service.DownInfoModel;
import com.baby.app.service.DownLoadServer;
import com.baby.app.service.IDownLoadServer;
import com.baby.app.widget.MyDialogUtil;
import com.baby.app.widget.dialog.CommonDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


@YQApi(
        swipeback = true,
        openAnimation = -1,
        closAnimatione = -1
)
public class VideoActivity extends IBaseActivity implements IVideoView
{

    public static final String VIDEO_KEY = "VIDEO_KEY";
    private static final int COMMENT_REQUEST = 101;
//    @BindView(R.id.video_back_view)
//    RelativeLayout back_view;
    @BindView(R.id.video_list)
    RecyclerView videoRecycleView;

    @BindView(R.id.videoplayer)
    JzvdStd mJzvdStd;

    @BindView(R.id.video_chat_back_view)
    RelativeLayout videoChatBackView;

    @BindView(R.id.video_chat_edit)
    EditText videoChatEdit;

    @BindView(R.id.video_back_btn)
    RelativeLayout video_back_btn;
    @BindView(R.id.play_view)
    ImageView play_view;

    private IDownLoadServer iDownLoadServer;

    private VideoInComeBean mVideoInComeBean;
    private VideoTypeAdapter mVideoTypeAdapter;
    private List<VideoTypeBean> videoTypeBeanList = new ArrayList<>();

    private VideoPresenter mVideoPresenter;

    private VideoDetailBean mVideoDetailBean;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_video;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                if (mJzvdStd.backPress()) {
                    return;
                } else {
                    videoRelease();
                    finish();
                }

                break;
            case MIDDLE:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mJzvdStd!=null) {
            if (mJzvdStd.currentState == Jzvd.CURRENT_STATE_PAUSE) {
                mJzvdStd.onVideoResume();
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mJzvdStd!=null) {
            if (mJzvdStd.currentState == Jzvd.CURRENT_STATE_PLAYING) {
                mJzvdStd.onVideoPause();
            }
        }
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
            //解析成功
            mVideoPresenter.usedViewOrCacheNum("2",String.valueOf(mVideoInComeBean.getId()));

            mVideoTypeAdapter.notifyDataSetChanged();
        }
        @Override
        public IBinder asBinder() {
            return null;
        }
    };

    /**
     * 开始下载
     *
     * @param url
     */

    public void startDownLoad(String url,String name,String cover,String id) {
        final DownInfoModel model = new DownInfoModel();
        if(model.findByStatus()){
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
    public void initToolBar(TitleBuilder mTitleBuilder) {
        setToolBarVisible(View.GONE);
        Intent intent = new Intent(this, DownLoadServer.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
//        mTitleBuilder.seTitleBgColor(Color.parseColor("#FFFA7334"));
//        setToolBarLineVisible(View.GONE);
//        mTitleBuilder.setMiddleTitleText("").setMiddleTitleTextColor(Color.parseColor("#FFFFFFFF")).setLeftDrawable(R.mipmap.ic_white_brown);
    }

    @Override
    public void initUiAndListener() {
        ImageView v_my_head_view = (ImageView) findViewById(R.id.v_my_head_view);
        if (UserStorage.getInstance().getHeadpic()!=null) {
            GlideUtils
                    .getInstance()
                    .LoadContextCircleBitmap(mContext,
                            UserStorage.getInstance().getHeadpic(),
                            v_my_head_view,
                            R.mipmap.ic_head_l,
                            R.mipmap.ic_head_l);
        }

        RelativeLayout video_comment_btn = (RelativeLayout)findViewById(R.id.video_comment_btn);
        video_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserStorage.getInstance().getSortNo() >= 2 || UserStorage.getInstance().getIsVip() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.KEY_INTENT_ACTIVITY,String.valueOf(mVideoDetailBean.getData().getId()));
                    Intent intent = new Intent(VideoActivity.this, CommentActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,COMMENT_REQUEST);
                } else {
                    MyDialogUtil.showCommonDialogDialog(VideoActivity.this, "用户等级2级才可发表评论，邀请好友就可以升级啦！", new CommonDialog.CommonDialogLisenter() {
                        @Override
                        public void onClick() {
                            openActivity(ErActivity.class);
                            finish();
                        }
                    });
                }
            }
        });


        play_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogUtil.showCommonDialogDialog(VideoActivity.this, "今日的观影次数已耗尽，邀请好友就获得更多观影次数啦", new CommonDialog.CommonDialogLisenter() {
                    @Override
                    public void onClick() {
                        openActivity(ErActivity.class);
                        finish();
                    }
                });
            }
        });
        video_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mJzvdStd.setVisibility(View.INVISIBLE);
        mVideoPresenter = new VideoPresenter(this);
        mVideoInComeBean = (VideoInComeBean) (getIntent().getExtras().getSerializable(VIDEO_KEY));

        mJzvdStd.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mJzvdStd.backPress()) {
                    return;
                } else {
                    videoRelease();
                    finish();
                }
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        videoRecycleView.setHasFixedSize(true);
        videoRecycleView.setNestedScrollingEnabled(false);
        videoRecycleView.setLayoutManager(linearLayoutManager);

        videoTypeBeanList.add(new VideoTypeBean(VideoTypeBean.LAYOUT_VIDEO_1));
        videoTypeBeanList.add(new VideoTypeBean(VideoTypeBean.LAYOUT_VIDEO_2));
        videoTypeBeanList.add(new VideoTypeBean(VideoTypeBean.LAYOUT_VIDEO_3));
        videoTypeBeanList.add(new VideoTypeBean(VideoTypeBean.LAYOUT_VIDEO_4));
        videoTypeBeanList.add(new VideoTypeBean(VideoTypeBean.LAYOUT_VIDEO_5));

        if (mVideoInComeBean.getIsCache() == 1) {
            String cache = (String) getIntent().getStringExtra("c");
            if (cache != null && cache.equals("1")) {
                mJzvdStd.setUp(mVideoInComeBean.getVideoUrl(), mVideoInComeBean.getVideoName()
                        , JzvdStd.SCREEN_WINDOW_FULLSCREEN);
            } else {
                mJzvdStd.setUp(mVideoInComeBean.getVideoUrl(), mVideoInComeBean.getVideoName()
                        , JzvdStd.SCREEN_WINDOW_NORMAL);
            }

//        Glide.with(this)
//                .load(VideoConstant.videoThumbList[0])
//                .into(mJzvdStd.thumbImageView);

            Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            mJzvdStd.startVideoFirst();
        }

//        videoChatEdit.setImeOptions(EditorInfo.IME_ACTION_SEND);
//        addOnSoftKeyBoardVisibleListener();
//        setListenerToRootView();
//        videoChatEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId==EditorInfo.IME_ACTION_SEND || (event!=null &&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
//                    //TODO:发送
//
//                    return true;
//                }
//                return false;
//            }
//        });

        mVideoPresenter.fetchDeitalData(String.valueOf(mVideoInComeBean.getId()));

    }

    public void videoRelease (){
        Jzvd.releaseAllVideos();
        //Change these two variables back
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public void refreshDetail(VideoDetailBean videoDetailBean) {
        mJzvdStd.setVisibility(View.VISIBLE);
        mVideoDetailBean = videoDetailBean;
        if (mVideoDetailBean!=null && mVideoDetailBean.getData().getVideoName() != null) {
            mTitleBuilder.setMiddleTitleText(mVideoDetailBean.getData().getVideoName());
        }
        if (mVideoInComeBean.getIsCache() == 0) {
            if (mVideoDetailBean.getUseView()!=null && mVideoDetailBean.getUseView().equals("1")) {
                mTitleBuilder.setMiddleTitleText(mVideoDetailBean.getData().getVideoName());
                mJzvdStd.setVisibility(View.VISIBLE);
                //        if (mVideoInComeBean.getVideoUrl() == null) {

                String cache = (String) getIntent().getStringExtra("c");
                if (cache != null && cache.equals("1")) {
                    mJzvdStd.setUp(mVideoDetailBean.getData().getVideoUrl(), mVideoDetailBean.getData().getVideoName()
                            , JzvdStd.SCREEN_WINDOW_FULLSCREEN);

                } else {
                    mJzvdStd.setUp(mVideoDetailBean.getData().getVideoUrl(), mVideoDetailBean.getData().getVideoName()
                            , JzvdStd.SCREEN_WINDOW_NORMAL);
                }


//        Glide.with(this)
//                .load(VideoConstant.videoThumbList[0])
//                .into(mJzvdStd.thumbImageView);

                Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                mJzvdStd.startVideoFirst();
                play_view.setVisibility(View.GONE);
                video_back_btn.setVisibility(View.GONE);
                //TODO:扣除播放次数
                mVideoPresenter.usedViewOrCacheNum("1",String.valueOf(mVideoInComeBean.getId()));
//        }
            } else {
                play_view.setVisibility(View.VISIBLE);
                video_back_btn.setVisibility(View.VISIBLE);
                videoRelease();
                MyDialogUtil.showCommonDialogDialog(this, "今日的观影次数已耗尽，邀请好友就获得更多观影次数啦", new CommonDialog.CommonDialogLisenter() {
                    @Override
                    public void onClick() {
                        openActivity(ErActivity.class);
                        finish();
                    }
                });
                mJzvdStd.setVisibility(View.INVISIBLE);
            }

        }


        mVideoTypeAdapter = new VideoTypeAdapter(videoTypeBeanList,videoDetailBean, mContext);
        videoRecycleView.setAdapter(mVideoTypeAdapter);

        if (mVideoDetailBean.getData().getBannerListHead().size() > 0) {
            final HomeBannerBean bannerBean = mVideoDetailBean.getData().getBannerListHead().get(0);
            mJzvdStd.setBannerImg(bannerBean, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mVideoPresenter.clickAd(String.valueOf(bannerBean.getId()));
                    jumpToTagActivity(bannerBean);
                }
            });
        }

        mVideoTypeAdapter.setmVideoTypeAdapterLisenter(new VideoTypeAdapter.VideoTypeAdapterLisenter() {
            @Override
            //评论
            public void sendComment() {
                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                if (UserStorage.getInstance().getSortNo() >= 2 || UserStorage.getInstance().getIsVip() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.KEY_INTENT_ACTIVITY,String.valueOf(mVideoDetailBean.getData().getId()));
                    Intent intent = new Intent(VideoActivity.this, CommentActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,COMMENT_REQUEST);
                } else {
                    MyDialogUtil.showCommonDialogDialog(VideoActivity.this, "用户等级2级才可发表评论，邀请好友就可以升级啦！", new CommonDialog.CommonDialogLisenter() {
                        @Override
                        public void onClick() {
                            openActivity(ErActivity.class);
                            finish();
                        }
                    });
                }
            }

            @Override
            //点赞
            public void setZan() {
                mVideoPresenter.setCareTimes(String.valueOf(mVideoDetailBean.getData().getId()),"1");
            }

            @Override
            //不点赞
            public void unSetZan() {
                mVideoPresenter.setCareTimes(String.valueOf(mVideoDetailBean.getData().getId()),"2");
            }

            @Override
            public void submitQuestion() {
                mVideoPresenter.saveloseVideo(String.valueOf(mVideoDetailBean.getData().getId()));
            }

            @Override
            public void showIntroduce(String introduce) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_INTENT_ACTIVITY,introduce);
                openActivity(IntroduceActivity.class,bundle);
            }

            @Override
            public void onLike() {
                if (UserStorage.getInstance().getUserType() == UserType.MARK_USER) {
                    mVideoPresenter.setCareHistory(String.valueOf(mVideoDetailBean.getData().getId()));
                } else {
                    jumpToLogin();
                }

            }

            @Override
            public void onDown() {
                mVideoPresenter.getCacheNum(String.valueOf(mVideoInComeBean.getId()));

            }

            @Override
            public void onShare() {
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(mVideoDetailBean.getData().getExtensionInfo().getExtensionContext());
                ToastUtil.showLongToast("请分享粘贴，已复制到系统剪切板");
            }

            @Override
            public void onBannerClick(HomeBannerBean bannerBean) {
                mVideoPresenter.clickAd(String.valueOf(bannerBean.getId()));
                jumpToTagActivity(bannerBean);
            }

            @Override
            public void onStarListClick(HomeListBean homeListBean) {
                videoRelease();
                finish();
                jumpToVideo(homeListBean.getId(),homeListBean.getVideoName(),homeListBean.getVideoUrl());

            }

            @Override
            public void gotoDetail(VideoLikeBean likeBean) {
                jumpToVideo(likeBean.getId(),likeBean.getVideoName(),null);
            }
        });

        mVideoPresenter.getVideoCommon(String.valueOf(mVideoInComeBean.getId()));



    }

    @Override
    public void refreshComment(VideoCommentBean videoCommentBean) {
        mVideoTypeAdapter.refershComment(videoCommentBean);
    }

    @Override
    public void dontPlayVideo() {

        videoRelease();
        MyDialogUtil.showCommonDialogDialog(this, "今日的观影次数已耗尽，邀请好友就获得更多观影次数啦", new CommonDialog.CommonDialogLisenter() {
            @Override
            public void onClick() {
                openActivity(ErActivity.class);
                finish();
            }
        });
    }

    @Override
    public void isCanDown() {
        startDownLoad(mVideoDetailBean.getData().getVideoUrl()
                ,mVideoDetailBean.getData().getVideoName(),mVideoDetailBean.getData().getVideoCover(),
                String.valueOf(mVideoDetailBean.getData().getId()));
    }

    @Override
    public void onCacheFailed() {
        MyDialogUtil.showCommonDialogDialog(this, "今日的缓存次数已耗尽，邀请好友就获得更多观影次数啦", new CommonDialog.CommonDialogLisenter() {
            @Override
            public void onClick() {
                openActivity(ErActivity.class);
                finish();
            }
        });
    }


    /**============================================键盘相关========================================================*/
    //一个静态变量存储高度
    public int keyboardHeight = 0;
    boolean isVisiableForLast = false;
    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = null;
    public void addOnSoftKeyBoardVisibleListener() {
        if (keyboardHeight > 0) {
            return;
        }
        final View decorView = getWindow().getDecorView();
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHight = rect.bottom - rect.top;
                //获得屏幕整体的高度
                int hight = decorView.getHeight();
                boolean visible = (double) displayHight / hight < 0.8;
                int statusBarHeight = 0;
                try {
                    Class<?> c = Class.forName("com.android.internal.R$dimen");
                    Object obj = c.newInstance();
                    Field field = c.getField("status_bar_height");
                    int x = Integer.parseInt(field.get(obj).toString());
                    statusBarHeight = getApplicationContext().getResources().getDimensionPixelSize(x);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (visible && visible != isVisiableForLast) {
                    //获得键盘高度
                    keyboardHeight = hight - displayHight - statusBarHeight;
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)videoChatBackView.getLayoutParams();
                    if (Build.BRAND.toLowerCase().contains("huawei") || Build.MANUFACTURER.equals("Huawei") || Build.BRAND.toLowerCase().contains("honor")) {
                        layoutParams.bottomMargin =keyboardHeight+ ScreenUtil.dip2px(VideoActivity.this,10);
                    } else {
                        layoutParams.bottomMargin =keyboardHeight+ScreenUtil.dip2px(VideoActivity.this,30);
                    }
                    videoChatBackView.setLayoutParams(layoutParams);
                    videoChatEdit.requestFocus();

                }
                isVisiableForLast = visible;
            }
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    private void setListenerToRootView() {
        final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                boolean mKeyboardUp = hideKeyboard();
                if (mKeyboardUp) {
                    videoChatBackView.setVisibility(View.VISIBLE);
                } else {
                    videoChatBackView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    private boolean hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive(videoChatEdit)){
            return true;
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case COMMENT_REQUEST:
                mVideoPresenter.getVideoCommon(String.valueOf(mVideoDetailBean.getData().getId()));

                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mJzvdStd!=null) {
            mJzvdStd.release();
            if (mJzvdStd.currentState == Jzvd.CURRENT_STATE_PLAYING) {
                mJzvdStd.onVideoPause();
            }
        }
        try {
            if (iDownLoadServer != null) {
                iDownLoadServer.setAnalysis(null);
            }
            analysisComplete = null;
            unbindService(conn);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
