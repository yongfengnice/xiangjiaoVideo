package com.android.baselibrary.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by liutb on 2018/3/20.
 */

public class DeviceUtils {

    /**
     * 获取手机信息
     *
     * @param context
     * @return
     */
    public static String getInfo(Context context) {
        String model = Build.MODEL;
        String device = Build.DEVICE;
        String brand = Build.BRAND;
        String product = Build.PRODUCT;
        String display = Build.DISPLAY;
        String manufacture = Build.MANUFACTURER;

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        float density = dm.density;

        StringBuilder sb = new StringBuilder();
        String finalInfo = sb.append("MODEL " + model).append("\nDEVICE " + device).append("\nBRAND " + brand)
                .append("\nPRODUCT " + product).append("\nDISPLAY " + display).append("\nMANUFACTURE " + manufacture)
                .append("\nSCREEN_WIDTH " + screenWidth).append("\nSCREEN_HEIGHT " + screenHeight)
                .append("\nDENSITY " + density).toString();
        return finalInfo;
    }

    /**
     * 蓝牙mac
     *
     * @return
     */
    public static final String getBluetoothMac() {
        BluetoothAdapter adapter = null;
        String bluetoothMac = null;
        try {
            adapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothMac = adapter.getAddress();
        } catch (Exception e) {
        }
        return bluetoothMac;
    }

    /**
     * wlanMac
     *
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds")
    public static final String getWlanMac(Context context) {
        String wlanMac = null;
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wlanMac = wm.getConnectionInfo().getMacAddress();
        } catch (Exception e) {

        }
        return wlanMac;
    }

    /**
     * 获取android版本
     *
     * @return
     */
    public static float getAndroidVersion() {
        return Float.valueOf(Build.VERSION.RELEASE);
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取sdk版本 get android os sdk version 2.2 = 8,2.3 = 9,4.2.1 = 17...
     *
     * @return sdk version
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取androidId
     *
     * @param context
     * @return
     */
    public static final String getAndroidId(Context context) {
        String androidID = null;
        try {
            androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {

        }
        return androidID;
    }

    /**
     * IMEI
     *
     * @param context
     * @return
     */
    public static final String getIMEI(Context context) {
        String deviceIMEI = null;
        try {
            TelephonyManager teleManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceIMEI = teleManager.getDeviceId();
        } catch (Exception e) {

        }
        if (deviceIMEI == null) {
            deviceIMEI = "87654321";
        }
        return deviceIMEI;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 像素-->dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    /**
     * sp --> px
     *
     * @param context
     * @param sp
     * @return
     */
    public static int sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        int px = Math.round(sp * scale);
        return px;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            return dm.heightPixels;
        } catch (Exception e) {
            Log.e("ERROR",e.toString());
            return 0;
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            return dm.widthPixels;
        } catch (Exception e) {
            Log.e("ERROR",e.toString());
            return 0;
        }
    }

    /**
     * 获取系统状态栏高度 return system bar height
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight1(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj;
            obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int width = Integer.parseInt(field.get(obj).toString());
            int height = context.getResources().getDimensionPixelSize(width);
            return height;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getStatuBarHeight",e.toString());
        }
        return 0;
    }

    /**
     * 获取系统状态栏高度 return system bar height
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height","dimen","android");
        if(resId>0){
            result = context.getResources().getDimensionPixelSize(resId);
        }
        return result;
    }

    /**
     * 获取系统状态栏高度 return system bar height
     *
     * @param context
     * @return
     */
//	public static int getStatuBarHeight2(Context context) {
//		Rect frame = new Rect();
//		((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//		int statusBarHeight = frame.top;
//		return statusBarHeight;
//	}

    /**
     * 获取屏幕英寸
     *
     * @param context
     * @return
     */
    public static float getScreenInches(Context context) {
        float screenInches = -1;
        try {
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            double width = Math.pow(dm.widthPixels / dm.xdpi, 2);
            double height = Math.pow(dm.heightPixels / dm.ydpi, 2);
            screenInches = (float) (Math.sqrt(width + height));
        } catch (Exception e) {
        }
        return screenInches;
    }

    /**
     * 获取某个view 的宽度和高度 o[0]---width o[1]---height
     */

    public static int[] getHeghtAndWidth(View view) {
        int o[] = new int[2];
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        o[0] = view.getMeasuredWidth();
        o[1] = view.getMeasuredHeight();
        return o;
    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public static int getDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;
        return density;
    }

    /**
     * 获取分配的heap大小
     *
     * @param context
     * @return
     */
    public static int getHeapSize(Context context) {
        int size = 0;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            size = activityManager.getMemoryClass();
        } catch (Exception e) {
            Log.e("ERROR",e.toString());
        }

        return size;
    }
    /**
     * 获取文字长度
     * @return
     */
    public static Paint getTextPaint() {
        Paint mTextPaint = new Paint();
        mTextPaint.setARGB(225, 75, 75, 75);
        Paint.Style style = mTextPaint.getStyle();
        mTextPaint.setStyle(style);
        mTextPaint.setTextSize(19.0f); // 指定字体大小
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setFakeBoldText(true); // 粗体
        mTextPaint.setAntiAlias(true); // 非锯齿效果
        return mTextPaint;
    }

    public static String getUniqueId(Context context){
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }


    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }
}
