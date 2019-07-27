package com.android.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class ActivityManager {

    private CopyOnWriteArrayList<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {

    }

    /**
     * 单一实例
     */
    public static ActivityManager getAppManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new CopyOnWriteArrayList<Activity>();
        }
        activityStack.add(activity);
    }

    public CopyOnWriteArrayList<Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = null;
        if(activityStack != null && activityStack.size()>0){
            activity = activityStack.get(activityStack.size()-1);
        }

        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if(activityStack.size() > 0){
        Activity activity = activityStack.get(activityStack.size()-1);
        finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (activityStack != null && activityStack.size() > 0) {
                if (activityStack.contains(activity)) {
                    activityStack.remove(activity);
                }
            }
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        synchronized(activityStack){
            if(activityStack.size() > 0){
                for (Activity activity : activityStack) {
                    if (cls.equals(activity.getClass())) {
                        finishActivity(activity);
                        break;
                    }
                }
            }

        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if(activityStack!= null && activityStack.size() > 0){
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                   // Log.v("【activity】",activityStack.get(i).getLocalClassName());
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivityNum(int num) {
        for (int i = activityStack.size() - num, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
                Log.v("【activity】",activityStack.get(i).getLocalClassName());
            }
        }
        activityStack.clear();
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public int ActivityStackSize() {
        return activityStack == null ? 0 : activityStack.size();
    }
}