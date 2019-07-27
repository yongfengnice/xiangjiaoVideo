package com.android.baselibrary.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.baselibrary.base.BaseApplication;
import com.android.baselibrary.base.Constants;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


/**
 * Created by geng on 2015/10/28.
 *
 */
public class SPUtils {
    /**
     * 保存在手机里面的文件名m
     */
    public static final String FILE_NAME = Constants.SP_NAME;


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        Context context = BaseApplication.getInstance().getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            if (object != null)
                editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        Context context = BaseApplication.getInstance().getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        try {
            if (defaultObject instanceof String) {
                return sp.getString(key, (String) defaultObject);
            } else if (defaultObject instanceof Integer) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if (defaultObject instanceof Boolean) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if (defaultObject instanceof Float) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if (defaultObject instanceof Long) {
                return sp.getLong(key, (Long) defaultObject);
            }
        } catch (ClassCastException e) {

        }


        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        Context context = BaseApplication.getInstance().getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {

        String clientId = (String) SPUtils.get("clientID", "");
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }


    //保存对象
    public static void putObject(String key, Object object) {
        Context context = BaseApplication.getInstance().getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (object == null) {
            //throw new IllegalArgumentException("object is null");
        }

        if (key.equals("") || key == null) {
            //throw new IllegalArgumentException("key is empty or null");
        }
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        editor.putString(key, gson.toJson(object));
        SharedPreferencesCompat.apply(editor);
    }

    //保存对象
    public static void putObject(String key, Object object, String tableName) {
        Context context = BaseApplication.getInstance().getContext();
        SharedPreferences sp = context.getSharedPreferences(tableName,
                Context.MODE_PRIVATE);
        if (object == null) {
            //throw new IllegalArgumentException("object is null");
        }

        if (key.equals("") || key == null) {
            //throw new IllegalArgumentException("key is empty or null");
        }
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        editor.putString(key, gson.toJson(object));
        SharedPreferencesCompat.apply(editor);
    }

    //取出对象
    public static <T> T getObject(String key, Class<T> a, String tableName) {
        Context context = BaseApplication.getInstance().getContext();
        SharedPreferences sp = context.getSharedPreferences(tableName,
                Context.MODE_PRIVATE);

        String gsonString = sp.getString(key, null);
        if (gsonString == null) {
            return null;
        } else {
            try {
                Gson gson = new Gson();
                return gson.fromJson(gsonString, a);
            } catch (Throwable e) {
                e.printStackTrace();
                //throw new IllegalArgumentException("Object storaged with key " + key + " is instanceof other class");

            }
        }
        return null;
    }

    //取出对象
    public static <T> T getObject(String key, Class<T> a) {
        Context context = BaseApplication.getInstance().getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        String gsonString = sp.getString(key, null);
        if (gsonString == null) {
            return null;
        } else {
            try {
                Gson gson = new Gson();
                return gson.fromJson(gsonString, a);
            } catch (Throwable e) {
                e.printStackTrace();
                //throw new IllegalArgumentException("Object storaged with key " + key + " is instanceof other class");

            }
        }
        return null;
    }


    public static void putList(String key, List SceneList) {

        Context context = BaseApplication.getInstance().getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        editor.putString(key, gson.toJson(SceneList));
        SharedPreferencesCompat.apply(editor);
    }

    @SuppressWarnings("unchecked")
    public static List<String> getStringList(String key) {

        Context context = BaseApplication.getInstance().getContext();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        String gsonString = sp.getString(key, null);

        if (gsonString == null) {
            return null;
        } else {
            try {
                Gson gson = new Gson();
                return gson.fromJson(gsonString, new TypeToken<List<String>>() {
                }.getType());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return null;


    }

}