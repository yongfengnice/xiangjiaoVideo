package com.baby.app.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.baselibrary.base.ActivityManager;
import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.login.presenter.LoginPresenter;
import com.baby.app.login.view.LoginView;
import com.baby.app.modules.home.MainActivity;
import com.baby.app.splash.WelComeActivity;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class LoginActivity extends IBaseActivity implements LoginView {

    private long mExitTime;

    @BindView(R.id.login_button)
    RelativeLayout loginButton;

    @BindView(R.id.login_phone_edit_text)
    EditText phoneTextView;

    @BindView(R.id.login_pw_edit_text)
    EditText pwTextView;

    @BindView(R.id.login_memory_left_view)
    RelativeLayout memoryBackView;

    @BindView(R.id.login_forgot_pw_view)
    RelativeLayout forgotPwView;

    @BindView(R.id.login_eye_view)
    RelativeLayout eyeView;

    @BindView(R.id.memory_img_view)
    ImageView memoryImageView;

    @BindView(R.id.eye_image_view)
    ImageView eye_image_view;

    @BindView(R.id.register_button)
    RelativeLayout registerButton;

    @BindView(R.id.login_back_view)
    ImageView backView;

    @BindView(R.id.login_content_view)
    RelativeLayout login_content_view;

    public static final String SPLASH_KEY = "SPLASH_KEY";

    private boolean isMemory = true; //是否记住密码
    private boolean isEyes = true;   //是否隐藏密码
    private boolean isSplashJump = false;

    private LoginPresenter mLoginPresenter;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        setToolBarVisible(View.GONE);
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                if (isSplashJump) {
                    openActivity(WelComeActivity.class);
                }
               finish();
                break;
            case MIDDLE:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    public void initUiAndListener() {

        if (getIntent().getExtras()!=null) {
            isSplashJump = getIntent().getExtras().getBoolean(SPLASH_KEY);
        }

        mLoginPresenter = new LoginPresenter(this);

        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSplashJump) {
                    openActivity(WelComeActivity.class);
                }
                finish();
            }
        });

        login_content_view.getBackground().setAlpha(50);
        //记住密码
        memoryBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMemory = !isMemory;
                if (isMemory) {
                    memoryImageView.setImageResource(R.mipmap.login_s);
                } else {
                    memoryImageView.setImageResource(R.mipmap.login_n);
                }
            }
        });

        //显示密码
        eyeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEyes = !isEyes;
                if(!isEyes) {
//选择状态 显示明文--设置为可见的密码
                    pwTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eye_image_view.setImageResource(R.mipmap.login_eye);
                } else {
//默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    pwTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eye_image_view.setImageResource(R.mipmap.login_eye_no);
                }
                if (pwTextView.getText()!=null && pwTextView.getText().toString().length() > 0) {
                    pwTextView.setSelection(pwTextView.getText().toString().length());
                }
            }
        });

        //登录
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneTextView.getText()==null) {
                    ToastUtil.showToast("请输入正确手机号码");
                } else if (phoneTextView.getText().toString().length() <= 0 ) {
                    ToastUtil.showToast("请输入正确手机号码");
                } else if (phoneTextView.getText().length()!=11) {
                    ToastUtil.showToast("请输入正确手机号码");
                } else if (pwTextView.getText() == null){
                    ToastUtil.showToast("请输入密码");
                } else if (pwTextView.getText().length() <=0){
                    ToastUtil.showToast("请输入密码");
                } else {
                    showDialogLoading("登录中...");
                    mLoginPresenter.login(phoneTextView.getText().toString(),pwTextView.getText().toString(),isMemory);
                }
            }
        });

        //注册
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1000);
            }
        });

        //忘记密码
        forgotPwView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ForgotPwActivity.class);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == 1001) {
            String result = data.getStringExtra("phone");
            if (result != null) {
                phoneTextView.setText(result);
                phoneTextView.setSelection(result.length());
            }
        }
    }


    @Override
    public void loginSuccess() {
        if (isSplashJump) {
            openActivity(MainActivity.class);
        }
        finish();
    }
}
