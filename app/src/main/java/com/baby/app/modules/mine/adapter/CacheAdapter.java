package com.baby.app.modules.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.util.GlideUtils;
import com.baby.app.R;
import com.baby.app.service.DownInfoModel;
import com.baby.app.service.DownLoadInfo;
import com.baby.app.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 已经缓存的
 * <p>
 * Created by low_s on 2018/11/2.
 */

public class CacheAdapter extends RecyclerView.Adapter<CacheAdapter.ViewHolder> {

    private boolean isEdit = false;
    private List<DownLoadInfo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    private DownInfoModel model = new DownInfoModel();

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public interface OnItemClickListener {
        void onItemClick(DownLoadInfo info);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CacheAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DownLoadInfo> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cache_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final DownLoadInfo info = list.get(position);
        holder.sizeTv.setText(Utils.getBytesToMBString(info.getTotalBytes()) + "");
        holder.nameTv.setText(info.getName());

        RelativeLayout check_button = holder.check_button;
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setEdit(true);
                model.updateEditByUrl(info.getUrl(),true);
                notifyDataSetChanged();
            }
        });
        ImageView check_img_view = holder.check_img_view;
        if (info.getEdit()) {
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
                        info.getCover(),
                        holder.img,
                        R.mipmap.video_cover,
                        R.mipmap.video_cover,
                        GlideUtils.LOAD_BITMAP);
        //为整体布局设置点击事件，触发接口的回调
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)
                    onItemClickListener.onItemClick(list.get(position));
            }
        });
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
        @BindView(R.id.check_button)
        RelativeLayout check_button;
        @BindView(R.id.check_img_view)
        ImageView check_img_view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
