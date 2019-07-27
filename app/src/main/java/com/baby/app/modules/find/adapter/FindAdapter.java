package com.baby.app.modules.find.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.find.FindBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;
import com.baby.app.service.DownInfoModel;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/29.
 */

public class FindAdapter extends BaseQuickAdapter<FindBean.Data,BaseViewHolder> {

    private DownInfoModel model = new DownInfoModel();
    private FindAdapterLisenter mFindAdapterLisenter;

    public void setmFindAdapterLisenter(FindAdapterLisenter mFindAdapterLisenter) {
        this.mFindAdapterLisenter = mFindAdapterLisenter;
    }

    public FindAdapter(int layoutResId, @Nullable List<FindBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final FindBean.Data item) {

        ImageView video_down_img = helper.getView(R.id.video_down_img);
        if (model.findVideoByDownId(String.valueOf(item.getId()))!=null) {
            video_down_img.setImageResource(R.mipmap.download_press);
        } else {
            video_down_img.setImageResource(R.mipmap.download_nopress);
        }

        ImageView imageView = helper.getView(R.id.videoCover_img_view);
        TextView countTextView = helper.getView(R.id.find_count_view);
        GlideUtils
                .getInstance()
                .LoadNewContextBitmap(mContext,
                        item.getVideoCover(),
                        imageView,
                        R.mipmap.video_cover,
                        R.mipmap.video_cover,
                        GlideUtils.LOAD_BITMAP);
        TextView nameTextView = helper.getView(R.id.find_name_text_view);
        nameTextView.setText(item.getVideoName());
        countTextView.setText(item.getPlayNum()+"次播放");

        RelativeLayout video_like_btn = helper.getView(R.id.video_like_btn);
        final ImageView video_like_img = helper.getView(R.id.video_like_img);
        if (item.getIsCare().equals("0")) {
            video_like_img.setImageResource(R.mipmap.favor_nopress);
        } else {
            video_like_img.setImageResource(R.mipmap.favor_press);
        }
        RelativeLayout video_down_btn = helper.getView(R.id.video_down_btn);
        RelativeLayout video_share_btn = helper.getView(R.id.video_share_btn);
        video_like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getIsCare().equals("0")) {
                    if (mFindAdapterLisenter!=null) {
                        mFindAdapterLisenter.onLike(item);
                    }
                    if (UserStorage.getInstance().isLogin() && UserStorage.getInstance().getUserType() == UserType.MARK_USER) {
                        item.setIsCare("1");
                        video_like_img.setImageResource(R.mipmap.favor_press);
                    }

                }
            }
        });

        video_down_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFindAdapterLisenter!=null) {
                    if (model.findVideoByDownId(String.valueOf(item.getId()))==null) {
                        mFindAdapterLisenter.onDown(item);
                    }

                }
            }
        });

        video_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFindAdapterLisenter!=null) {
                    mFindAdapterLisenter.onShare(item);
                }
            }
        });

    }

    public interface FindAdapterLisenter {

        void onLike(FindBean.Data data);
        void onDown(FindBean.Data data);
        void onShare(FindBean.Data data);
    }
}
