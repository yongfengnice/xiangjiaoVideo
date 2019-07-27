package com.baby.app.modules.mine.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseMultiItemQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.util.GlideUtils;
import com.android.baselibrary.util.ScreenUtil;
import com.bumptech.glide.Glide;
import com.baby.app.R;
import com.baby.app.modules.mine.bean.PromoteBean;
import com.baby.app.modules.mine.bean.PromoteFuliBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/10.
 *
 */

public class PromoteTypeAdapter extends BaseMultiItemQuickAdapter<PromoteBean,BaseViewHolder> {

    private List<PromoteBean> promoteBeans;

    private List<PromoteFuliBean>promoteFuliBeanList = new ArrayList<>();

    private PromoteTypeAdapterLisenter mPromoteTypeAdapterLisenter;

    public void setmPromoteTypeAdapterLisenter(PromoteTypeAdapterLisenter mPromoteTypeAdapterLisenter) {
        this.mPromoteTypeAdapterLisenter = mPromoteTypeAdapterLisenter;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PromoteTypeAdapter(List<PromoteBean> data, Context mContext) {
        super(data);
        this.promoteBeans = data;

        //福利任务数据
//        PromoteFuliBean fuliBean1 = new PromoteFuliBean();
//        fuliBean1.setTitle("1.用户名注册");
//        fuliBean1.setSpanned(Html.fromHtml("<font size=\"14\" color=\"#6e6e6e\">用户名注册，每日观影次数</font>" +
//                "<font size=\"14\" color=\"#DBB185\">+2</font>"+
//                "<font size=\"14\" color=\"#6e6e6e\"><br/>用户名注册并绑定手机号，每日观影次数</font>"+
//                "<font size=\"14\" color=\"#DBB185\">+4</font>"));
//        fuliBean1.setIsTopHidden(true);
//        fuliBean1.setIsBottomHidden(false);

        PromoteFuliBean fuliBean2 = new PromoteFuliBean();
        fuliBean2.setTitle("1.手机号注册");
        fuliBean2.setSpanned(Html.fromHtml("<font size=\"14\" color=\"#6e6e6e\">手机号注册，每日观影次数</font>" +
                "<font size=\"14\" color=\"#FA7334\">+4</font>"+
                "<font size=\"14\" color=\"#6e6e6e\">（*为防止APP被封丢失您的推广信息，可通过注册账号绑定推广人数，强烈建议注册*）</font>"));
        fuliBean2.setIsTopHidden(true);
        fuliBean2.setIsBottomHidden(false);

        PromoteFuliBean fuliBean3 = new PromoteFuliBean();
        fuliBean3.setTitle("2.填写邀请码");
        fuliBean3.setSpanned(Html.fromHtml("<font size=\"14\" color=\"#6e6e6e\">注册并填写好友邀请码，每日观影次数</font>" +
                "<font size=\"14\" color=\"#FA7334\">+2</font>"));
        fuliBean3.setIsTopHidden(false);
        fuliBean3.setIsBottomHidden(false);

        PromoteFuliBean fuliBean4 = new PromoteFuliBean();
        fuliBean4.setTitle("3.保存二维码");
        fuliBean4.setSpanned(Html.fromHtml("<font size=\"14\" color=\"#6e6e6e\">保存推广二维码，每日观影次数</font>" +
                "<font size=\"14\" color=\"#FA7334\">+1</font>"));
        fuliBean4.setIsTopHidden(false);
        fuliBean4.setIsBottomHidden(true);

//        promoteFuliBeanList.add(fuliBean1);
        promoteFuliBeanList.add(fuliBean2);
        promoteFuliBeanList.add(fuliBean3);
        promoteFuliBeanList.add(fuliBean4);


        int size = promoteBeans.size();
        for (int i = 0; i < size; i++) {
            switch (promoteBeans.get(i).getItemType()) {
                case PromoteBean.PROMOT_ROW1:
                    addItemType(PromoteBean.PROMOT_ROW1, R.layout.promote_layout_1);
                    break;
                case PromoteBean.PROMOT_ROW2:
                    addItemType(PromoteBean.PROMOT_ROW2, R.layout.promote_layout_2);
                    break;
                case PromoteBean.PROMOT_ROW3:
                    addItemType(PromoteBean.PROMOT_ROW3, R.layout.promote_layout_3);
                    break;
                case PromoteBean.PROMOT_ROW4:
                    addItemType(PromoteBean.PROMOT_ROW4, R.layout.promote_layout_4);
                    break;
                case PromoteBean.PROMOT_ROW5:
                    addItemType(PromoteBean.PROMOT_ROW5, R.layout.promote_layout_5);
                    break;
                default:
                    break;
            }
        }
        this.mContext = mContext;

    }

    @Override
    protected void convert(BaseViewHolder helper, PromoteBean item) {

//
        switch (helper.getItemViewType()) {
            case PromoteBean.PROMOT_ROW1: {
                //头像
                ImageView headView = helper.getView(R.id.my_user_head_view);
                //当前等级
//                ImageView levelImg = helper.getView(R.id.p_left_level_img);
                //当前等级名称
//                TextView levelNameView = helper.getView(R.id.level_name_text_view);
                //手机号
                TextView phoneView = helper.getView(R.id.p_phone_text_view);
                //邀请码
                TextView inviteView = helper.getView(R.id.p_invite_text_view);
                //二维码btn
                ImageView erBtn = helper.getView(R.id.er_btn);
//                TextView next_cha_view = helper.getView(R.id.next_cha_view);
                //进度
                ProgressBar progressBar = helper.getView(R.id.progressBarHorizontal);
                //下一等级
                ImageView nextLevelImage = helper.getView(R.id.p_level_img_view);
                //下等级名称
                TextView nextLevelName = helper.getView(R.id.p_level_text_view);
                if (UserStorage.getInstance().getHeadpic()!=null) {

                    GlideUtils
                            .getInstance()
                            .LoadContextCircleBitmap(mContext,
                                    UserStorage.getInstance().getHeadpic(),
                                    headView,
                                    R.mipmap.ic_head_l,
                                    R.mipmap.ic_head_l);
                }

//                switch (UserStorage.getInstance().getSortNo()) {
//                    case 0:
//                        levelImg.setImageResource(R.mipmap.ic_level0);
//                        break;
//                    case 1:
//                        levelImg.setImageResource(R.mipmap.ic_level1_s);
//                        break;
//                    case 2:
//                        levelImg.setImageResource(R.mipmap.ic_level2_s);
//                        break;
//                    case 3:
//                        levelImg.setImageResource(R.mipmap.ic_level3_s);
//                        break;
//                    case 4:
//                        levelImg.setImageResource(R.mipmap.ic_level4_s);
//                        break;
//                    case 5:
//                        levelImg.setImageResource(R.mipmap.ic_level5_s);
//                        break;
//                }
//                levelNameView.setText(UserStorage.getInstance().getLevelName());
//                if (UserStorage.getInstance().getIsVip() == 1) {
//                    levelImg.setImageResource(R.mipmap.ic_level_vip_s);
////                    levelNameView.setText("VIP");
//                }

                if (UserStorage.getInstance().getNickName()!=null && UserStorage.getInstance().getNickName().length() > 0) {
                    phoneView.setText(UserStorage.getInstance().getNickName());
                } else {
                    if (UserStorage.getInstance().getPhone()!=null) {
                        phoneView.setText(UserStorage.getInstance().getPhone());
                    }
                }
                inviteView.setText("我的邀请码:"+UserStorage.getInstance().getExtensionCode());
                erBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPromoteTypeAdapterLisenter.gotoEr();
                    }
                });
                progressBar.setProgress((int)(UserStorage.getInstance().getPercentExNum()*100));
                switch (UserStorage.getInstance().getNextSortNo()) {
                    case 0:
                        nextLevelImage.setImageResource(R.mipmap.ic_level0);
                        break;
                    case 1:
                        nextLevelImage.setImageResource(R.mipmap.ic_level1_s);
                        break;
                    case 2:
                        nextLevelImage.setImageResource(R.mipmap.ic_level2_s);
                        break;
                    case 3:
                        nextLevelImage.setImageResource(R.mipmap.ic_level3_s);
                        break;
                    case 4:
                        nextLevelImage.setImageResource(R.mipmap.ic_level4_s);
                        break;
                    case 5:
                        nextLevelImage.setImageResource(R.mipmap.ic_level5_s);
                        break;
                }
                nextLevelName.setText(UserStorage.getInstance().getNexName());
//                next_cha_view.setText("下一等级还差"+UserStorage.getInstance().getCcExNum()+"人");
                RelativeLayout right_view_1 = helper.getView(R.id.right_view_1);
                RelativeLayout right_view_2 = helper.getView(R.id.right_view_2);
                if (UserStorage.getInstance().getViewNum()>=99999999||UserStorage.getInstance().getIsVip() == 1) {

                    right_view_1.setVisibility(View.VISIBLE);
                    right_view_2.setVisibility(View.INVISIBLE);
                } else {
                    right_view_1.setVisibility(View.INVISIBLE);
                    right_view_2.setVisibility(View.VISIBLE);
                }

            }
            break;
            case PromoteBean.PROMOT_ROW2: {
                TextView p_video_num_view = helper.getView(R.id.p_video_num_view);
                TextView p_video_s_num_view = helper.getView(R.id.p_video_s_num_view);
                if (UserStorage.getInstance().getViewNum()>=99999999 || UserStorage.getInstance().getIsVip() == 1) {
                    p_video_num_view.setText("无限");
                    p_video_s_num_view.setText("无限");
                } else {
                    int userNum = (UserStorage.getInstance().getViewNum()-UserStorage.getInstance().getUsedViewNum());
                    p_video_num_view.setText(userNum+"");
                    p_video_s_num_view.setText(UserStorage.getInstance().getViewNum()+"");
                }

            }
            break;
            case PromoteBean.PROMOT_ROW3: {
                TextView p_task_msg_view = helper.getView(R.id.p_task_msg_view);
                p_task_msg_view.setText(Html.fromHtml("<font size=\"14\" color=\"#6e6e6e\">每日点击广告</font>" +
                        "<font size=\"14\" color=\"#FA7334\">+1</font>"+
                "<font size=\"14\" color=\"#6e6e6e\">,次数不累计</font>"));

            }
            break;
            case PromoteBean.PROMOT_ROW4: {
                TextView pl_v1_text_view = helper.getView(R.id.pl_v1_sub_text_view);
                pl_v1_text_view.setText(Html.fromHtml("<font size=\"14\" color=\"#6e6e6e\">推广</font>" +
                        "<font size=\"14\" color=\"#FA7334\">1</font>"+
                        "<font size=\"14\" color=\"#6e6e6e\">人-每日观影次数</font>"+
                        "<font size=\"14\" color=\"#FA7334\">+2</font>"+
                        "<font size=\"14\" color=\"#6e6e6e\">，缓存次数</font>"+
                        "<font size=\"14\" color=\"#FA7334\">+1</font>"));
                TextView pl_v2_text_view = helper.getView(R.id.pl_v2_sub_text_view);
                pl_v2_text_view.setText(Html.fromHtml("<font size=\"14\" color=\"#6e6e6e\">推广</font>" +
                        "<font size=\"14\" color=\"#FA7334\">3</font>"+
                        "<font size=\"14\" color=\"#6e6e6e\">人-每日观影次数</font>"+
                        "<font size=\"14\" color=\"#FA7334\">+5</font>"+
                        "<font size=\"14\" color=\"#6e6e6e\">，缓存次数</font>"+
                        "<font size=\"14\" color=\"#FA7334\">+3</font>"));
                TextView pl_v3_text_view = helper.getView(R.id.pl_v3_sub_text_view);
                pl_v3_text_view.setText(Html.fromHtml("<font size=\"14\" color=\"#6e6e6e\">推广</font>" +
                        "<font size=\"14\" color=\"#FA7334\">10</font>"+
                        "<font size=\"14\" color=\"#6e6e6e\">人-每日观影次数</font>"+
                        "<font size=\"14\" color=\"#FA7334\">+30</font>"+
                        "<font size=\"14\" color=\"#6e6e6e\">，缓存次数</font>"+
                        "<font size=\"14\" color=\"#FA7334\">+10</font>"));
                TextView pl_v4_text_view = helper.getView(R.id.pl_v4_sub_text_view);
                pl_v4_text_view.setText(Html.fromHtml("<font size=\"14\" color=\"#6e6e6e\">推广</font>" +
                        "<font size=\"14\" color=\"#FA7334\">30</font>"+
                        "<font size=\"14\" color=\"#6e6e6e\">人-每日观影次数</font>"+
                        "<font size=\"14\" color=\"#FA7334\">+50</font>"+
                        "<font size=\"14\" color=\"#6e6e6e\">，缓存次数</font>"+
                        "<font size=\"14\" color=\"#FA7334\">+30</font>"));
                TextView pl_v5_text_view = helper.getView(R.id.pl_v5_sub_text_view);
                pl_v5_text_view.setText(Html.fromHtml("<font size=\"14\" color=\"#6e6e6e\">推广</font>" +
                        "<font size=\"14\" color=\"#FA7334\">50</font>"+
                        "<font size=\"14\" color=\"#6e6e6e\">人-每日观影次数</font>"+
                        "<font size=\"14\" color=\"#FA7334\">+无限</font>"+
                        "<font size=\"14\" color=\"#6e6e6e\">，缓存次数</font>"+
                        "<font size=\"14\" color=\"#FA7334\">+50</font>"));
            }
            break;
            case PromoteBean.PROMOT_ROW5: {

                final RecyclerView mRecyclerView = helper.getView(R.id.p_5_recycler_view);
                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(OrientationHelper.VERTICAL);
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setAdapter(new PromoteFuliAdapter(R.layout.item_fuli_task_layout, promoteFuliBeanList));

            }
            break;
            default:
                break;
        }
    }

    public interface PromoteTypeAdapterLisenter {
        void gotoEr();
    }
}
