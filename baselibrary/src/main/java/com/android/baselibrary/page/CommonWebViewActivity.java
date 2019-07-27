package com.android.baselibrary.page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.baselibrary.R;
import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.title.TitleBuilder;

import java.util.List;


/**
 * Created by yongqianggeng on 2018/5/8.
 */
@YQApi(
        swipeback = true,
        openAnimation = -1,
        closAnimatione = -1
)
public class CommonWebViewActivity extends BaseActivity {

    private static final String TAG = "CommonWebViewActivity";
    private String mPrevUrl;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                break;
            case MIDDLE:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {

        mTitleBuilder.setMiddleTitleText("")
                    .setLeftDrawable(R.mipmap.ic_back_brown).setLeftText("返回");

    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initUiAndListener() {

        Intent intent = this.getIntent();
        this.mWebView = (WebView) this.findViewById(R.id.webview);
        this.mProgressBar = (ProgressBar)this.findViewById(R.id.web_progressbar);
        this.mWebView.setVerticalScrollbarOverlay(true);
        this.mWebView.getSettings().setLoadWithOverviewMode(true);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setUseWideViewPort(true);
        this.mWebView.getSettings().setBuiltInZoomControls(true);
        if(Build.VERSION.SDK_INT > 11) {
            this.mWebView.getSettings().setDisplayZoomControls(false);
        }

        this.mWebView.getSettings().setSupportZoom(true);
        this.mWebView.setWebViewClient(new RongWebviewClient());
        this.mWebView.setWebChromeClient(new RongWebChromeClient());
        this.mWebView.setDownloadListener(new RongWebViewDownLoadListener());
        this.mWebView.getSettings().setDomStorageEnabled(true);
        this.mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        String url = intent.getStringExtra("url");
        Uri data = intent.getData();
        if(url != null) {
            this.mPrevUrl = url;
            this.mWebView.loadUrl(url);
        } else if(data != null) {
            this.mPrevUrl = data.toString();
            this.mWebView.loadUrl(data.toString());
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == 4 && this.mWebView.canGoBack()) {
            this.mWebView.goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public boolean checkIntent(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, 0);
        return apps.size() > 0;
    }

    private class RongWebViewDownLoadListener implements DownloadListener {
        private RongWebViewDownLoadListener() {
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            if(checkIntent(CommonWebViewActivity.this, intent)) {
                startActivity(intent);
                if(uri.getScheme().equals("file") && uri.toString().endsWith(".txt")) {
                    finish();
                }
            }

        }
    }

    private class RongWebChromeClient extends WebChromeClient {
        private RongWebChromeClient() {
        }

        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100) {
                CommonWebViewActivity.this.mProgressBar.setVisibility(View.GONE);
            } else {
                if(CommonWebViewActivity.this.mProgressBar.getVisibility() == View.GONE) {
                    CommonWebViewActivity.this.mProgressBar.setVisibility(View.VISIBLE);
                }

                CommonWebViewActivity.this.mProgressBar.setProgress(newProgress);
            }

            super.onProgressChanged(view, newProgress);
        }

        public void onReceivedTitle(WebView view, String title) {
            mTitleBuilder.setMiddleTitleText(title);

        }
    }

    private class RongWebviewClient extends WebViewClient {
        private RongWebviewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(CommonWebViewActivity.this.mPrevUrl != null) {
                if(!CommonWebViewActivity.this.mPrevUrl.equals(url)) {
                    if(!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://")) {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        try {
                            CommonWebViewActivity.this.startActivity(intent);
                        } catch (Exception var6) {
                            var6.printStackTrace();
                        }

                        return true;
                    } else {
                        CommonWebViewActivity.this.mPrevUrl = url;
                        CommonWebViewActivity.this.mWebView.loadUrl(url);
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                CommonWebViewActivity.this.mPrevUrl = url;
                CommonWebViewActivity.this.mWebView.loadUrl(url);
                return true;
            }
        }
    }
}
