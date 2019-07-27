package com.android.baselibrary.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by gyq on 13-7-6.
 */
public class ViewUtil {
    /**
     * whether contains child
     *
     * @param parent
     * @param child
     * @return the index of the child if the parent contains the child, otherwise return -1
     */
    public static int safeIndexOfChild(ViewGroup parent, View child) {
        return (parent != null && child != null) ? parent.indexOfChild(child) : -1;
    }

    public static void safeRemoveChildView(ViewGroup parent, View child) {
        Log.d("", "will safeRemoveChildView : remove view = " + child + " parent = " + parent + " realParent = " + child.getParent());
        if (parent != null && child != null) {
            // if (ViewUtil.safeIndexOfChild(parent, child) != -1) {
            if (parent == child.getParent()) {
                parent.removeView(child);
                Log.d("ViewUtil", "did safeRemoveChildView : remove view = " + child + " parent = " + parent + " realParent = " + child.getParent());
            }
        }
    }

    public static void safeRemoveChildView(View child) {
        Log.d("ViewUtil", "will safeRemoveChildView : remove view = " + child);
        if (child != null) {
            ViewParent parent = child.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(child);
                Log.d("ViewUtil", "did safeRemoveChildView : remove view = " + child + " parent = " + parent + " realParent = " + child.getParent());
            }
        }
    }

    public static void safeAddChildView(ViewGroup parent, View child, int index) {
        Log.d("ViewUtil", "will safeAddChildView : add view = " + child + " parent = " + parent + " realParent = " + child.getParent());
        if (parent != null && child != null) {
            if (child.getParent() == null && ViewUtil.safeIndexOfChild(parent, child) == -1) {
                parent.addView(child, index);
                Log.d("ViewUtil", "did safeAddChildView : add view = " + child + " parent = " + parent + " realParent = " + child.getParent());
            }
        }
    }

    public static Bitmap renderViewToBitmap(View sourceView) {
        if (sourceView != null) {
            //sourceView.layout(0, 0, sourceView.getLayoutParams().width, sourceView.getLayoutParams().height);
            int width = sourceView.getWidth();
            int height = sourceView.getHeight();
            if (width > 0 && height > 0) {
                Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                sourceView.draw(c);
                return b;
            }
        }
        return null;
    }

    /**
     * get the relative coordinate
     *
     * @param view
     * @param relativeView
     * @return the relative location
     */
    public static Point getRelativeLocation(View view, View relativeView) {
        if (view != null && relativeView != null && view != relativeView) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int[] location2 = new int[2];
            relativeView.getLocationOnScreen(location2);
            return new Point(location[0] - location2[0], location[1] - location2[1]);
        }
        return new Point(0, 0);
    }

    /**
     * get the relative rect
     *
     * @param view
     * @param relativeView
     * @return the relative view's rect
     */
    public static Rect getRelativeRect(View view, View relativeView) {
        if (relativeView != null) {
            Point point = getRelativeLocation(view, relativeView);
            Rect rect = new Rect(point.x, point.y, point.x + view.getWidth(), point.y + view.getHeight());
            return rect;
        }
        return new Rect();
    }


    @SuppressLint("SetJavaScriptEnabled")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setWebViewSetting(WebView mWebView) {
        WebSettings webSettings = mWebView.getSettings();
        // 存在XSS漏洞
        webSettings.setJavaScriptEnabled(true);
        // 移除Android低版本XSS漏洞
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
                mWebView.removeJavascriptInterface("accessibility");
                mWebView.removeJavascriptInterface("accessibilityTraversal");
            } catch (Throwable tr) {
                tr.printStackTrace();
            }
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " ISZApp(ISZ/" + Build.VERSION.SDK_INT + ")");
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDatabaseEnabled(true);
        // 版本低于4.2
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setAppCacheMaxSize(1024 * 1024 * 4);
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        }
        // 版本大于5.0
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
