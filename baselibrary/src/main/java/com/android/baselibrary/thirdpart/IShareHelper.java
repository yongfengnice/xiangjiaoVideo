package com.android.baselibrary.thirdpart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.baselibrary.thirdpart.weixin.IShareWeixinHelper;
import com.tencent.mm.sdk.modelbase.BaseResp;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by pengji on 16-7-4.
 *
 * @see IShareConfig
 */
public class IShareHelper implements Closeable {

    private Activity mActivity;
    private IShareWeixinHelper mIShareWeixinHelper;

    public IShareHelper(@NonNull Activity activity, @NonNull IShareListener listener) {
        mActivity = activity;

        if (hasConfigQQ()) {
        }

        if (hasConfigWeixin()) {
            mIShareWeixinHelper = new IShareWeixinHelper(new IShareWeixinListenerAdapter(listener));
        }


    }

    public Activity getActivity() {
        return mActivity;
    }


    public IShareWeixinHelper getIShareWeixinHelper() {
        return mIShareWeixinHelper;
    }


    public void resume() {
        if (mIShareWeixinHelper != null) {
            mIShareWeixinHelper.resume();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void close() throws IOException {
        mActivity = null;
        mIShareWeixinHelper.close();
    }



    private static class IShareWeixinListenerAdapter implements IShareWeixinHelper.IWXListener {

        @NonNull
        private final IShareListener mOutListener;

        private IShareWeixinListenerAdapter(@NonNull IShareListener outListener) {
            mOutListener = outListener;
        }

        @Override
        public void onWXCallback(BaseResp baseResp) {
            mOutListener.onWeixinCallback(baseResp);
        }
    }


    public interface IShareListener {

        void onQQComplete(Object o);

        void onQQCancel();

        void onWeixinCallback(BaseResp baseResp);
    }

    private static boolean hasConfigQQ() {
        return !TextUtils.isEmpty(IShareConfig.getQQAppId());
    }

    private static boolean hasConfigWeixin() {
        return !TextUtils.isEmpty(IShareConfig.getWeixinAppKey());
    }

    private static boolean hasConfigWeibo() {
        return !TextUtils.isEmpty(IShareConfig.getWeiboAppKey());
    }

    public static class SimpleIShareListener implements IShareListener {

        @Override
        public void onQQComplete(Object o) {
            // ignore
        }


        @Override
        public void onQQCancel() {
            // ignore
        }

        @Override
        public void onWeixinCallback(BaseResp baseResp) {
            // ignore
        }
    }

}
