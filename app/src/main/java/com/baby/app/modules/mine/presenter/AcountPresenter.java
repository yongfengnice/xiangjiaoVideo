package com.baby.app.modules.mine.presenter;

import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.upload.UploadImageListener;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.baby.app.modules.mine.view.IAoucntView;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * Created by yongqianggeng on 2018/10/18.
 */

public class AcountPresenter extends BasePresenter {

    private IAoucntView mIAoucntView;

    public AcountPresenter(IAoucntView aoucntView) {
        this.mIAoucntView = aoucntView;
    }

    @Override
    protected BaseView getView() {
        return mIAoucntView;
    }

    /**
     * 创建调用系统照相机待存储的临时文件
     */
    public File createCameraTempFile() {
        return new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/sleepApp/image/"),
                System.currentTimeMillis() + ".jpg");
    }

    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }


    public void uploadImage(final String imageString) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIAoucntView.showDialogLoading("修改头像中...");
                uploadSingleImage(NetService.getInstance().saveMemberPic(imageString),new UploadImageListener() {

                    @Override
                    public void uploadImageSuccess(String imageUrl) {
                        mIAoucntView.hideDialogLoading();
                        UserStorage.getInstance().saveUserHead(imageUrl);
                        mIAoucntView.refreshHeadImage(imageUrl);
                        ToastUtil.showToast("头像更新成功");
                        Logger.e("*******upload image ID=" + imageUrl);
                    }

                    @Override
                    public void uploadImageFailed() {
                        mIAoucntView.hideDialogLoading();
                        mIAoucntView.showToast("头像上传失败");
                    }
                });
            }
        },100);
    }

    public void saveMemberInfoSex(final String sex){
        requestDateNew(NetService.getInstance().saveMemberInfoSex(sex), "", new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean bean = (BaseBean) obj;
                UserStorage.getInstance().saveSex(sex);
                mIAoucntView.refreshSex(sex);
            }
            @Override
            public void onFaild(Object obj) {

            }

            @Override
            public void onNetWorkError(String errorMsg) {

            }
        });
    }
}
