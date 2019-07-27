package com.baby.app.modules.mine.presenter;

import android.os.Handler;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.baby.app.modules.mine.view.NickNameView;

/**
 * Created by yongqianggeng on 2017/12/11.
 */

public class NickNamePresenter extends BasePresenter {

    private NickNameView nickNameView;

    public NickNamePresenter(NickNameView nickNameView){
        this.nickNameView = nickNameView;
    }

    @Override
    protected BaseView getView() {
        return this.nickNameView;
    }


    /*
    * 修改用户昵称
    * */
    public void changeUserNickName(final String nickName){

        requestDateNew(NetService.getInstance().saveMemberInfoName(nickName), Constants.DIALOG_LOADING, new BaseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                BaseBean bean = (BaseBean) obj;
                UserStorage.getInstance().saveNickName(nickName);

                nickNameView.refreshUserNickName(nickName);
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
