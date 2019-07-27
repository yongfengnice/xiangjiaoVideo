package com.baby.app.modules.mine.presenter;

import com.android.baselibrary.base.BaseCallBack;
import com.android.baselibrary.base.BasePresenter;
import com.android.baselibrary.base.BaseView;
import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.NetService;
import com.android.baselibrary.service.bean.user.UserBean;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.baby.app.modules.mine.view.MineView;

/**
 * Created by yongqianggeng on 2018/10/8.
 * 个人中心
 */

public class MinePresenter extends BasePresenter {

    private MineView mMineView;

    public MinePresenter(MineView mineView){
        this.mMineView = mineView;
    }

    @Override
    protected BaseView getView() {
        return mMineView;
    }

    public void fetchUserData() {
            requestDateNew(NetService.getInstance().getMemberInfo(), "", new BaseCallBack() {
                @Override
                public void onSuccess(Object obj) {
                    UserBean bean = (UserBean) obj;
                    UserStorage.getInstance().saveUserInfo(bean);
                    mMineView.refreshMineData(false);
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
