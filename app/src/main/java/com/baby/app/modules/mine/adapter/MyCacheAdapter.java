package com.baby.app.modules.mine.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.baselibrary.recycleradapter.BaseMultiItemQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseQuickAdapter;
import com.android.baselibrary.recycleradapter.BaseViewHolder;
import com.android.baselibrary.util.ScreenUtil;
import com.baby.app.R;
import com.baby.app.modules.mine.bean.MyCacheBean;
import com.baby.app.service.DownLoadInfo;

import java.util.List;

/**
 * Created by yongqianggeng on 2018/10/4.
 * 我的缓存
 */

public class MyCacheAdapter extends BaseMultiItemQuickAdapter<MyCacheBean,BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private List<MyCacheBean> cacheBeanList;
    private OnItemClkListener onItemCListener;
    public interface OnItemClkListener{
        void OnItemClick(DownLoadInfo info);
    }
    public void setOnItemClkListener(OnItemClkListener onItemCListener){
        this.onItemCListener = onItemCListener;
    }
    public MyCacheAdapter(List<MyCacheBean> data, Context mContext) {
        super(data);
        this.cacheBeanList = data;
        addItemType(MyCacheBean.LAYOUT_SECTION, R.layout.item_cache_section);
        addItemType(MyCacheBean.LAYOUT_ROW, R.layout.item_cache_row);
        this.mContext = mContext;
    }


    public void setData(List<MyCacheBean> data){
        this.cacheBeanList = data;
        setNewData(cacheBeanList);
      //  notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyCacheBean item) {
        switch (helper.getItemViewType()) {
            case MyCacheBean.LAYOUT_SECTION: {// 已经缓存
                final RecyclerView mRecyclerView = helper.getView(R.id.recycler_view);
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
                MyCacheListAdapter adapter =  new MyCacheListAdapter(R.layout.item_cache_list_layout, item.getDownLoadInfoList());
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if(null != onItemCListener){
                            onItemCListener.OnItemClick(item.getDownLoadInfoList().get(position));
                        }
                    }
                });
            }
            break;
            case MyCacheBean.LAYOUT_ROW: {// 未缓存
                final RecyclerView mRecyclerView = helper.getView(R.id.cache_recycler_view);
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
                MyNoCacheListAdapter adapter =  new MyNoCacheListAdapter(R.layout.item_no_cache_layout, item.getDownLoadInfoList());
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if(null != onItemCListener){
                            onItemCListener.OnItemClick(item.getDownLoadInfoList().get(position));
                        }
                    }
                });
            }
            break;
        }
    }
}
