package com.baby.app.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.baby.app.R;
import com.squareup.picasso.Picasso;

/**
 * Created by yongqianggeng on 2018/9/19.
 */

public class LocalImageHolderView implements Holder<Integer> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        imageView.setImageResource(R.drawable.ic_default_adimage);
        Picasso.with(context)
                .load(data)
                .error(R.drawable.ic_default_adimage)
                .placeholder(R.drawable.ic_default_adimage)
                .fit()
                .into(imageView);
    }
}
