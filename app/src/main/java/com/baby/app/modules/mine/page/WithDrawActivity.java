package com.baby.app.modules.mine.page;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.service.bean.mine.WithDrawBean;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.modules.mine.presenter.WithDrawPresenter;
import com.baby.app.modules.mine.view.IWithDrawView;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class WithDrawActivity extends BaseActivity implements IWithDrawView {

    @BindView(R.id.view_1_right_text)
    TextView view_1_right_text;

    @BindView(R.id.view_2_right_text)
    TextView view_2_right_text;

    @BindView(R.id.view_3_right_text)
    TextView view_3_right_text;

    @BindView(R.id.view_4_right_text)
    TextView view_4_right_text;

    @BindView(R.id.w_name_edite)
    EditText w_name_edite;

    @BindView(R.id.w_bank_edite)
    EditText w_bank_edite;

    @BindView(R.id.w_price_edite)
    EditText w_price_edite;

    @BindView(R.id.with_draw_commit)
    RelativeLayout with_draw_commit;

    private InputMethodManager manager;
    private WithDrawPresenter mWithDrawPresenter;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_with_draw;
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
                openActivity(MyPromoteActivity.class);
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("提现")
                .setLeftDrawable(R.mipmap.ic_back_brown).setRightText("查看记录")
                .setRightTextColor(Color.parseColor("#FFDBB185"));
    }

    @Override
    public void initUiAndListener() {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mWithDrawPresenter = new WithDrawPresenter(this);
        mWithDrawPresenter.fetchData();
        with_draw_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                boolean isRight = true;
                if (w_name_edite.getText()!=null && w_name_edite.getText().toString().length() > 0) {
                    isRight = true;
                } else {
                    ToastUtil.showToast("请输入收款人姓名");
                    isRight = false;
                    return;
                }

                if (w_bank_edite.getText()!=null && w_bank_edite.getText().toString().length() > 0) {
                    isRight = true;
                } else {
                    isRight = false;
                    ToastUtil.showToast("请输入银行卡号");
                    return;
                }

                if (w_price_edite.getText()!=null && w_price_edite.getText().toString().length() > 0) {
                    isRight = true;
                } else {
                    isRight = false;
                    ToastUtil.showToast("请输入金额");
                    return;
                }
                if (isRight) {
                    mWithDrawPresenter.withDraw(w_name_edite.getText().toString(),
                            w_bank_edite.getText().toString(),
                            w_price_edite.getText().toString());
                }
            }
        });


    }

    @Override
    public void callBack(WithDrawBean withDrawBean) {
        view_1_right_text.setText(withDrawBean.getTotalCronNum()+"");
        view_2_right_text.setText(withDrawBean.getCurrentCronNum()+"");
        view_3_right_text.setText(withDrawBean.getPrice()+"");
        view_4_right_text.setText(withDrawBean.getChargeFee());
    }

    @Override
    public void withDrawSuccess() {
        ToastUtil.showToast("提现申请成功");
        finish();
    }
}
