package com.baby.app.modules.video.page;



import android.widget.TextView;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class IntroduceActivity extends IBaseActivity {


    @BindView(R.id.v_text_view)
    TextView textView;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_introduce;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                finish();
                break;
            case MIDDLE:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("简介")
                .setLeftDrawable(R.mipmap.ic_back_brown);
    }

    @Override
    public void initUiAndListener() {
        String introduce = getIntent().getStringExtra(Constants.KEY_INTENT_ACTIVITY);
        textView.setText(introduce);
    }
}
