package com.baby.app.modules.mine.page;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.title.TitleBuilder;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.android.baselibrary.base.BaseActivity;
import com.android.baselibrary.base.standard.YQApi;
import com.android.baselibrary.widget.title.TitleBuilder;
import com.baby.app.R;
import com.baby.app.application.IBaseActivity;

@YQApi(
        swipeback = true,
        openAnimation = -1,
        closAnimatione = -1
)
public class AlipayActivity extends IBaseActivity {

    private String alipayForm;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_alipay;
    }

    @Override
    protected void onTitleClickListen(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                finish();
                break;
            case MIDDLE:
                break;
            case RIGHT:
                break;
        }
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setMiddleTitleText("支付宝支付")
                .setLeftDrawable(R.mipmap.ic_back_brown).setLeftText("我");
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initUiAndListener() {

        WebView webView = (WebView)findViewById(R.id.webView);

        alipayForm = getIntent().getExtras().getString("form", "");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        // 为图片添加放大缩小功能
        webView.getSettings().setUseWideViewPort(true);

        webView.setInitialScale(70);   //100代表不缩放
//        alipayForm = alipayForm.replaceAll("&quot;","&");
//        String url = Constants.NEW_BASE_URL+"api/user/aliPayOrderInfo";
//        webView.loadData(alipayForm, "text/html", "utf-8");

        webView.loadDataWithBaseURL(null, alipayForm, "text/html", "utf-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // 获取上下文, H5PayDemoActivity为当前页面
                final Activity context = AlipayActivity.this;

                // ------  对alipays:相关的scheme处理 -------
                if(url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                        finish();
//                        EventBusUtil.postEventByEventBus(new RechargeEvent(),RechargeEvent.TAG);

                    } catch (Exception e) {
//                        new AlertDialog.Builder(context)
//                                .setMessage("未检测到支付宝客户端，请安装后重试。")
//                                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
//                                        context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
//                                    }
//                                }).setNegativeButton("取消", null).show();
                        return true;
                    }
                    return true;
                }
                // ------- 处理结束 -------

                if (!(url.startsWith("http") || url.startsWith("https"))) {
                    return true;
                }

                view.loadUrl(url);
                return true;

            }
        });
    }

}
