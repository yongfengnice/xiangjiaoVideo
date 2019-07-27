package com.baby.app.modules.mine.page;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.widget.MClearEditText;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.presenter.NickNamePresenter;
import com.baby.app.modules.mine.view.NickNameView;


import org.simple.eventbus.EventBus;

import butterknife.BindView;

@YQApi(
        swipeback = true,
        openAnimation = -1,
        closAnimatione = -1
)
public class NickNameActivity extends IBaseActivity implements NickNameView {

    @BindView(R.id.nick_name)
    MClearEditText editText;

    private NickNamePresenter mNickNamePresenter;
    private InputMethodManager manager;


    @Override
    protected int getLayoutView() {
        return R.layout.activity_nick_name;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
                break;
            case MIDDLE:
                break;
            case RIGHT:
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (editText.getText().toString().length() >= 2 && editText.getText().toString().length() <= 16) {
                    String spaceString = editText.getText().toString().replaceAll(" +", "");
                    if (spaceString.length() > 0) {
                        mNickNamePresenter.changeUserNickName(editText.getText().toString());
                    } else {
                        ToastUtil.showToast("内容不能为空");
                    }
                } else {
                    ToastUtil.showToast("昵称长度必须为1~16位");
                }
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("修改昵称")
                .setLeftDrawable(R.mipmap.ic_back_brown).setRightText("更改");
        mTitleBuilder.setRightTextColor(Color.parseColor("#FFFA7334"));
    }

    @Override
    public void initUiAndListener() {

        mNickNamePresenter = new NickNamePresenter(this);
        String old_name = getIntent().getExtras().getString(Constants.KEY_INTENT_ACTIVITY, "");
        editText.setText(old_name);
        if (old_name!=null && old_name.length() > 0) {
            editText.setSelection(old_name.length());
        }
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    @Override
    public void refreshUserNickName(final String nickName) {
        ToastUtil.showLongToast("修改成功");
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //execute the task
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        }, 500);
    }

    /**
     * 点击空白部分隐藏键盘
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}
