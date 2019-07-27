package com.baby.app.modules.home.page;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;

/**
 * Created by yongqianggeng on 2018/10/1.
 * 重榜热播 (暂时用分类/最新片源的页面代替)
 */
@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class HomeHotActivity extends IBaseActivity {
    @Override
    protected int getLayoutView() {
        return R.layout.activity_hot_home;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {

    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {

    }

    @Override
    public void initUiAndListener() {

    }
}
