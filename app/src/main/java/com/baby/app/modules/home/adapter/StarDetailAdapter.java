package com.baby.app.modules.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseMultiItemQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.home.HomeListBean;
import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.util.GlideUtils;
import com.android.baselibrary.util.ScreenUtil;
import com.baby.app.R;
import com.baby.app.modules.home.bean.StartDetailTypeBean;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/16.
 */

public class StarDetailAdapter extends BaseMultiItemQuickAdapter<StartDetailTypeBean,BaseViewHolder> {

    private Context mContext;
    private List<StartDetailTypeBean>typeBeanList;
    private HomeStarBean mHomeStarBean;
    private List<DetailListBean.Data>mHomeListBeans;
    private StarDetailAdapterLisenter mStarDetailAdapterLisenter;
    private boolean first1 = true;
    private boolean first2 = true;
    private boolean first3 = true;

    private StarDetailListAdapter mStarDetailListAdapter;

    public void setmStarDetailAdapterLisenter(StarDetailAdapterLisenter mStarDetailAdapterLisenter) {
        this.mStarDetailAdapterLisenter = mStarDetailAdapterLisenter;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public StarDetailAdapter(Context mContext,
                             List<DetailListBean.Data>homeListBeans,
                             HomeStarBean mHomeStarBean,
                             List<StartDetailTypeBean> data,
                             boolean first1,
                             boolean first2,
                             boolean first3) {
        super(data);
        this.first1 = first1;
        this.first2 = first2;
        this.first3 = first3;
        this.typeBeanList = data;
        this.mHomeStarBean = mHomeStarBean;
        this.mHomeListBeans = homeListBeans;
        int size = typeBeanList.size();
        for (int i = 0; i < size; i++) {
            switch (typeBeanList.get(i).getItemType()) {
                case StartDetailTypeBean.LAYOUT_STAR_DEAIL_1:
                    addItemType(StartDetailTypeBean.LAYOUT_STAR_DEAIL_1, R.layout.star_detail_layout_1);
                    break;
                case StartDetailTypeBean.LAYOUT_STAR_DEAIL_2:
                    addItemType(StartDetailTypeBean.LAYOUT_STAR_DEAIL_2, R.layout.star_detail_layout_2);
                    break;
                case StartDetailTypeBean.LAYOUT_STAR_DEAIL_3:
                    addItemType(StartDetailTypeBean.LAYOUT_STAR_DEAIL_3, R.layout.star_detail_layout_3);
                    break;
                default:
                    break;
            }
        }

        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, StartDetailTypeBean item) {
        switch (helper.getItemViewType()) {
            case StartDetailTypeBean.LAYOUT_STAR_DEAIL_1: {
                if (first1) {
                    first1 = false;
                    RelativeLayout back_view = helper.getView(R.id.back_view);
                    back_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mStarDetailAdapterLisenter.back();
                        }
                    });
                    ImageView imageView = helper.getView(R.id.star_image_view);
                    TextView countView = helper.getView(R.id.star_count_view);
                    TextView nameView = helper.getView(R.id.star_name_view);
                    TextView dataView = helper.getView(R.id.star_data_view);
                    GlideUtils
                            .getInstance()
                            .LoadContextCircleBitmap(mContext,
                                    mHomeStarBean.getHeadpic(),
                                    imageView,
                                    R.mipmap.ic_head_l,
                                    R.mipmap.ic_head_l);
                    countView.setText(mHomeStarBean.getVideoNum()+"部影片");
                    nameView.setText(mHomeStarBean.getName());
//                    dataView.setText("身高:"+mHomeStarBean.getHeightNum()+" 三围:"+mHomeStarBean.getBwh()+" 罩杯:"+mHomeStarBean.getCupName());
                    dataView.setText("身高:"+mHomeStarBean.getHeightNum());
                }
            }
            break;
            case StartDetailTypeBean.LAYOUT_STAR_DEAIL_2: {
                if (first2) {
                    first2 = false;
                    final TextView textView = helper.getView(R.id.star_content_view);
                    textView.setText(mHomeStarBean.getBriefContext());
                    final RelativeLayout star_more_btn = helper.getView(R.id.star_more_btn);
                    star_more_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textView.setMaxLines(100);
                            star_more_btn.setVisibility(View.INVISIBLE);
                        }
                    });
                }

            }
            break;
            case StartDetailTypeBean.LAYOUT_STAR_DEAIL_3: {

                if (first3) {
                    first3 = false;
                    final RelativeLayout star_new_btn = helper.getView(R.id.star_new_btn);
                    final TextView star_new_btn_text = helper.getView(R.id.star_new_btn_text);
                    final RelativeLayout star_more_btn = helper.getView(R.id.star_more_btn);
                    final TextView star_more_btn_text = helper.getView(R.id.star_more_btn_text);


                    star_new_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            star_new_btn.setBackgroundResource(R.drawable.star_detail_selected);
                            star_more_btn.setBackgroundResource(R.drawable.star_detail_nomal);
                            star_new_btn_text.setTextColor(Color.parseColor("#FFFA7334"));
                            star_more_btn_text.setTextColor(Color.parseColor("#FF000000"));
                            if (mStarDetailAdapterLisenter !=null) {
                                mStarDetailAdapterLisenter.setNewVideo();
                            }
                        }
                    });
                    star_more_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            star_new_btn.setBackgroundResource(R.drawable.star_detail_nomal);
                            star_more_btn.setBackgroundResource(R.drawable.star_detail_selected);
                            star_more_btn_text.setTextColor(Color.parseColor("#FFFA7334"));
                            star_new_btn_text.setTextColor(Color.parseColor("#FF000000"));
                            if (mStarDetailAdapterLisenter !=null) {
                                mStarDetailAdapterLisenter.setMoreVideo();
                            }
                        }
                    });


                    final RecyclerView mRecyclerView = helper.getView(R.id.star_detial_recycler_view);
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    manager.setOrientation(OrientationHelper.VERTICAL);
                    mRecyclerView.setLayoutManager(manager);
                    mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);

                            outRect.bottom = ScreenUtil.dip2px(mContext, 10);
                        }
                    });
                    mStarDetailListAdapter = new StarDetailListAdapter(R.layout.item_star_detail, mHomeListBeans);
                    mStarDetailListAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            mStarDetailAdapterLisenter.onItemClick(mHomeListBeans.get(position));
                        }
                    });
                    mRecyclerView.setAdapter(mStarDetailListAdapter);
                } else {
                    mStarDetailListAdapter.notifyDataSetChanged();
                }


            }
            break;
        }
    }

    public void refresh(List<DetailListBean.Data>list){
        mStarDetailListAdapter.addData(list);
//        mStarDetailListAdapter.notifyDataSetChanged();
    }

    public interface StarDetailAdapterLisenter{
        void setNewVideo();
        void setMoreVideo();
        void onItemClick(DetailListBean.Data starBean);
        void back();
    }
}
