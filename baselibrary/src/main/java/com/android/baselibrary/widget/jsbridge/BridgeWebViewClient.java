package com.android.baselibrary.widget.jsbridge;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.baselibrary.util.PhoneCallUtil;

import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.net.URLDecoder;

/**
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {

    public static final String TAG = BridgeWebViewClient.class.getSimpleName();
    private BridgeWebView webView;
    private SoftReference<Context> softReference;

    public BridgeWebViewClient(BridgeWebView webView, Context context) {
        this.webView = webView;
        this.softReference = new SoftReference<>(context);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else if (url.startsWith("tel:")) {
            if (softReference != null && softReference.get() != null) {
                PhoneCallUtil.callPhone(softReference.get(), url.replaceAll("tel:", ""));
            }
            return true;
        } else if (url.startsWith("http") || url.startsWith("www")) {
            webView.loadUrl(url);
        } else if (webView.getBridgeWebViewLoadingListener() != null) {
            if (webView.getBridgeWebViewLoadingListener().jsBridgeOverrideUrlLoading(view, url)) {
                return true;
            }
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (webView.getBridgeWebViewLoadingListener() != null) {
            webView.getBridgeWebViewLoadingListener().jsBridgeOnPageStarted(view, url, favicon);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if (webView.getBridgeWebViewLoadingListener() != null) {
            webView.getBridgeWebViewLoadingListener().jsBridgeOnPageFinished(view, url);
        }
        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }
        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (webView.getBridgeWebViewLoadingListener() != null) {
            webView.getBridgeWebViewLoadingListener().jsBridgeOnReceivedError(view, errorCode, description, failingUrl);
        }
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        handler.proceed();
    }
}