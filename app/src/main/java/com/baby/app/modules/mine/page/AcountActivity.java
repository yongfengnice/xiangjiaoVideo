package com.baby.app.modules.mine.page;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.baselibrary.AppCommon;
import com.android.baselibrary.PermissonCallBack;
import com.android.baselibrary.base.ActivityManager;
import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.picture.PopWindowUtil;
import com.android.baselibrary.picture.SingleImageInterface;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.usermanger.LogoutEvent;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.util.GlideUtils;
import com.android.baselibrary.widget.headimage.ClipImageActivity;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;
import com.baby.app.modules.mine.presenter.AcountPresenter;
import com.baby.app.modules.mine.view.IAoucntView;
import com.baby.app.permisson.CallPermissonCallBack;
import com.baby.app.permisson.CallPermissonManager;
import com.baby.app.splash.SplashPresener;
import com.baby.app.splash.SplashView;
import com.soundcloud.android.crop.Crop;

import org.simple.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;

@YQApi(
        swipeback = false,
        openAnimation = -1,
        closAnimatione = -1
)
public class AcountActivity extends IBaseActivity implements SplashView,IAoucntView,SingleImageInterface {

    @BindView(R.id.login_out_button)
    RelativeLayout outButton;

    @BindView(R.id.acount_item1)
    RelativeLayout acountItem1;
    @BindView(R.id.acount_item2)
    RelativeLayout acountItem2;
    @BindView(R.id.acount_item3)
    RelativeLayout acountItem3;
    @BindView(R.id.my_img_view)
    RoundedImageView myImageView;
    @BindView(R.id.user_name_text)
    TextView user_name_text;
    @BindView(R.id.user_sex_text)
    TextView user_sex_text;

    //调用照相机返回图片临时文件
    private File tempFile;
    private SplashPresener mSplashPresener;

    private PopWindowUtil pop = null;// 底部相册/相机选择弹框

    private PopWindowUtil pop2 = null;// 底部相册/相机选择弹框

    private AcountPresenter mAcountPresenter;
    private int type; //1.图片 2.性别

    @Override
    protected int getLayoutView() {
        return R.layout.activity_acount;
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
        mTitleBuilder.setMiddleTitleText("账户管理")
                .setLeftDrawable(R.mipmap.ic_back_brown);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserStorage.getInstance().getNickName()!=null &&UserStorage.getInstance().getNickName().length() > 0) {
            user_name_text.setText(UserStorage.getInstance().getNickName());
        } else {
            user_name_text.setText("未设定");
        }
        if (UserStorage.getInstance().getSex() == 3) {
            user_sex_text.setText("未设定");
        } else if (UserStorage.getInstance().getSex() == 1) {
            user_sex_text.setText("男");
        } else {
            user_sex_text.setText("女");
        }
    }

    @Override
    public void initUiAndListener() {
        initPopWindow();
        initPopWindow2();
        mSplashPresener = new SplashPresener(this);
        mAcountPresenter = new AcountPresenter(this);
        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        GlideUtils
                .getInstance()
                .LoadContextCircleBitmap(mContext,
                        UserStorage.getInstance().getHeadpic(),
                        myImageView,
                        R.mipmap.ic_head_l,
                        R.mipmap.ic_head_l);
        acountItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallPermissonManager.checkVideoPermisson(AcountActivity.this, new CallPermissonCallBack() {
                    @Override
                    public void hasPermisson() {
                        type = 1;
                        pop.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
                    }

                    @Override
                    public void hasNoPermisson() {

                    }
                });
            }
        });

        acountItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.KEY_INTENT_ACTIVITY,UserStorage.getInstance().getNickName());
//                openActivity(NickNameActivity.class,bundle);
            }
        });

        acountItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                pop2.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
            }
        });
    }

    //TODO:初始化popwindow
    private void initPopWindow(){
        // 图片选择
        pop = new PopWindowUtil(this);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //2.调用hideSoftInputFromWindow方法隐藏软键盘
                imm.hideSoftInputFromWindow(getRootView().getWindowToken(), 0); //强制隐藏键盘
            }
        });
        pop.setIsSingle(true);
        pop.setSingleImageLisener(this);
    }

    private void initPopWindow2(){
        // 图片选择
        pop2 = new PopWindowUtil(this);
        pop2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //2.调用hideSoftInputFromWindow方法隐藏软键盘
                imm.hideSoftInputFromWindow(getRootView().getWindowToken(), 0); //强制隐藏键盘
            }
        });
        pop2.setIsSingle(true);
        pop2.setSingleImageLisener(this);
        pop2.getItem_popupwindows_camera().setText("男");
        pop2.getItem_popupwindows_Photo().setText("女");
    }

    /**
     * 退出登录
     * */
    private void showDialog() {
        AppCommon.showDialog(mContext, "提示", "是否要注销登录？", "确定", "取消", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mSplashPresener.fetchOutInfo();

            }
        });
    }

    @Override
    public void fetchDeviceInfo(LoginBean loginBean) {
        finish();
    }

    @Override
    public void faied() {

    }

    @Override
    public void refreshHeadImage(String imageUrl) {
        GlideUtils
                .getInstance()
                .LoadContextCircleBitmap(mContext,
                        UserStorage.getInstance().getHeadpic(),
                        myImageView,
                        R.mipmap.ic_head_l,
                        R.mipmap.ic_head_l);
    }

    @Override
    public void refreshSex(String sex) {
        if (UserStorage.getInstance().getSex() == 3) {
            user_sex_text.setText("未设定");
        } else if (UserStorage.getInstance().getSex() == 1) {
            user_sex_text.setText("男");
        } else {
            user_sex_text.setText("女");
        }
    }

    /**=======相册相机,头像上传======**/
    @Override
    public void gotoCamera() {
        if (type == 1) {
            //跳转到调用系统相机
            tempFile = mAcountPresenter.createCameraTempFile();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
            Uri uri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, Constants.REQUEST_CAPTURE);
        } else {
            mAcountPresenter.saveMemberInfoSex("1");
        }

    }

    @Override
    public void gotoSelectImgActivity() {
        if (type ==1) {
            //跳转到调用系统图库
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "请选择图片"), Constants.REQUEST_PICK);
        } else {
            mAcountPresenter.saveMemberInfoSex("2");
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
//                    beginCrop(Uri.fromFile(tempFile));
                    gotoClipActivity(Uri.fromFile(tempFile));

                }
                break;
            case Constants.REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    gotoClipActivity(uri);

                }
                break;
            case Constants.REQUEST_CROP_PHOTO:  //剪切图片返回
                handleCrop(resultCode, data);
                if (tempFile != null) {
                    tempFile.delete();
                }

                break;
        }
    }


    /**
     * 打开截图界面
     *
     * @param uri
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, Constants.REQUEST_CROP_PHOTO);
    }


    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
//            Uri uri = Crop.getOutput(result);
            Uri uri = result.getData();
            if (uri == null) {
                return;
            }
            String cropImagePath = AppCommon.getRealFilePathFromUri(getApplicationContext(), uri);
            mAcountPresenter.uploadImage(cropImagePath);

            //resultView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            ToastUtil.showToast(Crop.getError(result).getMessage());
        }
    }


}
