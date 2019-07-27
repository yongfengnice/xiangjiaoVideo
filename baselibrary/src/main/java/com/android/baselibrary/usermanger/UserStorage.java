package com.android.baselibrary.usermanger;

import com.android.baselibrary.base.Constants;
import com.android.baselibrary.service.bean.mine.HistoryBean;
import com.android.baselibrary.service.bean.user.LoginBean;
import com.android.baselibrary.service.bean.user.UserBean;
import com.android.baselibrary.util.SPUtils;
import com.orhanobut.logger.Logger;


import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyq
 * <p>
 * on 2016/7/11.
 */

public class UserStorage {

    public static UserStorage instance;

    public static UserStorage getInstance() {
        synchronized (UserStorage.class) {
            if (instance == null) {
                instance = new UserStorage();
            }
        }
        return instance;
    }

    public UserStorage() {
        initUserInfo();
        EventBus.getDefault().register(this);
    }

    private String versionCode;

    private LoginBean user;

    private UserBean info;

    //等级名称
    private String levelName;
    //等级no
    private int sortNo;
    private int nextSortNo;
    //头像
    private String headpic;
    //手机号码
    private String phone;
    private String nickName;
    //性别
    private int sex;
    private int ccExNum;
    private int cacheNum;
    private int viewNum;
    private String qqurl;
    private int usedViewNum;
    private String extensionCode;
    private float percentExNum;
    private String nexName;
    //历史记录
    private List<HistoryBean>historyBeanList;
    private List<HistoryBean>careHistoryList;
    private List<HistoryBean>cacheHistoryList;
    //vip
    private int isVip;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    //初始化
    public void initUserInfo() {

        if (user == null) {
            user = SPUtils.getObject(Constants.SP_LOGIN_OBJECT, LoginBean.class);
        }
        if (user != null) {
            saveLoginInfo(user);
        }
        info = SPUtils.getObject(Constants.SP_USER_OBJECT, UserBean.class);
        if (info != null) {
            saveUserInfo(info);
        }
    }

    public String getNexName() {
        if (info != null && info.getData() != null) {
            nexName = info.getData().getMemberInfo().getNexName();
        } else {
            nexName = "";
        }
        return nexName;
    }

    public int getIsVip() {
        if (info != null && info.getData() != null) {
            isVip = info.getData().getMemberInfo().getIsVip();
        } else {
            isVip = 0;
        }
        return isVip;
    }

    public float getPercentExNum() {
        return percentExNum;
    }

    public String getExtensionCode() {
        if (info != null && info.getData() != null) {
            extensionCode = info.getData().getMemberInfo().getExtensionCode();
        } else {
            extensionCode = "";
        }
        return extensionCode;
    }

    public List<HistoryBean> getCareHistoryList() {
        if (info != null && info.getData() != null) {
            careHistoryList = info.getData().getCareHistoryList();
        } else {
            careHistoryList = new ArrayList<>();
        }
        return careHistoryList;
    }

    public List<HistoryBean> getCacheHistoryList() {
        if (cacheHistoryList == null) {
            cacheHistoryList = new ArrayList<>();
        }
        return cacheHistoryList;
    }

    public List<HistoryBean> getHistoryBeanList() {
        if (info != null && info.getData() != null) {
            historyBeanList = info.getData().getViewHistoryList();
        } else {
            historyBeanList = new ArrayList<>();
        }
        return historyBeanList;
    }

    public int getUsedViewNum() {
        if (info != null && info.getData() != null) {
            usedViewNum = info.getData().getMemberInfo().getUsedViewNum();
        } else {
            usedViewNum = 0;
        }
        return usedViewNum;
    }

    public String getNickName() {
        if (info != null && info.getData() != null) {
            nickName = info.getData().getMemberInfo().getNickName();
        } else {
            nickName = "";
        }
        return nickName;
    }

    public String getQqurl() {
        if (info != null && info.getData() != null) {
            qqurl = info.getData().getQqurl();
        }
        return qqurl;
    }

    public int getViewNum() {
        if (info != null && info.getData() != null) {
            viewNum = info.getData().getMemberInfo().getViewNum();
        } else {
            viewNum = 0;
        }
        return viewNum;
    }

    public int getCacheNum() {
        if (info != null && info.getData() != null) {
            cacheNum = info.getData().getMemberInfo().getCacheNum();
        } else {
            cacheNum = 0;
        }
        return cacheNum;
    }

    public int getCcExNum() {
        if (info != null && info.getData() != null) {
            ccExNum = info.getData().getMemberInfo().getCcExNum();
        } else {
            ccExNum = 0;
        }
        return ccExNum;
    }

    public String getHeadpic() {
        if (info != null && info.getData() != null) {
            headpic = info.getData().getMemberInfo().getHeadpic();
        }
        return headpic;
    }

    public String getLevelName() {
        if (info != null && info.getData() != null) {
            levelName = info.getData().getMemberInfo().getLevleName();
        }
        return levelName;
    }

    public String getPhone() {
        if (info != null && info.getData() != null) {
            phone = info.getData().getMemberInfo().getTel();
        }
        return phone;
    }
//    性别 1=男 2=女 3=未知
    public int getSex() {
        if (info != null && info.getData() != null) {
            sex = info.getData().getMemberInfo().getSex();
        } else {
            sex = 3;
        }
        return sex;
    }

    public int getSortNo() {
        if (info != null && info.getData() != null) {
            sortNo = info.getData().getMemberInfo().getSortNo();
        } else {
            sortNo = 0;
        }
        return sortNo;
    }

    public int getNextSortNo() {
        if (info != null && info.getData() != null) {
            nextSortNo = info.getData().getMemberInfo().getNextSortNo();
        } else {
            nextSortNo = 1;
        }
        return nextSortNo;
    }

    public LoginBean getUser() {
        return user;
    }


    //TODO:用户登录
    public void doLogin(LoginBean user) {
        this.user.setToken(user.getToken());
        this.user.setLoginType(user.getLoginType());
        SPUtils.putObject(Constants.SP_LOGIN_OBJECT, user);
    }

    public void noMemerylogin(LoginBean user) {
        this.user.setToken(user.getToken());
        this.user.setLoginType(user.getLoginType());
        SPUtils.remove(Constants.SP_LOGIN_OBJECT);
        SPUtils.remove(Constants.SP_USER_OBJECT);
    }

    //TODO:游客登录
    public void touristLogin(LoginBean user) {
        this.user = user;
        this.info = null;
        SPUtils.remove(Constants.SP_USER_OBJECT);
        SPUtils.putObject(Constants.SP_LOGIN_OBJECT, user);
    }



    public void saveUserInfo(UserBean userBean) {
        this.info = userBean;
        SPUtils.putObject(Constants.SP_USER_OBJECT, userBean);
    }

    public void saveLoginInfo(LoginBean loginBean) {
        this.user = loginBean;
        SPUtils.putObject(Constants.SP_LOGIN_OBJECT, loginBean);
    }


    public void logout(boolean isQuit) {
//        Logger.e("*********userage logout***********");
//        SPUtils.clear();
//        SPUtils.remove(Constants.SP_LOGIN_OBJECT);
//        SPUtils.remove(Constants.SP_USER_OBJECT);
        EventBus.getDefault().post(new LogoutEvent(isQuit), LogoutEvent.TAG);
//        user = null;
    }

    public boolean isLogin() {
        if(user == null){
            return false;
        }else{
            return true;
        }
    }



    public UserBean getInfo() {
        return info;
    }


    public void saveUserHead(String imageUrl) {
        info.getData().getMemberInfo().setHeadpic(imageUrl);
        saveUserInfo(info);
    }
    public void saveSex(String sex) {
        info.getData().getMemberInfo().setSex(Integer.parseInt(sex));
        saveUserInfo(info);
    }

    public void saveNickName(String nickName) {
        info.getData().getMemberInfo().setNickName(nickName);
        saveUserInfo(info);
    }

    public String getToken() {
        if (user!=null &&user.getToken() != null) {
            return user.getToken();
        }
        return null;
    }




    /**
     * 1.登录用户 2.游客
     * **/
    public UserType getUserType() {
        if (user == null) {
            user = SPUtils.getObject(Constants.SP_LOGIN_OBJECT, LoginBean.class);
        }
        if (user != null)
            if (user.getLoginType() == 1) {
                return UserType.MARK_USER;
            } else {
                return UserType.NOMAL_USER;
            }
        else
            return UserType.NOMAL_USER;
    }

}
