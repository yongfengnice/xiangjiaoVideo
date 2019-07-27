package com.baby.app.modules.video.page;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.video.iview.ICommentView;
import com.baby.app.modules.video.presenter.CommentPresenter;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class CommentActivity extends IBaseActivity implements ICommentView {

    @BindView(R.id.video_edit)
    EditText videoEditView;
    @BindView(R.id.video_text_count_view)
    TextView textView;

    private String videoId;
    private CommentPresenter mCommentPresenter;
    private InputMethodManager mInputMethodManager;
    @Override
    protected int getLayoutView() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
                break;
            case MIDDLE:
                break;
            case RIGHT:
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (videoEditView.getText()!=null && videoEditView.getText().toString().length() > 0) {
                    mCommentPresenter.saveVideoCommon(videoId,videoEditView.getText().toString());
                } else {
                    ToastUtil.showToast("请输入内容");
                }
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("评论")
                .setLeftDrawable(R.mipmap.ic_back_brown).setRightText("发送").setRightTextColor(Color.parseColor("#FFFA7334"));
    }

    @Override
    public void initUiAndListener() {
        videoId = getIntent().getExtras().getString(Constants.KEY_INTENT_ACTIVITY);
        mCommentPresenter = new CommentPresenter(this);
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        videoEditView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        videoEditView.addTextChangedListener(editclick);

    }

    private TextWatcher editclick = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
        @Override
        public void afterTextChanged(Editable s) {
            String text = videoEditView.getText().toString();
            textView.setText(text.length()+"/50");
        }
    };

    @Override
    public void commentSuccess() {
        ToastUtil.showToast("评论成功");
        Intent intent = new Intent(CommentActivity.this,VideoActivity.class);
        //将text框中的值传入
        setResult(101, intent);
        finish();
    }
}
