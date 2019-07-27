package com.baby.app.modules.home.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.baselibrary.recycleradapter.BaseMultiItemQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.service.bean.home.HomeClassBean;
import com.android.baselibrary.service.bean.home.HomeClassCollectBean;
import com.android.baselibrary.service.bean.home.HomeDataBean;
import com.android.baselibrary.service.bean.home.HomeListBean;
import com.android.baselibrary.service.bean.home.HomeStarBean;
import com.android.baselibrary.util.ScreenUtil;
import com.baby.app.R;
import com.baby.app.modules.home.bean.HomeTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongqianggeng on 2018/9/22.
 *  首页布局
 */

public class HomeTypeAdapter extends BaseMultiItemQuickAdapter<HomeTypeBean,BaseViewHolder> {

    private Context mContext;
    private List<HomeTypeBean> homeTypeBeans;

    private HomeDataBean homeDataBean;

    private HomeTypeAdapterLisenter mHomeTypeAdapterLisenter;

    public void setmHomeTypeAdapterLisenter(HomeTypeAdapterLisenter homeTypeAdapterLisenter) {
        this.mHomeTypeAdapterLisenter = homeTypeAdapterLisenter;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    public HomeTypeAdapter(List data,HomeDataBean dataBean,Context context) {
        super(data);

        this.homeTypeBeans = data;
        if (dataBean == null) {
            this.homeDataBean = new HomeDataBean();
        } else {
            this.homeDataBean = dataBean;
        }

        int size = homeTypeBeans.size();
        for (int i = 0; i < size; i++) {
            switch (homeTypeBeans.get(i).getItemType()) {
                case HomeTypeBean.LAYOUT_CLASS:
                    addItemType(HomeTypeBean.LAYOUT_CLASS, R.layout.home_class_layout);
                    break;
                case HomeTypeBean.LAYOUT_NEW_LIST:
                    addItemType(HomeTypeBean.LAYOUT_NEW_LIST, R.layout.home_list_layout);
                    break;
                case HomeTypeBean.LAYOUT_HOT_LIST:
                    addItemType(HomeTypeBean.LAYOUT_HOT_LIST, R.layout.home_list_layout);
                    break;
                case HomeTypeBean.LAYOUT_MAN_LIST:
                    addItemType(HomeTypeBean.LAYOUT_MAN_LIST, R.layout.home_man_list_layout);
                    break;
                default:
                    break;
            }
        }

        if (homeDataBean.getData()!=null && homeDataBean.getData().getClassifyListCollect() != null) {
            List<HomeClassCollectBean>homeClassCollectBeanList = homeDataBean.getData().getClassifyListCollect();
            for (int i = 0; i < homeClassCollectBeanList.size(); i++) {
                HomeClassCollectBean homeClassCollectBean = homeClassCollectBeanList.get(i);
                HomeTypeBean homeTypeBean = new HomeTypeBean(homeClassCollectBean.getId());
                homeTypeBean.setHomeClassCollectBean(homeClassCollectBean);
                homeTypeBeans.add(homeTypeBean);
                addItemType(homeClassCollectBean.getId(), R.layout.home_list_layout);
            }
        }


        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


    @Override
    protected void convert(BaseViewHolder helper, final HomeTypeBean item) {

        switch (helper.getItemViewType()) {
            case HomeTypeBean.LAYOUT_CLASS: {
                // 功能区域
                if (homeDataBean !=null && homeDataBean.getData()!=null && homeDataBean.getData().getClassifyList() != null) {
                    final RecyclerView mRecyclerView = helper.getView(R.id.recycler_view);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                    mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);
                            int pos = parent.getChildAdapterPosition(view);
                            if (pos/4 == 0) {
                                outRect.bottom = ScreenUtil.dip2px(mContext, 14);
                            } else {
                                outRect.bottom = ScreenUtil.dip2px(mContext, 10);
                            }

                        }
                    });
                    HomeClassAdpter classAdpter = new HomeClassAdpter(R.layout.item_home_class_layout, homeDataBean.getData().getClassifyList());
                    //TODO:事件
                    classAdpter.setmHomeClassAdpterLisenter(new HomeClassAdpter.HomeClassAdpterLisenter() {
                        @Override
                        public void onItemClick(HomeClassBean classBean) {
                            if (mHomeTypeAdapterLisenter != null) {
                                mHomeTypeAdapterLisenter.onClassClick(classBean);
                            }
                        }
                    });
                    mRecyclerView.setAdapter(classAdpter);
                }

            }
                break;
            case HomeTypeBean.LAYOUT_NEW_LIST: {
                TextView textView = helper.getView(R.id.more_text_view);
                textView.setText("最新片源");
                if (homeDataBean !=null && homeDataBean.getData()!=null && homeDataBean.getData().getNewVideoList() != null) {

                    final RecyclerView mRecyclerView = helper.getView(R.id.new_recycler_view);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);
                            int pos = parent.getChildAdapterPosition(view);
                            if (pos%2 ==0) {
                                outRect.left = ScreenUtil.dip2px(mContext, 10);
                                outRect.right = ScreenUtil.dip2px(mContext, 4);
                            } else {
                                outRect.left = ScreenUtil.dip2px(mContext, 4);
                                outRect.right = ScreenUtil.dip2px(mContext, 10);
                            }
                            outRect.bottom = ScreenUtil.dip2px(mContext, 10);
                        }
                    });
                    HomeListAdapter homeListAdapter = new HomeListAdapter(R.layout.item_home_list_layout, homeDataBean.getData().getNewVideoList());
                    mRecyclerView.setAdapter(homeListAdapter);
                    //TODO:事件
                    helper.getView(R.id.more_back_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mHomeTypeAdapterLisenter != null) {
                                mHomeTypeAdapterLisenter.onNewMoreMovies();
                            }
                        }
                    });

                    homeListAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            HomeListBean listBean = homeDataBean.getData().getNewVideoList().get(position);
                            if (mHomeTypeAdapterLisenter != null) {
                                mHomeTypeAdapterLisenter.gotoVideo(listBean);
                            }
                        }
                    });
                }
            }
                break;

            case HomeTypeBean.LAYOUT_HOT_LIST: {
                TextView textView = helper.getView(R.id.more_text_view);
                textView.setText("重磅热播");
                if (homeDataBean !=null && homeDataBean.getData()!=null && homeDataBean.getData().getMostVideoList() != null) {
                    final RecyclerView mRecyclerView = helper.getView(R.id.new_recycler_view);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);
                            int pos = parent.getChildAdapterPosition(view);
                            if (pos%2 ==0) {
                                outRect.left = ScreenUtil.dip2px(mContext, 10);
                                outRect.right = ScreenUtil.dip2px(mContext, 4);
                            } else {
                                outRect.left = ScreenUtil.dip2px(mContext, 4);
                                outRect.right = ScreenUtil.dip2px(mContext, 10);
                            }
                            outRect.bottom = ScreenUtil.dip2px(mContext, 10);
                        }
                    });
                    HomeListAdapter homeListAdapter = new HomeListAdapter(R.layout.item_home_list_layout, homeDataBean.getData().getMostVideoList());
                    mRecyclerView.setAdapter(homeListAdapter);
                    //TODO:事件
                    helper.getView(R.id.more_back_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mHomeTypeAdapterLisenter != null) {
                                mHomeTypeAdapterLisenter.onHotMoreMovies();
                            }
                        }
                    });

                    homeListAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            HomeListBean listBean = homeDataBean.getData().getMostVideoList().get(position);
                            if (mHomeTypeAdapterLisenter != null) {
                                mHomeTypeAdapterLisenter.gotoVideo(listBean);
                            }
                        }
                    });
                }

            }
            break;

            case HomeTypeBean.LAYOUT_MAN_LIST: {
                TextView textView = helper.getView(R.id.more_text_view);
                textView.setText("人气明星");
                if (homeDataBean !=null && homeDataBean.getData()!=null && homeDataBean.getData().getStarList() != null) {
                    final RecyclerView mRecyclerView = helper.getView(R.id.man_recycler_view);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);
                            outRect.bottom = ScreenUtil.dip2px(mContext, 1);
                        }
                    });
                    HomeManAdapter manAdapter = new HomeManAdapter(R.layout.item_home_star_layout, homeDataBean.getData().getStarList());
                    mRecyclerView.setAdapter(manAdapter);
                    manAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            if (mHomeTypeAdapterLisenter != null) {
                                mHomeTypeAdapterLisenter.onStarMovies(homeDataBean.getData().getStarList().get(position));
                            }
                        }
                    });
                    //TODO:事件
                    helper.getView(R.id.more_back_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mHomeTypeAdapterLisenter != null) {
                                mHomeTypeAdapterLisenter.onStarMoreMovies();
                            }
                        }
                    });
                }

            }
            break;
            default: {
                if (item !=null && item.getHomeClassCollectBean()!=null && item.getHomeClassCollectBean().getName() != null) {
                    TextView textView = helper.getView(R.id.more_text_view);
                    textView.setText(item.getHomeClassCollectBean().getName());
                    final RecyclerView mRecyclerView = helper.getView(R.id.new_recycler_view);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);
                            int pos = parent.getChildAdapterPosition(view);
                            if (pos%2 ==0) {
                                outRect.left = ScreenUtil.dip2px(mContext, 20);
                                outRect.right = ScreenUtil.dip2px(mContext, 4);
                            } else {
                                outRect.left = ScreenUtil.dip2px(mContext, 4);
                                outRect.right = ScreenUtil.dip2px(mContext, 20);
                            }
                            outRect.bottom = ScreenUtil.dip2px(mContext, 10);
                        }
                    });
                    HomeListAdapter homeListAdapter = new HomeListAdapter(R.layout.item_home_list_layout, item.getHomeClassCollectBean().getVideoList());
                    mRecyclerView.setAdapter(homeListAdapter);
                    //TODO:事件
                    helper.getView(R.id.more_back_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mHomeTypeAdapterLisenter != null) {
                                HomeClassBean classBean = new HomeClassBean();
                                classBean.setName(item.getHomeClassCollectBean().getName());
                                classBean.setId(item.getHomeClassCollectBean().getId());
                                mHomeTypeAdapterLisenter.onClassClick(classBean);
                            }
                        }
                    });

                    homeListAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            HomeListBean listBean = item.getHomeClassCollectBean().getVideoList().get(position);
                            if (mHomeTypeAdapterLisenter != null) {
                                mHomeTypeAdapterLisenter.gotoVideo(listBean);
                            }
                        }
                    });
                }
            }
                break;
        }
    }

    public interface HomeTypeAdapterLisenter {
        //分类点击事件
        void onClassClick(HomeClassBean homeClassBean);
        //最新片源更多
        void onNewMoreMovies();
        //人气明星更多
        void onStarMoreMovies();
        //人气明星
        void onStarMovies(HomeStarBean homeStarBean);
        //观看视频
        void gotoVideo(HomeListBean listBean);
        //重磅热播
        void onHotMoreMovies();

    }
}
