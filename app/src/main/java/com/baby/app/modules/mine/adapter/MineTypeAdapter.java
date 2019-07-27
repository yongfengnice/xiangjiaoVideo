package com.baby.app.modules.mine.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.baselibrary.recycleradapter.BaseMultiItemQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.mine.HistoryBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.util.SPUtils;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.baby.app.R;
import com.baby.app.modules.mine.bean.MineTypeBean;
import com.baby.app.service.DownInfoModel;
import com.baby.app.service.DownLoadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/29.
 * 我的
 */

public class MineTypeAdapter extends BaseMultiItemQuickAdapter<MineTypeBean,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private boolean isRefreshHeadUrl;
    private DownInfoModel downModel = new DownInfoModel();
    private List<MineTypeBean> mineTypeBeans;

    private MineTypeAdapterLisenter mMineTypeAdapterLisenter;

    private boolean isFistLoad = false;

    private MineHistoryAdapter mMineHistoryAdapter;
    private MineHistoryAdapter mMineLikeAdapter;
    private MineCacheAdapter mMineCacheAdapter;

    private List<HistoryBean>historyBeanList = new ArrayList<>();
    private List<DownLoadInfo>cacheHistoryBeanList = new ArrayList<>();
    private List<HistoryBean>likeHistoryBeanList = new ArrayList<>();

    public void setIsRefreshHeadUrl(boolean isRefreshHeadUrl) {
        this.isRefreshHeadUrl = isRefreshHeadUrl;
    }


    public void setMineTypeAdapterLisenter(MineTypeAdapterLisenter mineTypeAdapterLisenter){
        this.mMineTypeAdapterLisenter = mineTypeAdapterLisenter;
    }

    public MineTypeAdapter(List<MineTypeBean> data, Context context) {
        super(data);
        this.mineTypeBeans = data;

        int size = mineTypeBeans.size();
        for (int i = 0; i < size; i++) {
            switch (mineTypeBeans.get(i).getItemType()) {
                case MineTypeBean.LAYOUT_MINE_1:
                    addItemType(MineTypeBean.LAYOUT_MINE_1, R.layout.mine_layout_1);
                    break;
                case MineTypeBean.LAYOUT_MINE_4:
                    addItemType(MineTypeBean.LAYOUT_MINE_4, R.layout.mine_layout_4);
                    break;
                case MineTypeBean.LAYOUT_MINE_2:
                    addItemType(MineTypeBean.LAYOUT_MINE_2, R.layout.mine_layout_2);
                    break;
                case MineTypeBean.LAYOUT_MINE_3:
                    addItemType(MineTypeBean.LAYOUT_MINE_3, R.layout.mine_layout_3);
                    break;
                default:
                    break;
            }
        }
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineTypeBean item) {
        switch (helper.getItemViewType()) {
            case MineTypeBean.LAYOUT_MINE_1: {
                refreshLayout1(helper);
            }
            break;
            case MineTypeBean.LAYOUT_MINE_4: {
                RelativeLayout mine_left_btn = helper.getView(R.id.mine_left_btn);
                RelativeLayout mine_right_btn = helper.getView(R.id.mine_right_btn);
                mine_left_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mMineTypeAdapterLisenter != null) {
                            mMineTypeAdapterLisenter.gotoVip();
                        }
                    }
                });
                mine_right_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mMineTypeAdapterLisenter != null) {
                            mMineTypeAdapterLisenter.gotoWithDraw();
                        }
                    }
                });
            }
                break;
            case MineTypeBean.LAYOUT_MINE_2: {

                //TODO:推广
                helper.getView(R.id.action_1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UserStorage.getInstance().getUserType() == UserType.MARK_USER){
                            if (mMineTypeAdapterLisenter != null) {
                                mMineTypeAdapterLisenter.gotoPromote();
                            }
                        }else {
                            AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                                    .setTitle("提示")//设置对话框的标题
                                    .setMessage("请先登录")//设置对话框的内容
                                    //设置对话框的按钮
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mMineTypeAdapterLisenter.gotoLogin();
                                            dialog.dismiss();
                                        }
                                    }).create();
                            dialog.show();
                        }
                    }
                });
                //TODO:意见反馈
                helper.getView(R.id.action_3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMineTypeAdapterLisenter != null) {
                            mMineTypeAdapterLisenter.feedBack();
                        }
                    }
                });
                //TODO:通知
                helper.getView(R.id.action_4).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMineTypeAdapterLisenter != null) {
                            mMineTypeAdapterLisenter.gotoNotification();
                        }
                    }
                });
                //TODO:vip
                helper.getView(R.id.action_2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMineTypeAdapterLisenter != null) {
                            mMineTypeAdapterLisenter.gotoVip();
                        }
                    }
                });
                //TODO:火爆交流群
                helper.getView(R.id.action_5).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMineTypeAdapterLisenter != null) {
                            mMineTypeAdapterLisenter.gotoHotQun();
                        }
                    }
                });

            }
            break;

            case MineTypeBean.LAYOUT_MINE_3: {
                //我的缓存
                RelativeLayout myCacheBtn = helper.getView(R.id.my_cache_view);
                myCacheBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMineTypeAdapterLisenter != null) {
                            mMineTypeAdapterLisenter.gotoMyCache();
                        }
                    }
                });
                //我的历史
                RelativeLayout historyBtn = helper.getView(R.id.my_history_btn);
                historyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMineTypeAdapterLisenter != null) {
                            mMineTypeAdapterLisenter.gotoHistory();
                        }
                    }
                });
                //我的喜欢
                RelativeLayout myLikeBtn = helper.getView(R.id.my_like_btn);
                myLikeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMineTypeAdapterLisenter != null) {
                            mMineTypeAdapterLisenter.gotoMyLike();
                        }
                    }
                });

                refreshHistory(helper);
                refreshCache(helper);
                refreshLisk(helper);

            }
            break;
            default:
                break;
        }
    }

    private RecyclerView hRecyclerView;
    private RecyclerView cRecyclerView;
    private RecyclerView lRecyclerView;
    //历史
    private void refreshHistory(BaseViewHolder helper) {
        TextView m_h_text_view = helper.getView(R.id.m_h_text_view);
        if (UserStorage.getInstance().getHistoryBeanList()!=null && UserStorage.getInstance().getHistoryBeanList().size() > 0) {
            m_h_text_view.setText("目前历史观看过"+UserStorage.getInstance().getHistoryBeanList().size()+"部");
        } else {
            m_h_text_view.setText("目前历史观看过"+"0"+"部");
        }
        if (hRecyclerView == null) {
            if (UserStorage.getInstance().getHistoryBeanList()!=null && UserStorage.getInstance().getHistoryBeanList().size() > 0) {
                historyBeanList.addAll(UserStorage.getInstance().getHistoryBeanList());
            }
            hRecyclerView = helper.getView(R.id.h_recycler_view);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(OrientationHelper.HORIZONTAL);
            hRecyclerView.setLayoutManager(manager);
            hRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int pos = parent.getChildAdapterPosition(view);
                    if (pos ==0) {
                        outRect.left = ScreenUtil.dip2px(mContext, 12);
                        outRect.right = ScreenUtil.dip2px(mContext, 24);
                    } else {
                        outRect.left = ScreenUtil.dip2px(mContext, 0);
                        outRect.right = ScreenUtil.dip2px(mContext, 24);
                    }
                }
            });
            mMineHistoryAdapter = new MineHistoryAdapter(R.layout.item_mine_history_layout, historyBeanList);
            hRecyclerView.setAdapter(mMineHistoryAdapter);
            mMineHistoryAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    HistoryBean historyBean = historyBeanList.get(position);
                    if (mMineTypeAdapterLisenter!=null) {
                        mMineTypeAdapterLisenter.gotoDetail(historyBean);
                    }
                }
            });
        } else {
            historyBeanList.clear();
            if (UserStorage.getInstance().getHistoryBeanList()!=null && UserStorage.getInstance().getHistoryBeanList().size() > 0) {
                historyBeanList.addAll(UserStorage.getInstance().getHistoryBeanList());
            }
            mMineHistoryAdapter.notifyDataSetChanged();
        }
        if (UserStorage.getInstance().getHistoryBeanList()!=null &&UserStorage.getInstance().getHistoryBeanList().size() > 0) {
            hRecyclerView.setVisibility(View.VISIBLE);
        } else {
            hRecyclerView.setVisibility(View.GONE);
        }
    }
    //我的缓存
    private void refreshCache(BaseViewHolder helper){
        TextView m_c_text_view = helper.getView(R.id.m_c_text_view);
        int count = 0;

        if (downModel.getCachBean()!=null && downModel.getCachBean().size() > 0) {
            count = count + downModel.getCachBean().size();
        }
        if (downModel.getNoCachBean()!=null && downModel.getNoCachBean().size() > 0) {
            count = count + downModel.getNoCachBean().size();
        }


//        if (downModel.getNoCachBean()!=null && downModel.getNoCachBean().size() > 0) {
//            count = count + downModel.getNoCachBean().size();
//        }
        m_c_text_view.setText("目前本地大片有"+count+"部");
        if (cRecyclerView == null) {
            if (downModel.getCachBean()!=null && downModel.getCachBean().size() > 0) {
                cacheHistoryBeanList.addAll(downModel.getCachBean());
            }
            if (downModel.getNoCachBean()!=null && downModel.getNoCachBean().size() > 0) {
                cacheHistoryBeanList.addAll(downModel.getNoCachBean());
            }

            cRecyclerView = helper.getView(R.id.c_recycler_view);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(OrientationHelper.HORIZONTAL);
            cRecyclerView.setLayoutManager(manager);
            cRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int pos = parent.getChildAdapterPosition(view);
                    if (pos ==0) {
                        outRect.left = ScreenUtil.dip2px(mContext, 12);
                        outRect.right = ScreenUtil.dip2px(mContext, 24);
                    } else {
                        outRect.left = ScreenUtil.dip2px(mContext, 0);
                        outRect.right = ScreenUtil.dip2px(mContext, 24);
                    }
                }
            });
            mMineCacheAdapter = new MineCacheAdapter(R.layout.item_mine_history_layout, cacheHistoryBeanList);
            cRecyclerView.setAdapter(mMineCacheAdapter);

            mMineCacheAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    DownLoadInfo downLoadInfo = cacheHistoryBeanList.get(position);
                    if (downLoadInfo.getStatus() == 1) {
                        if (mMineTypeAdapterLisenter!=null) {
                            mMineTypeAdapterLisenter.gotoCacheDetail(downLoadInfo);
                        }
                    } else {
                        ToastUtil.showToast("正在下载中...");
                    }

                }
            });

        } else {
            cacheHistoryBeanList.clear();
            if (downModel.getCachBean()!=null && downModel.getCachBean().size() > 0) {
                cacheHistoryBeanList.addAll(downModel.getCachBean());
            }
            if (downModel.getNoCachBean()!=null && downModel.getNoCachBean().size() > 0) {
                cacheHistoryBeanList.addAll(downModel.getNoCachBean());
            }
            mMineCacheAdapter.notifyDataSetChanged();
        }
        if (cacheHistoryBeanList!=null &&cacheHistoryBeanList.size() > 0) {
            cRecyclerView.setVisibility(View.VISIBLE);
        } else {
            cRecyclerView.setVisibility(View.GONE);
        }

    }
    //我喜欢
    private void refreshLisk(BaseViewHolder helper) {
        TextView m_l_text_view = helper.getView(R.id.m_l_text_view);
        if (UserStorage.getInstance().getCareHistoryList()!=null && UserStorage.getInstance().getCareHistoryList().size() > 0) {
            m_l_text_view.setText("目前已有喜欢"+UserStorage.getInstance().getCareHistoryList().size()+"部");
        } else {
            m_l_text_view.setText("目前已有喜欢"+"0"+"部");
        }
        if (lRecyclerView == null) {
            if (UserStorage.getInstance().getCareHistoryList()!=null && UserStorage.getInstance().getCareHistoryList().size() > 0) {
                likeHistoryBeanList.addAll(UserStorage.getInstance().getCareHistoryList());
            }
            lRecyclerView = helper.getView(R.id.x_recycler_view);
            LinearLayoutManager manager2 = new LinearLayoutManager(mContext);
            manager2.setOrientation(OrientationHelper.HORIZONTAL);
            lRecyclerView.setLayoutManager(manager2);
            lRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int pos = parent.getChildAdapterPosition(view);
                    if (pos ==0) {
                        outRect.left = ScreenUtil.dip2px(mContext, 12);
                        outRect.right = ScreenUtil.dip2px(mContext, 24);
                    } else {
                        outRect.left = ScreenUtil.dip2px(mContext, 0);
                        outRect.right = ScreenUtil.dip2px(mContext, 24);
                    }
                }
            });
            mMineLikeAdapter = new MineHistoryAdapter(R.layout.item_mine_history_layout, likeHistoryBeanList);
            lRecyclerView.setAdapter(mMineLikeAdapter);
            mMineLikeAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    HistoryBean historyBean = likeHistoryBeanList.get(position);
                    if (mMineTypeAdapterLisenter!=null) {
                        mMineTypeAdapterLisenter.gotoDetail(historyBean);
                    }
                }
            });
        } else {
            likeHistoryBeanList.clear();
            if (UserStorage.getInstance().getCareHistoryList()!=null && UserStorage.getInstance().getCareHistoryList().size() > 0) {
                likeHistoryBeanList.addAll(UserStorage.getInstance().getCareHistoryList());
            }
            mMineLikeAdapter.notifyDataSetChanged();
        }
        if (UserStorage.getInstance().getCareHistoryList()!=null && UserStorage.getInstance().getCareHistoryList().size() > 0) {
            lRecyclerView.setVisibility(View.VISIBLE);
        } else {
            lRecyclerView.setVisibility(View.GONE);
        }

    }

    private void refreshLayout1(BaseViewHolder helper){
        //等级
        TextView levelTextView = helper.getView(R.id.level_text_view);
        //头像
        RoundedImageView userHeadImageView = helper.getView(R.id.u_image_view);
        //等级图标
//        ImageView levelTopImageView = helper.getView(R.id.level_top_img);
//        ImageView level0ImageView = helper.getView(R.id.level_top_0_img);
        //手机号码
        TextView minePhoneTextView = helper.getView(R.id.mine_phone_text);
        //性别
//        ImageView sexImageView = helper.getView(R.id.mine_sex_view);

        RelativeLayout mineMasterView = helper.getView(R.id.mine_master_view);
        RelativeLayout mineNomalView = helper.getView(R.id.mine_nomal_view);
        RelativeLayout touch_login_view = helper.getView(R.id.touch_login_view);

        if (UserStorage.getInstance().getUserType() == UserType.MARK_USER) {
            mineNomalView.setVisibility(View.INVISIBLE);
            mineMasterView.setVisibility(View.VISIBLE);
            if (UserStorage.getInstance().getLevelName() != null) {
                levelTextView.setText(UserStorage.getInstance().getLevelName());
            } else {
                levelTextView.setText("加载中...");
            }
            if (UserStorage.getInstance().getHeadpic()!=null) {
                Glide.with(mContext.getApplicationContext())
                        .load(UserStorage.getInstance().getHeadpic())
                        .placeholder(userHeadImageView.getDrawable())
                        .skipMemoryCache(false)
                        .dontAnimate()
                        .error(R.mipmap.ic_head_l)
                        .into(userHeadImageView);
            } else {

            }
            if (UserStorage.getInstance().getNickName()!=null && UserStorage.getInstance().getNickName().length() > 0) {
                minePhoneTextView.setText(UserStorage.getInstance().getNickName());
            } else {
                if (UserStorage.getInstance().getPhone()!=null) {
                    minePhoneTextView.setText(UserStorage.getInstance().getPhone());
                } else {
                    minePhoneTextView.setText("看管大人请登录");
                }
            }

//            性别 1=男 2=女 3=未知

//            switch (UserStorage.getInstance().getSex()) {
//                case 1:
//                    sexImageView.setVisibility(View.VISIBLE);
//                    sexImageView.setImageResource(R.mipmap.ic_man);
//                    break;
//                case 2:
//                    sexImageView.setVisibility(View.VISIBLE);
//                    sexImageView.setImageResource(R.mipmap.ic_woman);
//                    break;
//                case 3:
//                    sexImageView.setVisibility(View.GONE);
//                    break;
//            }
            if (UserStorage.getInstance().getIsVip() == 1) {

                levelTextView.setText("VIP");
            }

        } else {
            mineNomalView.setVisibility(View.VISIBLE);
            minePhoneTextView.setVisibility(View.VISIBLE);
            minePhoneTextView.setText("看管大人请登录");
            userHeadImageView.setImageResource(R.mipmap.ic_head_l);
            mineMasterView.setVisibility(View.INVISIBLE);
            levelTextView.setText("登录注册");
        }

        touch_login_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserStorage.getInstance().getUserType() == UserType.NOMAL_USER) {
                    if (mMineTypeAdapterLisenter != null) {
                        mMineTypeAdapterLisenter.gotoLogin();
                    }
                }
            }
        });

        levelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserStorage.getInstance().getUserType() == UserType.NOMAL_USER) {
                    if (mMineTypeAdapterLisenter != null) {
                        mMineTypeAdapterLisenter.gotoLogin();
                    }
                }
            }
        });

//        //TODO:共有的信息
//        level0ImageView.setVisibility(View.INVISIBLE);
//        switch (UserStorage.getInstance().getSortNo()) {
//            case 0:
//                levelTopImageView.setVisibility(View.INVISIBLE);
//                level0ImageView.setVisibility(View.VISIBLE);
//                break;
//            case 1:
//                levelTopImageView.setVisibility(View.VISIBLE);
//                levelTopImageView.setImageResource(R.mipmap.ic_level1_s);
//                break;
//            case 2:
//                levelTopImageView.setVisibility(View.VISIBLE);
//                levelTopImageView.setImageResource(R.mipmap.ic_level2_s);
//                break;
//            case 3:
//                levelTopImageView.setImageResource(R.mipmap.ic_level3_s);
//                levelTopImageView.setVisibility(View.VISIBLE);
//                break;
//            case 4:
//                levelTopImageView.setImageResource(R.mipmap.ic_level4_s);
//                levelTopImageView.setVisibility(View.VISIBLE);
//                break;
//            case 5:
//                levelTopImageView.setImageResource(R.mipmap.ic_level5_s);
//                levelTopImageView.setVisibility(View.VISIBLE);
//                break;
//        }
//        if (UserStorage.getInstance().getIsVip() == 1) {
//            levelTopImageView.setVisibility(View.VISIBLE);
//            levelTopImageView.setImageResource(R.mipmap.ic_level_vip_s);
//
//        }
        //TODO:次数
        TextView mineTimesView = helper.getView(R.id.mine_times_view);
        //TODO:下一等级图标
        ImageView nextLevelImgView = helper.getView(R.id.next_level_img_view);
        //TODO:下一等级人数
        TextView ccExNumTextView = helper.getView(R.id.ccExNum_text_view);
        TextView next_level_text_view = helper.getView(R.id.next_level_text_view);
        if (UserStorage.getInstance().getViewNum()>=99999999 || UserStorage.getInstance().getIsVip() == 1) {
            nextLevelImgView.setVisibility(View.INVISIBLE);
            next_level_text_view.setVisibility(View.VISIBLE);
            ccExNumTextView.setVisibility(View.VISIBLE);
            mineTimesView.setText("无限");
            ccExNumTextView.setText("推广用户已超过");
        } else {
            next_level_text_view.setVisibility(View.INVISIBLE);
            nextLevelImgView.setVisibility(View.VISIBLE);
            int userNum = (UserStorage.getInstance().getViewNum()-UserStorage.getInstance().getUsedViewNum());
            mineTimesView.setText(userNum+"/"+UserStorage.getInstance().getViewNum());

            switch (UserStorage.getInstance().getNextSortNo()) {
                case 0:
                    nextLevelImgView.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    nextLevelImgView.setVisibility(View.VISIBLE);
                    nextLevelImgView.setImageResource(R.mipmap.ic_level1_s);
                    break;
                case 2:
                    nextLevelImgView.setVisibility(View.VISIBLE);
                    nextLevelImgView.setImageResource(R.mipmap.ic_level2_s);
                    break;
                case 3:
                    nextLevelImgView.setImageResource(R.mipmap.ic_level3_s);
                    nextLevelImgView.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    nextLevelImgView.setImageResource(R.mipmap.ic_level4_s);
                    nextLevelImgView.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    nextLevelImgView.setImageResource(R.mipmap.ic_level5_s);
                    nextLevelImgView.setVisibility(View.VISIBLE);
                    break;
            }

            ccExNumTextView.setText("下一等级还差"+UserStorage.getInstance().getCcExNum()+"人");
        }

        RelativeLayout tuiguangView =  helper.getView(R.id.tuigunang_view);
        tuiguangView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(UserStorage.getInstance().getUserType() == UserType.MARK_USER){
                    if (mMineTypeAdapterLisenter != null) {
                        mMineTypeAdapterLisenter.gotoPromote();
                    }
                }else {
                    AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .setTitle("提示")//设置对话框的标题
                            .setMessage("请先登录")//设置对话框的内容
                            //设置对话框的按钮
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mMineTypeAdapterLisenter.gotoLogin();
                                    dialog.dismiss();
                                }
                            }).create();
                    dialog.show();
                }
            }
        });

        userHeadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMineTypeAdapterLisenter != null) {
                    mMineTypeAdapterLisenter.gotoAcount();
                }
            }
        });
    }

    public interface MineTypeAdapterLisenter {
        //我的喜欢
        void gotoMyLike();
        //历史记录
        void gotoHistory();
        //我的缓存
        void gotoMyCache();
        //进入通知
        void gotoNotification();
        //vip
        void gotoVip();
        //提现
        void gotoWithDraw();
        //登录注册
        void gotoLogin();
        //火爆交流群
        void gotoHotQun();
        //推广
        void gotoPromote();
        //账户管理
        void gotoAcount();
        //意见反馈
        void feedBack();
        //进入视频详情
        void gotoDetail(HistoryBean historyBean);

        //进入视频详情
        void gotoCacheDetail(DownLoadInfo downLoadInfo);
    }
}
