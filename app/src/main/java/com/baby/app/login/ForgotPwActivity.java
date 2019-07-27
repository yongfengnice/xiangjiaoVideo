package com.baby.app.login;

import android.content.Intent;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.login.presenter.RegisterPersenter;
import com.baby.app.login.view.RegisterView;
import com.baby.app.modules.home.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class ForgotPwActivity extends IBaseActivity implements RegisterView {

    @BindView(R.id.forget_back_view)
    ImageView backView;

    @BindView(R.id.forget_phone_edit_text_view)
    EditText phoneEditTextView;

    @BindView(R.id.forget_phone_code)
    EditText phoneCode;

    //验证码
    @BindView(R.id.f_code_back_view)
    RelativeLayout codeBackView;
    @BindView(R.id.f_code_text_view)
    TextView codeTextView;

    //密码
    @BindView(R.id.forget_pw_edit_text)
    EditText passwordEditTextView;

    @BindView(R.id.forget_eye_view)
    RelativeLayout forget_eye_view;

    @BindView(R.id.f_eye_image_view)
    ImageView f_eye_image_view;

    //完成
    @BindView(R.id.forget_button)
    Button forgetButton;


    @BindView(R.id.forget_content_view)
    RelativeLayout forget_content_view;
    private int reg = 60;
    private Runnable updateTimeRunnable;

    private Handler mHandler;

    private RegisterPersenter mRegisterPersenter;

    private boolean isEyes = true;   //是否隐藏密码

    @Override
    protected int getLayoutView() {
        return R.layout.activity_forgot_pw;
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
        setToolBarVisible(View.GONE);
    }

    @Override
    public void initUiAndListener() {
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRegisterPersenter = new RegisterPersenter(this);
        mHandler = new Handler();
        updateTimeRunnable = new UpdateTimeRunnable();

        codeBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reg == 60) {
                    //获取验证码
                    if (phoneEditTextView.getText()==null) {
                        ToastUtil.showToast("请输入正确手机号码");
                    } else if (phoneEditTextView.getText().toString().length() <= 0 ) {
                        ToastUtil.showToast("请输入正确手机号码");
                    } else if (phoneEditTextView.getText().toString().length()!=11) {
                        ToastUtil.showToast("请输入正确手机号码");
                    } else {
                        mHandler.postDelayed(updateTimeRunnable,1000);
                        mRegisterPersenter.fetchCode(phoneEditTextView.getText().toString(),"2");
                    }
                }

            }
        });

        forget_content_view.getBackground().setAlpha(50);

        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneEditTextView.getText()==null) {
                    ToastUtil.showToast("请输入正确手机号码");
                } else if (phoneEditTextView.getText().toString().length() <= 0 ) {
                    ToastUtil.showToast("请输入正确手机号码");
                } else if (phoneEditTextView.getText().length()!=11) {
                    ToastUtil.showToast("请输入正确手机号码");
                } else if (phoneCode.getText() == null){
                    ToastUtil.showToast("请输入验证码");
                } else if (phoneCode.getText().length() !=4) {
                    ToastUtil.showToast("请输入4位验证码");
                } else if (passwordEditTextView.getText() == null){
                    ToastUtil.showToast("请输入6-20位密码");
                } else if (!regEx(passwordEditTextView.getText().toString())) {
                    ToastUtil.showToast("请输入6-20位密码");
                } else {
                    //TODO:完成
                    mRegisterPersenter.loseTel(phoneEditTextView.getText().toString(),phoneCode.getText().toString(),
                            passwordEditTextView.getText().toString());
                }
            }
        });

        forget_eye_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEyes = !isEyes;
                if(!isEyes) {
//选择状态 显示明文--设置为可见的密码
                    passwordEditTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    f_eye_image_view.setImageResource(R.mipmap.login_eye);
                } else {
//默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    passwordEditTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    f_eye_image_view.setImageResource(R.mipmap.login_eye_no);
                }
                if (passwordEditTextView.getText()!=null && passwordEditTextView.getText().toString().length() > 0) {
                    passwordEditTextView.setSelection(passwordEditTextView.getText().toString().length());
                }
            }
        });
    }

    private boolean regEx(String str){
        String reg = "^[a-zA-Z0-9]{6,20}$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(reg);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        return rs;
    }

    @Override
    public void registerSuccess() {
//        Intent intent = new Intent(this,LoginActivity.class);
//        //将text框中的值传入
//        intent.putExtra("phone",phoneEditTextView.getText().toString());
//        setResult(1001, intent);
//        finish();
        openActivity(MainActivity.class);
        finish();
    }

    private class UpdateTimeRunnable implements Runnable {

        public UpdateTimeRunnable() {

        }

        @Override
        public void run() {
            if (reg == 0) {
                reg = 60;
                codeTextView.setText("获取");
            } else {
                codeTextView.setText(reg+"秒");
                reg --;
                mHandler.postDelayed(this, 1000);
            }
        }

    }
}
