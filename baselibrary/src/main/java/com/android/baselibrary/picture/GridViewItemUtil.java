package com.android.baselibrary.picture;

import android.widget.GridView;

/**
 * Created by pc on 2015/12/31.
 */
public class GridViewItemUtil {


    public static int getItemWidth(GridView gv){

        int horizontalSpacing = gv.getHorizontalSpacing();
        int numColumns = gv.getNumColumns();
        int itemWidth = (gv.getWidth() - (numColumns - 1) * horizontalSpacing
                - gv.getPaddingLeft() - gv.getPaddingRight())
                / numColumns;

        return itemWidth;
    }
}
