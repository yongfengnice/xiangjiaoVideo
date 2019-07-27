package com.baby.app.modules.video.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.page.CommonWebViewActivity;
import com.android.baselibrary.recycleradapter.BaseMultiItemQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.HomeBannerBean;
import com.android.baselibrary.service.bean.home.HomeListBean;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.service.bean.video.VideoCommentBean;
import com.android.baselibrary.service.bean.video.VideoDetailBean;
import com.android.baselibrary.service.bean.video.VideoLikeBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.util.GlideUtils;
import com.android.baselibrary.util.ScreenUtil;
import com.android.baselibrary.widget.RefreshLayout;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.baby.app.R;
import com.baby.app.advertise.AdvertiseActivity;
import com.baby.app.modules.video.bean.VideoTypeBean;
import com.baby.app.service.DownInfoModel;
import com.baby.app.service.DownLoadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/2.
 *
 */

public class VideoTypeAdapter extends BaseMultiItemQuickAdapter<VideoTypeBean,BaseViewHolder> {

    private Context mContext;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private DownInfoModel model = new DownInfoModel();
    private List<VideoCommentBean.Data>videoCommentBeanList = new ArrayList<>();

    private List<VideoLikeBean>videoLikeBeanList = new ArrayList<>();

    private List<VideoTypeBean> videoTypeBeans;

    private VideoDetailBean mVideoDetailBean;

    private VideoLikeAdapter mVideoLikeAdapter;
    private VideoCommentAdapter mVideoCommentAdapter;

    private VideoTypeAdapterLisenter mVideoTypeAdapterLisenter;

    private boolean isMore = false;
    public VideoLikeAdapter getmVideoLikeAdapter() {
        return mVideoLikeAdapter;
    }

    public void setmVideoTypeAdapterLisenter(VideoTypeAdapterLisenter mVideoTypeAdapterLisenter) {
        this.mVideoTypeAdapterLisenter = mVideoTypeAdapterLisenter;
    }

    public VideoTypeAdapter(List<VideoTypeBean> data, VideoDetailBean videoDetailBean, Context context) {
        super(data);
        this.videoTypeBeans = data;
        this.mVideoDetailBean = videoDetailBean;


        int size = videoTypeBeans.size();
        for (int i = 0; i < size; i++) {
            switch (videoTypeBeans.get(i).getItemType()) {
                case VideoTypeBean.LAYOUT_VIDEO_1:
                    addItemType(VideoTypeBean.LAYOUT_VIDEO_1, R.layout.item_video_1);
                    break;
                case VideoTypeBean.LAYOUT_VIDEO_2:
                    if (mVideoDetailBean.getData().getBannerList().size() > 0) {
                        addItemType(VideoTypeBean.LAYOUT_VIDEO_2, R.layout.item_video_2);
                    }
                    break;
                case VideoTypeBean.LAYOUT_VIDEO_3:
                    addItemType(VideoTypeBean.LAYOUT_VIDEO_3, R.layout.item_video_3);
                    break;
                case VideoTypeBean.LAYOUT_VIDEO_4:
                    addItemType(VideoTypeBean.LAYOUT_VIDEO_4, R.layout.item_video_4);

                    break;
                case VideoTypeBean.LAYOUT_VIDEO_5:
                    addItemType(VideoTypeBean.LAYOUT_VIDEO_5, R.layout.item_video_5);
                    break;

                default:
                    break;
            }
        }

        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, final VideoTypeBean item) {
        switch (helper.getItemViewType()) {
            case VideoTypeBean.LAYOUT_VIDEO_1: {
               layoutReresh1(helper);
            }
            break;
            case VideoTypeBean.LAYOUT_VIDEO_2: {
                layoutReresh2(helper);
            }
            break;
            case VideoTypeBean.LAYOUT_VIDEO_3: {
               layoutRefresh3(helper);
            }
            break;

            case VideoTypeBean.LAYOUT_VIDEO_4: {

                layoutRefresh4(helper);
            }
            break;
            case VideoTypeBean.LAYOUT_VIDEO_5: {


                TextView video_comment_view2 = helper.getView(R.id.video_comment_view2);
                video_comment_view2.setText(mVideoDetailBean.getData().getVideoCommentNum()+"条");
                final RecyclerView mRecyclerView = helper.getView(R.id.v_comment_recycler_view);
                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(OrientationHelper.VERTICAL);
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.bottom = ScreenUtil.dip2px(mContext, 15);
                    }
                });
                mVideoCommentAdapter = new VideoCommentAdapter(R.layout.item_video_comment_layout, videoCommentBeanList);
                mRecyclerView.setAdapter(mVideoCommentAdapter);
            }
            break;
            default:
                break;
        }
    }

    public void refershComment(VideoCommentBean videoCommentBean){
        videoCommentBeanList.clear();
        videoCommentBeanList.addAll(videoCommentBean.getData());
        mVideoCommentAdapter.notifyDataSetChanged();

    }

    private void layoutReresh1(BaseViewHolder helper){
        if (mVideoDetailBean != null) {
            TextView videoNameView = helper.getView(R.id.video_name_text_view);
            TextView timeView = helper.getView(R.id.video_left_view);
            TextView video_intruduce_view = helper.getView(R.id.video_intruduce_view);
            RelativeLayout v_introduce_view = helper.getView(R.id.v_introduce_view);
            timeView.setText(mVideoDetailBean.getData().getPushTime()+" . "+mVideoDetailBean.getData().getPlayNum()+"次播放");
            videoNameView.setText(mVideoDetailBean.getData().getVideoName());
            RelativeLayout video_submit_btn = helper.getView(R.id.video_submit_btn);

            TextView video_comment_view = helper.getView(R.id.video_comment_view);
            final ImageView left_zan = helper.getView(R.id.left_zan);
            final ImageView right_zan = helper.getView(R.id.right_zan);
            RelativeLayout left_zan_back = helper.getView(R.id.left_zan_back);
            RelativeLayout right_zan_back = helper.getView(R.id.right_zan_back);
            TextView video_zan_count = helper.getView(R.id.video_zan_count);
            video_zan_count.setText(mVideoDetailBean.getData().getCent()+"觉得很赞");
            video_intruduce_view.setText(mVideoDetailBean.getData().getBriefContent());
            video_comment_view.setText("热门评论"+" ("+mVideoDetailBean.getData().getVideoCommentNum()+")");
            if (mVideoDetailBean.getData().getIsLike().equals("1")) {
                left_zan.setImageResource(R.mipmap.praise_press);
                right_zan.setImageResource(R.mipmap.unpraise_unpress);
            } else if (mVideoDetailBean.getData().getIsLike().equals("0")) {
                left_zan.setImageResource(R.mipmap.praise_unpress);
                right_zan.setImageResource(R.mipmap.unpraise_unpress);
            } else if (mVideoDetailBean.getData().getIsLike().equals("2")) {
                left_zan.setImageResource(R.mipmap.unpraise_unpress);
                right_zan.setImageResource(R.mipmap.unpraise_press);
            }

            left_zan_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int isLike = Integer.parseInt(mVideoDetailBean.getData().getIsLike());
                    if (isLike==0) {
                        left_zan.setImageResource(R.mipmap.praise_press);
                        right_zan.setImageResource(R.mipmap.unpraise_unpress);
                        if (mVideoTypeAdapterLisenter!=null) {
                            mVideoTypeAdapterLisenter.setZan();
                        }
                        mVideoDetailBean.getData().setIsLike("1");
                    }
                }
            });

            right_zan_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int isLike = Integer.parseInt(mVideoDetailBean.getData().getIsLike());
                    if (isLike==0) {
                        left_zan.setImageResource(R.mipmap.praise_unpress);
                        right_zan.setImageResource(R.mipmap.unpraise_press);
                        if (mVideoTypeAdapterLisenter!=null) {
                            mVideoTypeAdapterLisenter.unSetZan();
                        }
                        mVideoDetailBean.getData().setIsLike("2");
                    }
                }
            });
            video_submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mVideoTypeAdapterLisenter!=null) {
                        mVideoTypeAdapterLisenter.submitQuestion();
                    }
                }
            });
            v_introduce_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mVideoTypeAdapterLisenter!=null) {
                        mVideoTypeAdapterLisenter.showIntroduce(mVideoDetailBean.getData().getBriefContent());
                    }
                }
            });
            ProgressBar progressBar = helper.getView(R.id.zan_present);
            String cent = mVideoDetailBean.getData().getCent().replace("%","");
            progressBar.setProgress((int) Float.parseFloat(cent));

            RelativeLayout video_like_btn = helper.getView(R.id.video_like_btn);
            final ImageView video_like_img = helper.getView(R.id.video_like_img);
            if (mVideoDetailBean.getData().getIsCare().equals("0")) {
                video_like_img.setImageResource(R.mipmap.favor_nopress);
            } else {
                video_like_img.setImageResource(R.mipmap.favor_press);
            }
            RelativeLayout video_down_btn = helper.getView(R.id.video_down_btn);
            RelativeLayout video_share_btn = helper.getView(R.id.video_share_btn);
            video_like_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mVideoDetailBean.getData().getIsCare().equals("0")) {
                        if (mVideoTypeAdapterLisenter!=null) {
                            mVideoTypeAdapterLisenter.onLike();
                        }
                        if (UserStorage.getInstance().isLogin() && UserStorage.getInstance().getUserType() == UserType.MARK_USER) {
                            mVideoDetailBean.getData().setIsCare("1");
                            video_like_img.setImageResource(R.mipmap.favor_press);
                        }
                    }
                }
            });


            video_down_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mVideoTypeAdapterLisenter!=null) {
                        if (model.findVideoByDownId(String.valueOf(mVideoDetailBean.getData().getId()))==null) {
                            mVideoTypeAdapterLisenter.onDown();
                        }

                    }
                }
            });

            video_share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mVideoTypeAdapterLisenter!=null) {
                        mVideoTypeAdapterLisenter.onShare();
                    }
                }
            });

        }

        ImageView video_down_img = helper.getView(R.id.video_down_img);
        if (model.findVideoByDownId(String.valueOf(mVideoDetailBean.getData().getId()))!=null) {
            video_down_img.setImageResource(R.mipmap.down_d);
        } else {
            video_down_img.setImageResource(R.mipmap.down_d);
        }
    }

    private void layoutReresh2(BaseViewHolder helper) {
        if (mVideoDetailBean.getData().getBannerList().size() > 0) {
            ConvenientBanner convenientBanner = helper.getView(R.id.video_convenientBanner);
            List<String> data_banner_string = new ArrayList<>();
            final List<HomeBannerBean>bannerBeanList = mVideoDetailBean.getData().getBannerList();
            for (int position = 0; position < bannerBeanList.size(); position++){
                HomeBannerBean bannerBean = bannerBeanList.get(position);
                data_banner_string.add(bannerBean.getPicUrl());
            }

            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, data_banner_string);

            convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    HomeBannerBean tempBannerBean = mVideoDetailBean.getData().getBannerList().get(position);
                    if (mVideoTypeAdapterLisenter!=null) {
                        mVideoTypeAdapterLisenter.onBannerClick(tempBannerBean);
                    }
                }
            });
            if (data_banner_string.size() > 1) {
                convenientBanner.setCanLoop(true);
            } else {
                convenientBanner.setCanLoop(false);
            }
            convenientBanner.setPageIndicator(new int[]{R.mipmap.icon_home_banner_a, R.mipmap.icon_home_banner_aa});
            convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            convenientBanner.startTurning(5000);
            convenientBanner.setManualPageable(true);
            if (convenientBanner.getViewPager() != null) {
                convenientBanner.getViewPager().setClipToPadding(false);
                convenientBanner.getViewPager().setClipChildren(false);
                try {
                    ((RelativeLayout) convenientBanner.getViewPager().getParent()).setClipChildren(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                convenientBanner.getViewPager().setOffscreenPageLimit(3);
            }
        }
    }

    private void layoutRefresh3(BaseViewHolder helper){
        RoundedImageView v_girl_img_view = helper.getView(R.id.v_girl_img_view);
        GlideUtils
                .getInstance()
                .LoadContextCircleBitmap(mContext,
                        mVideoDetailBean.getData().getHeadPic(),
                        v_girl_img_view,
                        R.mipmap.ic_head_l,
                        R.mipmap.ic_head_l);

        TextView v_name_text_view = helper.getView(R.id.v_name_text_view);
        v_name_text_view.setText(mVideoDetailBean.getData().getStarName());
        TextView v_info_text_view = helper.getView(R.id.v_info_text_view);
        v_info_text_view.setText(mVideoDetailBean.getData().getBriefContext());
        TextView video_start_count = helper.getView(R.id.video_start_count);
        video_start_count.setText(mVideoDetailBean.getData().getStarVideoNum()+"部电影");

        final RecyclerView mRecyclerView = helper.getView(R.id.v_n_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                if (pos ==0) {
                    outRect.left = ScreenUtil.dip2px(mContext, 0);
                    outRect.right = ScreenUtil.dip2px(mContext, 14);
                } else {
                    outRect.left = ScreenUtil.dip2px(mContext, 0);
                    outRect.right = ScreenUtil.dip2px(mContext, 14);
                }
            }
        });
        VideoNvAdapter videoNvAdapter = new VideoNvAdapter(R.layout.item_video_nv_layout, mVideoDetailBean.getData().getStarVideoList());
        mRecyclerView.setAdapter(videoNvAdapter);
        videoNvAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mVideoTypeAdapterLisenter != null) {
                    mVideoTypeAdapterLisenter.onStarListClick(mVideoDetailBean.getData().getStarVideoList().get(position));
                }
            }
        });
    }

    private void layoutRefresh4(BaseViewHolder helper) {
        final LinearLayout video_layout_4 = helper.getView(R.id.video_layout_4);
        final RelativeLayout video_more_btn = helper.getView(R.id.video_more_btn);

        final TextView v_n_more_text_view = helper.getView(R.id.v_n_more_text_view);
        final ImageView v_n_more_img = helper.getView(R.id.v_n_more_img);

        final RecyclerView mRecyclerView = helper.getView(R.id.v_like_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ScreenUtil.dip2px(mContext, 12);
            }
        });
        if (mVideoDetailBean.getData().getLikeVideoList().size() <= 3) {
            video_more_btn.setVisibility(View.GONE);
            videoLikeBeanList.addAll(mVideoDetailBean.getData().getLikeVideoList());
        } else {
            video_more_btn.setVisibility(View.VISIBLE);
            for (int i = 0; i <3; i++) {
                VideoLikeBean videoLikeBean = mVideoDetailBean.getData().getLikeVideoList().get(i);
                videoLikeBeanList.add(videoLikeBean);
            }
        }

        mVideoLikeAdapter = new VideoLikeAdapter(R.layout.item_video_guess_layout, videoLikeBeanList);
        mRecyclerView.setAdapter(mVideoLikeAdapter);
        mVideoLikeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (videoLikeBeanList!=null &&videoLikeBeanList.size() > 0) {
                    mVideoTypeAdapterLisenter.gotoDetail(videoLikeBeanList.get(position));
                }
            }
        });
        video_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMore) {
//                    video_layout_4.setFocusable(true);
                    v_n_more_text_view.setText("收起");
                    v_n_more_img.setBackgroundResource(R.mipmap.ic_av_less);
                    videoLikeBeanList.clear();
                    mVideoLikeAdapter.addData(mVideoDetailBean.getData().getLikeVideoList());
                } else {
//                    video_layout_4.setFocusable(true);
                    v_n_more_text_view.setText("查看更多");
                    v_n_more_img.setBackgroundResource(R.mipmap.ic_av_more);
                    videoLikeBeanList.clear();
                    for (int i = 0; i <3; i++) {
                        VideoLikeBean videoLikeBean = mVideoDetailBean.getData().getLikeVideoList().get(i);
                        videoLikeBeanList.add(videoLikeBean);
                    }
                    mVideoLikeAdapter.notifyDataSetChanged();

                }
                isMore = !isMore;

            }
        });

    }

    // banner加载图片适配
    class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.start_banner_item, null);
            imageView = (ImageView) view.findViewById(R.id.iv_banner_img);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            // 图片
            GlideUtils
                    .getInstance()
                    .LoadContextRoundAndCeterCropBitmapDefault(mContext,
                            data,
                            imageView,
                            R.mipmap.loading,
                            R.mipmap.loading,
                            GlideUtils.LOAD_BITMAP, 0);
        }
    }

    public interface VideoTypeAdapterLisenter {

        void sendComment();
        void setZan();
        void unSetZan();
        void submitQuestion();
        void showIntroduce(String introduce);
        void onLike();
        void onDown();
        void onShare();
        void onBannerClick(HomeBannerBean bannerBean);
        void onStarListClick(HomeListBean homeListBean);
        void gotoDetail(VideoLikeBean likeBean);
    }
}
