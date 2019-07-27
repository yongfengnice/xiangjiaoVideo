package com.baby.app.modules.search.view;

import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.bean.home.DetailListBean;
import com.android.baselibrary.service.bean.search.SearchBean;

/**
 * Created by yongqianggeng on 2018/10/16.
 */

public interface SearchView extends BaseView {

    void refersh(SearchBean searchBean);
    void refreshList(DetailListBean listBean);
}
