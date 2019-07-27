package com.android.baselibrary.util;

import com.google.gson.Gson;

/**
 * Created by gyq on 2017/5/12.
 */

public class JsonUtil {

    public static String beanToJson(Object object){

        Gson gson = new Gson();
        if(object != null){
            return gson.toJson(object);
        }
        return "";
    }

    public static <T> T jsonToBean(String jsonString, Class<T> beanCalss) {

        Gson gson = new Gson();
        if(jsonString != null){
            return gson.fromJson(jsonString,beanCalss);
        }
        return null;

    }
}
