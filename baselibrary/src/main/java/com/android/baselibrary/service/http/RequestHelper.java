package com.android.baselibrary.service.http;

import com.android.baselibrary.service.UrlConstants;
import com.android.baselibrary.usermanger.UserStorage;
import com.android.baselibrary.usermanger.UserType;
import com.android.baselibrary.util.GetDevicedIDUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;


/**
 * Created by gyq on 2017/3/8 0023.
 * <p/>
 * 定义统一传参
 */
public class RequestHelper {

    private Gson gson = new Gson();
    private UserStorage mUserStorage;

    public RequestHelper(UserStorage mUserStorage) {
        this.mUserStorage = mUserStorage;
    }

    public RequestBody getHttpRequestMap(HashMap<String,String> paramsMap) {
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        return body;
    }

}
