package com.android.baselibrary.base;

import android.util.Log;

import com.android.baselibrary.service.UrlConstants;
import com.android.baselibrary.service.bean.BaseBean;
import com.android.baselibrary.service.upload.UploadImageBean;
import com.android.baselibrary.service.upload.UploadImageListener;
import com.android.baselibrary.util.LogUtils;
import com.android.baselibrary.util.NetWorkUtils;
import com.android.baselibrary.util.StringUtils;
import com.android.baselibrary.widget.toast.ToastUtil;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public abstract class BasePresenter {


    public Subscription mSubscription;

    BaseView baseView;


    /*
    * 上传单张图片
    * */
    public void uploadSingleImage(final Call call, final UploadImageListener uploadImageListener) {

        baseView = (BaseView) getView();
//        baseView.showDialogLoading();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    baseView.hideDialogLoading();
                    String jsonString = "";
                    if (response != null && response.body() != null) {
                        jsonString = new String(response.body().bytes());
                    }
                    UploadImageBean uploadImageBean = (UploadImageBean) new Gson().fromJson(jsonString, UploadImageBean.class);
                    if(uploadImageBean != null){
                        if (uploadImageBean.getHttpCode() == UrlConstants.SUCCESS_CODE && uploadImageBean.getRetCode().equals(UrlConstants.REG_CODE)) {
                            uploadImageListener.uploadImageSuccess(uploadImageBean.getUrl());
                        } else {
                            uploadImageListener.uploadImageFailed();
                        }
                    }else {
                        uploadImageListener.uploadImageFailed();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                baseView.hideDialogLoading();
                uploadImageListener.uploadImageFailed();
            }
        });
    }

    /*
    * 上传单张图片无进度条
    * */
    public void uploadSingleImageNoLoding(final Call call, final UploadImageListener uploadImageListener) {

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = "";
                    if (response != null && response.body() != null) {
                        jsonString = new String(response.body().bytes());
                    }
                    UploadImageBean uploadImageBean = (UploadImageBean) new Gson().fromJson(jsonString, UploadImageBean.class);
                    if(uploadImageBean != null){
                        if (uploadImageBean.getHttpCode() == UrlConstants.SUCCESS_CODE && uploadImageBean.getRetCode().equals(UrlConstants.REG_CODE)) {
                            uploadImageListener.uploadImageSuccess(uploadImageBean.getData());
                        } else {
                            uploadImageListener.uploadImageFailed();
                        }
                    } else {
                        uploadImageListener.uploadImageFailed();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                uploadImageListener.uploadImageFailed();
            }
        });
    }


    public synchronized void requestDateNoLog(Observable observable, final String isDialog, final BaseCallBack callBack) {
        baseView = (BaseView) getView();
        if (!NetWorkUtils.isNetworkConnected(BaseApplication.getInstance().getContext())) {
            callBack.onNetWorkError("无网络");
            return;
        }

        mSubscription = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        if (isDialog != null && baseView != null) {
                            if (isDialog.equals(Constants.DIALOG_LOADING)) {
                                baseView.showDialogLoading();
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isDialog != null && baseView != null) {
                            if (isDialog.equals(Constants.DIALOG_LOADING)) {
                                baseView.hideDialogLoading();
                            } else {
                                baseView.hidePageLoading();

                            }
                        }
                        if (e.getCause()!=null && e.getCause().getMessage()!= null) {
                            LogUtils.w("error","网络失败"+e.getLocalizedMessage());
                        } else {
                            LogUtils.w("error","网络失败"+e.getCause().getMessage()+"\n"+e.getLocalizedMessage());
                        }

                        callBack.onNetWorkError(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {

                        if (baseView != null) {
                            baseView.hideDialogLoading();
                        }
                        if (o != null && o instanceof BaseBean) {
                            BaseBean bean = (BaseBean) o;
                            if (bean == null) {
                                // onFail("抱歉,服务器出错");
                                callBack.onFaild(o);
                            } else if (bean.getHttpCode() == UrlConstants.SUCCESS_CODE && bean.getRetCode().equals(UrlConstants.REG_CODE)) {
                                callBack.onSuccess(o);
                            } else if (bean.getHttpCode() == UrlConstants.REQUEST_OUTDATA ) {
                                if (baseView != null) {
                                    baseView.gotoLogin();
                                }
                            } else{
                                callBack.onFaild(o);
                            }
                        } else {

                            if (o == null) {
                                callBack.onFaild(o);
                            } else {
                                callBack.onSuccess(o);
                            }
                        }
                    }
                });
    }


    public synchronized void requestDateNew(Observable observable, final String isDialog, final BaseCallBack callBack) {
        baseView = (BaseView) getView();
        if (!NetWorkUtils.isNetworkConnected(BaseApplication.getInstance().getContext())) {
            if(isDialog != null && baseView != null){

                baseView.hideDialogLoading();
                baseView.showToast("网络连接失败");
            }
            callBack.onNetWorkError("");
            return;
        }

        mSubscription = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        if  (baseView != null) {
                            if (isDialog.equals(Constants.DIALOG_LOADING)) {
                                baseView.showDialogLoading();
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (baseView != null) {
                            baseView.hideDialogLoading();
                            ToastUtil.showToast("服务器出错了");
                        }
                        callBack.onNetWorkError(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {

                        if (baseView != null) {
                            baseView.hideDialogLoading();
                        }
                        if (o != null && o instanceof BaseBean) {
                            BaseBean bean = (BaseBean) o;
                            if (bean == null) {
                                // onFail("抱歉,服务器出错");
                                callBack.onFaild(o);
                            } else if (bean.getHttpCode() == UrlConstants.SUCCESS_CODE && bean.getRetCode().equals(UrlConstants.REG_CODE)) {
                                callBack.onSuccess(o);
                            } else if (bean.getHttpCode() == UrlConstants.REQUEST_OUTDATA) {
                                if (baseView != null) {
                                    baseView.gotoLogin();
                                    //TODO:暂时加上
                                    callBack.onFaild(o);
                                }
                            } else {
                                if (isDialog != null){
                                    if (StringUtils.isNotEmpty(bean.getRetMsg())){
                                        showMsgFailed(bean);
                                    } else {
                                        ToastUtil.showToast("对不起，出错了");
                                    }
                                }
                                callBack.onFaild(o);
                            }
                        } else {

                            if (o == null) {
                                // onFail("抱歉,服务器出错");
                                callBack.onFaild(o);
                                baseView.showToast("抱歉,系统抢修中");
                            } else {
                                callBack.onSuccess(o);
                            }
                        }
                    }
                });
    }



    public Subscription requestSubscript(Observable observable, final String isDialog, final BaseCallBack callBack) {
        baseView = (BaseView) getView();
        if (!NetWorkUtils.isNetworkConnected(BaseApplication.getInstance().getContext())) {
            if(isDialog != null && baseView != null){
                baseView.showToast("网络连接失败");
            }
            callBack.onNetWorkError("无网络");
            return null;
        }
        Subscription subscription = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        if (baseView != null) {
                            if (isDialog.equals(Constants.DIALOG_LOADING)) {
                                baseView.showDialogLoading();
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (baseView != null) {
                            baseView.hideDialogLoading();
                            ToastUtil.showToast("对不起，出错了");
                        }
                        callBack.onNetWorkError(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {

                        if (baseView != null) {
                            baseView.hidePageLoading();
                        }
                        if (o != null && o instanceof BaseBean) {
                            BaseBean bean = (BaseBean) o;
                            if (bean == null) {
                                // onFail("抱歉,服务器出错");
                                callBack.onFaild(o);
                            } else if (bean.getHttpCode() == UrlConstants.SUCCESS_CODE && bean.getRetCode().equals(UrlConstants.REG_CODE)) {
                                callBack.onSuccess(o);
                            } else if (bean.getHttpCode() == UrlConstants.REQUEST_OUTDATA) {
                                if (baseView != null) {
                                    baseView.gotoLogin();
                                }
                            } else{
                                if(isDialog != null){
                                    if(StringUtils.isNotEmpty(bean.getRetMsg())){
                                        showMsgFailed(bean);
                                    }else{
                                        ToastUtil.showToast("对不起，出错了");
                                    }
                                }
                                callBack.onFaild(o);
                            }
                        } else {

                            if (o == null) {
                                // onFail("抱歉,服务器出错");
                                callBack.onFaild(o);
                                baseView.showToast("抱歉,系统抢修中");
                            } else {
                                callBack.onSuccess(o);
                            }
                        }
                    }
                });

        return subscription;
    }



    public Subscription requestNoLogSubscript(Observable observable, final String isDialog, final BaseCallBack callBack) {
        baseView = (BaseView) getView();
        if (!NetWorkUtils.isNetworkConnected(BaseApplication.getInstance().getContext())) {
            if(isDialog != null && baseView != null){

                baseView.hideDialogLoading();
                baseView.showToast("网络连接失败");
            }
            callBack.onNetWorkError("");
            return null;
        }
        Subscription subscription = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onCompleted() {
                        onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (baseView != null) {
                            baseView.hideDialogLoading();
//                            ToastUtil.showToast("对不起，出错了");
                        }
                        callBack.onNetWorkError(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {

                        if (baseView != null) {
                            if (isDialog.equals(Constants.DIALOG_LOADING)) {
                                baseView.showDialogLoading();
                            }
                        }
                        if (o != null && o instanceof BaseBean) {
                            BaseBean bean = (BaseBean) o;
                            if (bean == null) {
                                // onFail("抱歉,服务器出错");
                                callBack.onFaild(o);
                            } else if (bean.getHttpCode() == UrlConstants.SUCCESS_CODE && bean.getRetCode().equals(UrlConstants.REG_CODE)) {
                                callBack.onSuccess(o);
                            } else if (bean.getHttpCode() == UrlConstants.REQUEST_OUTDATA) {
                                if (baseView != null) {
                                    baseView.gotoLogin();
                                }
                            } else{
                                if(isDialog != null){
                                    if(StringUtils.isNotEmpty(bean.getRetMsg())){
                                        showMsgFailed(bean);
                                    }else{
//                                        ToastUtil.showToast("对不起，出错了");
                                    }
                                }
                                callBack.onFaild(o);
                            }
                        } else {

                            if (o == null) {
                                // onFail("抱歉,服务器出错");
                                callBack.onFaild(o);
//                                baseView.showToast("抱歉,系统抢修中");
                            } else {
                                callBack.onSuccess(o);
                            }
                        }
                    }
                });

        return subscription;
    }

    private void onFinish() {

    }

    protected void showMsgFailed(BaseBean bean){

        ToastUtil.showToast(bean.getRetMsg());
    }

    /**
     * 取消请求
     *
     * @return
     */
    public void cancelRequest() {
        if (mSubscription != null)
            mSubscription.unsubscribe();
    }

    /**
     * 获取view 用来loading和取消loading
     *
     * @return
     */
    protected abstract BaseView getView();
}
