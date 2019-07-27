package com.baby.app.modules.mine.page;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.MClearEditText;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.modules.mine.presenter.RechargePresenter;
import com.baby.app.modules.mine.view.IRechargeView;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class RechargeActivity extends BaseActivity implements IRechargeView {

    @BindView(R.id.rechare_commit)
    RelativeLayout rechare_commit;

    @BindView(R.id.recharge_name)
    MClearEditText recharge_name;

    private InputMethodManager manager;

    private RechargePresenter mRechargePresenter;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_recharge;
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
        mTitleBuilder.setMiddleTitleText("输入充值码")
                .setLeftDrawable(R.mipmap.ic_back_brown);
    }

    @Override
    public void initUiAndListener() {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mRechargePresenter = new RechargePresenter(this);

        rechare_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (recharge_name.getText()!=null && recharge_name.getText().toString().length() > 0) {
                    mRechargePresenter.recharge(recharge_name.getText().toString());
                } else {
                    ToastUtil.showToast("请输入充值码");
                }
            }
        });
    }

    @Override
    public void rechargeSuccess() {
        ToastUtil.showToast("充值成功");
        finish();
    }
}
