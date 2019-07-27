package com.baby.app.modules.mine.page;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.mine.HistoryBean;
import com.android.baselibrary.service.bean.video.VideoInComeBean;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.util.StringUtils;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.adapter.CacheAdapter;
import com.baby.app.modules.mine.adapter.NoCacheAdapter;
import com.baby.app.modules.video.page.VideoActivity;
import com.baby.app.service.DownInfoModel;
import com.baby.app.service.DownLoadInfo;
import com.baby.app.service.DownLoadProgress;
import com.baby.app.service.DownLoadServer;
import com.baby.app.service.IDownLoadServer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yongqianggeng on 2018/10/4.
 * 我的缓存
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class MyCacheActivity extends IBaseActivity {
    @BindView(R.id.root_view)
    RelativeLayout root_view;
    @BindView(R.id.cache_recycler)
    RecyclerView cacheRecycler;
    @BindView(R.id.no_cache_recycler)
    RecyclerView noRecycler;
    @BindView(R.id.cache_layout)
    LinearLayout cacheLayout;
    @BindView(R.id.no_cache_layout)
    LinearLayout noCache;

    @BindView(R.id.controll_bottom_view)
    LinearLayout controll_bottom_view;
    @BindView(R.id.control_seleted_view)
    RelativeLayout control_seleted_view;
    @BindView(R.id.control_delete_view)
    RelativeLayout control_delete_view;
    @BindView(R.id.control_seleted_text_view)
    TextView control_seleted_text_view;

    private CacheAdapter cacheAdapter;
    private NoCacheAdapter noCacheAdapter;
    //private View emptyView;
    // private MyCacheAdapter mMyCacheAdapter;
    private DownInfoModel model;
    private List<DownLoadInfo> cacheList;
    private List<DownLoadInfo> noCacheList;
    private IDownLoadServer iDownLoadServer;

    private boolean isEdit = false;
    private boolean isAll = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (0 == what) {
                noCacheList = model.getNoCachBean();
                if(noCache.getVisibility() != View.VISIBLE)
                    noCache.setVisibility(View.VISIBLE);
                if (null != noCacheAdapter) {
                    noCacheAdapter.setData(noCacheList);
                    noCacheAdapter.notifyChange();
                }
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noCacheList = model.getNoCachBean();
                        cacheList = model.getCachBean();
                        setVisiable();
                        noCacheAdapter.setData(noCacheList);
                        cacheAdapter.setData(noCacheList);

                        noCacheAdapter.notifyDataSetChanged();
                        cacheAdapter.notifyDataSetChanged();
                    }
                },500);

            }

        }
    };

    @Override
    protected int getLayoutView() {
        return R.layout.activity_my_cache;
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
                isEdit = !isEdit;
                if (isEdit) {
                    mTitleBuilder.setRightText("完成");
                    controll_bottom_view.setVisibility(View.VISIBLE);
                    noCacheAdapter.setEdit(true);
                    cacheAdapter.setEdit(true);
                } else {
                    mTitleBuilder.setRightText("编辑");
                    controll_bottom_view.setVisibility(View.GONE);
                    cacheAdapter.setEdit(false);
                    noCacheAdapter.setEdit(false);
                    for (DownLoadInfo downLoadInfo:cacheList) {
                        downLoadInfo.setEdit(false);
                        model.updateEditByUrl(downLoadInfo.getUrl(),false);
                    }
                    for (DownLoadInfo downLoadInfo:noCacheList) {
                        downLoadInfo.setEdit(false);
                        model.updateEditByUrl(downLoadInfo.getUrl(),false);
                    }
                }

                noCacheAdapter.notifyDataSetChanged();
                cacheAdapter.notifyDataSetChanged();

                break;
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("离线缓存")
                .setLeftDrawable(R.mipmap.ic_back_brown).setRightText("编辑").setRightTextColor(Color.parseColor("#FFFA7334"));
        Intent intent = new Intent(this, DownLoadServer.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        cacheAdapter = new CacheAdapter(this);
        noCacheAdapter = new NoCacheAdapter(this);
        model = new DownInfoModel();
        cacheList = model.getCachBean();
        noCacheList = model.getNoCachBean();
        setVisiable();
        setRecycle();
    }

    private void setVisiable(){
        if (null != cacheList && cacheList.size() > 0) {
            cacheLayout.setVisibility(View.VISIBLE);
            cacheAdapter.setData(cacheList);
        }else{
            cacheLayout.setVisibility(View.GONE);
        }
        if (null != noCacheList && noCacheList.size() > 0) {
            noCache.setVisibility(View.VISIBLE);
            noCacheAdapter.setData(noCacheList);
        }else {
            noCache.setVisibility(View.GONE);
        }
    }

    private void setRecycle() {

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        cacheRecycler.setLayoutManager(manager);
        cacheRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ScreenUtil.dip2px(mContext, 12);
            }
        });
        cacheRecycler.setAdapter(cacheAdapter);
        cacheRecycler.setNestedScrollingEnabled(false);

        LinearLayoutManager mg = new LinearLayoutManager(mContext);
        mg.setOrientation(OrientationHelper.VERTICAL);
        noRecycler.setLayoutManager(mg);
        noRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ScreenUtil.dip2px(mContext, 12);
            }
        });
        noRecycler.setAdapter(noCacheAdapter);
        noRecycler.setNestedScrollingEnabled(false);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                iDownLoadServer = IDownLoadServer.Stub.asInterface(iBinder);
                iDownLoadServer.setProgress(downLoadProgress);
                try {
                    noCacheList = model.getNoCachBean();
                    if (noCacheList!=null) {
                        for (DownLoadInfo downLoadInfo:noCacheList) {
//                            Toast.makeText(mContext,"正在开始",Toast.LENGTH_SHORT).show();
                            iDownLoadServer.resume(downLoadInfo.getDownId(),downLoadInfo.getUrl());
                            break;
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    DownLoadProgress downLoadProgress = new DownLoadProgress() {
        @Override
        public void progress(long curduration, long allduration) throws RemoteException {
            handler.sendEmptyMessage(0);

        }

        @Override
        public void downStatus(int status) throws RemoteException {
            handler.sendEmptyMessage(1);
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    };

    private void addEmpty() {
        //失败页面
        final RelativeLayout emptyView = (RelativeLayout)getLayoutInflater().inflate(R.layout.cache_error_layout, null);
        if (noCacheList == null) {
            noCacheList = new ArrayList<>();
        }
        if (cacheList == null) {
            cacheList = new ArrayList<>();
        }
        if (noCacheList.size() == 0 && cacheList.size() == 0) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            emptyView.setLayoutParams(layoutParams);
            root_view.addView(emptyView);
        }
    }

    @Override
    public void initUiAndListener() {
        addEmpty();

        control_seleted_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAll = !isAll;
                if (isAll) {
                    control_seleted_text_view.setText("取消全选");
                    for (DownLoadInfo downLoadInfo:noCacheList) {
                        downLoadInfo.setEdit(true);
                        model.updateEditByUrl(downLoadInfo.getUrl(),true);
                    }
                    for (DownLoadInfo downLoadInfo:cacheList) {
                        downLoadInfo.setEdit(true);
                        model.updateEditByUrl(downLoadInfo.getUrl(),true);
                    }
                } else {
                    control_seleted_text_view.setText("全选");
                    for (DownLoadInfo downLoadInfo:noCacheList) {
                        downLoadInfo.setEdit(false);
                        model.updateEditByUrl(downLoadInfo.getUrl(),false);
                    }
                    for (DownLoadInfo downLoadInfo:cacheList) {
                        downLoadInfo.setEdit(false);
                        model.updateEditByUrl(downLoadInfo.getUrl(),false);
                    }
                }
                cacheAdapter.notifyDataSetChanged();
                noCacheAdapter.notifyDataSetChanged();
            }
        });

        control_delete_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isHave = false;
                if (noCacheList != null) {
                    for (DownLoadInfo downLoadInfo:noCacheList) {
                        if (downLoadInfo.getEdit()) {
                            isHave = true;
                            try {
                                model.delete(downLoadInfo.getUrl());
                                iDownLoadServer.delete(downLoadInfo.getUrl());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (cacheList!=null) {
                    for (DownLoadInfo downLoadInfo:cacheList) {
                        if (downLoadInfo.getEdit()) {
                            isHave = true;
                            try {
                                model.delete(downLoadInfo.getUrl());
                                iDownLoadServer.delete(downLoadInfo.getUrl());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                if (isHave){
                    noCacheList = model.getNoCachBean();
                    cacheList = model.getCachBean();
                    noCacheAdapter.notifyDataSetChanged();
                    cacheAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showToast("请选择");
                }
                addEmpty();


            }
        });



        cacheAdapter.setOnItemClickListener(new CacheAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DownLoadInfo info) {
                jumpToVideo(Integer.parseInt(info.getVideoId()),info.getName(),info.getPath());
            }
        });

        noCacheAdapter.setOnItemClickListener(new CacheAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DownLoadInfo info) {
                int status = info.getStatus();
//                if(status != 1){// 没有在下载中开始下载
//                    try {
//                        info.setStatus(1);
//                        Toast.makeText(mContext,"正在开始",Toast.LENGTH_SHORT).show();
//                        model.updateStatusByUrl(info.getUrl(),1);
//                        iDownLoadServer.resume(info.getDownId(),info.getUrl());
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }else{
////                    try {
////                        info.setStatus(0);
////                        Toast.makeText(mContext,"正在暂停",Toast.LENGTH_SHORT).show();
////                        model.updateStatusByUrl(info.getUrl(),0);
////                        iDownLoadServer.pause(info.getDownId(),info.getUrl());
////                    } catch (RemoteException e) {
////                        e.printStackTrace();
////                    }
//                }
            }
        });
    }

    public void jumpToVideo(int id, String name, String url) {
        VideoInComeBean videoInComeBean = new VideoInComeBean();
        videoInComeBean.setId(id);
        videoInComeBean.setIsCache(1);
        videoInComeBean.setVideoName(name);
        videoInComeBean.setVideoUrl(url);
        Bundle bundle = new Bundle();
        bundle.putSerializable(VideoActivity.VIDEO_KEY, (Serializable) videoInComeBean);
        bundle.putString("c","1");
        openActivity(VideoActivity.class,bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            iDownLoadServer.setProgress(null);
            downLoadProgress = null;
            unbindService(conn);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
