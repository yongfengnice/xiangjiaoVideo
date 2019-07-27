package com.android.baselibrary.service.upload;

/**
 * Created by yongqianggeng on 2017/12/11.
 * 图片请求返回监听
 */

public interface UploadImageListener {


    //如果是多图返回字image id字符串,如果是单图，直接返回url
    void uploadImageSuccess(String imageIDs);

    void uploadImageFailed();
}