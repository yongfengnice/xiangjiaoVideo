package com.baby.app.modules.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.util.GlideUtils;
import com.bumptech.glide.Glide;
import com.baby.app.R;
import com.baby.app.service.DownInfoModel;
import com.baby.app.service.DownLoadInfo;
import com.baby.app.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 已经缓存的
 * <p>
 * Created by low_s on 2018/11/2.
 */

public class NoCacheAdapter extends RecyclerView.Adapter<NoCacheAdapter.ViewHolder> {
    private boolean isEdit = false;
    private List<DownLoadInfo> list;
    private Context context;
    private CacheAdapter.OnItemClickListener onItemClickListener;
    private long lastClickTime = 0L;
    private static final int FAST_CLICK_DELAY_TIME = 3000;  // 快速点击间隔

    private DownInfoModel model = new DownInfoModel();

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public interface OnItemClickListener {
        void onItemClick(DownLoadInfo info);
    }

    public void setOnItemClickListener(CacheAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NoCacheAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_no_cache_layout, parent, false));
    }

    public void setData(List<DownLoadInfo> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, List payloads) {
        if (payloads.isEmpty()) {
           final DownLoadInfo item = list.get(position);
            holder.sizeTv.setText(Utils.getProgressDisplayLine(item.getCurrentBytes(), item.getTotalBytes()));
            holder.nameTv.setText(item.getName());


            RelativeLayout check_button = holder.check_button;
            check_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setEdit(true);
                    model.updateEditByUrl(item.getUrl(),true);
                    notifyDataSetChanged();
                }
            });
            ImageView check_img_view = holder.check_img_view;
            if (item.getEdit()) {
                check_img_view.setImageResource(R.mipmap.icon_checked);
            } else {
                check_img_view.setImageResource(R.mipmap.icon_uncheck);
            }
            if (isEdit) {
                check_button.setVisibility(View.VISIBLE);
            } else {
                check_button.setVisibility(View.GONE);
            }



            GlideUtils
                    .getInstance()
                    .LoadNewContextBitmap(context,
                            item.getCover(),
                            holder.img,
                            R.mipmap.video_cover,
                            R.mipmap.video_cover,
                            GlideUtils.LOAD_BITMAP);

            long total = item.getTotalBytes();
            if(0 == total){
                holder.progressBar.setProgress(0);
            }else{
                long current = item.getCurrentBytes();
                int progress = (int) (((float)current/total)*100);
                if(progress == 0)
                    progress = 1;
                holder.progressBar.setProgress(progress);
            }

        } else {//payloads不为空 即调用notifyItemChanged(position,payloads)方法后执行的
            DownLoadInfo item = list.get(position);
            holder.sizeTv.setText(Utils.getProgressDisplayLine(item.getCurrentBytes(), item.getTotalBytes()));
            long total = item.getTotalBytes();
            if(0 == total){
                holder.progressBar.setProgress(0);
            }else{
                long current = item.getCurrentBytes();
                int progress = (int) (((float)current/total)*100);
                if(progress == 0)
                    progress = 1;
                holder.progressBar.setProgress(progress);
            }
        }

        //为整体布局设置点击事件，触发接口的回调
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - lastClickTime < FAST_CLICK_DELAY_TIME){
                    return;
                }
                lastClickTime = System.currentTimeMillis();
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(list.get(position));
            }
        });
    }

    public void notifyChange() {
        notifyItemRangeChanged(0,list.size(),0);
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.name_tv)
        TextView nameTv;
        @BindView(R.id.size_tv)
        TextView sizeTv;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.check_button)
        RelativeLayout check_button;
        @BindView(R.id.check_img_view)
        ImageView check_img_view;
        private int position;

        private void setDataPosition(int position) {
            this.position = position;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
