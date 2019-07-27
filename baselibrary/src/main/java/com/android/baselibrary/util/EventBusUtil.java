package com.android.baselibrary.util;

import android.content.Context;

import org.simple.eventbus.EventBus;

/**
 * Created by gyq on 2016/8/21.
 */
public class EventBusUtil {

    public static void  registerEvent(Context context){

        EventBus.getDefault().register(context);

    }

    public static void  postEventByEventBus(Object object, String tag){

        EventBus.getDefault().post(object,tag);

    }

    public static void  unregisterEvent(Context context){

        EventBus.getDefault().unregister(context);

    }

}
