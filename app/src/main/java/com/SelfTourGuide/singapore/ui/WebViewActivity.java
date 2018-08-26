package com.SelfTourGuide.singapore.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.SelfTourGuide.singapore.R;
import com.SelfTourGuide.singapore.base.BaseActivity;
import com.google.android.gms.wearable.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webview;
    @Bind(R.id.left)
    ImageView goback;
    @Bind(R.id.right)
    ImageView goForward;
    @Bind(R.id.linear_right)
    LinearLayout linear_right;
    @Bind(R.id.linear_left)
    LinearLayout linear_left;
    @Bind(R.id.linear_close)
    LinearLayout linear_close;
    @Bind(R.id.close)
    ImageView close;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        url = getIntent().getExtras().getString("url");

        WebSettings webSettings = webview.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setSupportZoom(false);
        webview.setWebChromeClient(new MyWebChromeClient());
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new MyWebChromeClient());



        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                goback.setEnabled(webview.canGoBack());
                goForward.setEnabled(webview.canGoForward());
                if (goback.isEnabled()){
                    goback.setBackgroundResource(R.drawable.pressleft);
                }else{
                    goback.setBackgroundResource(R.drawable.defaultleft);
                }
                if (goForward.isEnabled()){
                    goForward.setBackgroundResource(R.drawable.pressright);
                }else{
                    goForward.setBackgroundResource(R.drawable.defaultright);
                }

            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (goback.isEnabled()){
                    goback.setBackgroundResource(R.drawable.pressleft);
                }else{
                    goback.setBackgroundResource(R.drawable.defaultleft);
                }
                if (goForward.isEnabled()){
                    goForward.setBackgroundResource(R.drawable.pressright);
                }else{
                    goForward.setBackgroundResource(R.drawable.defaultright);
                }
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (webview.canGoBack()){
                    goback.setBackgroundResource(R.drawable.pressleft);
                }else{
                    goback.setBackgroundResource(R.drawable.defaultleft);
                }
                if (webview.canGoForward()){
                    goForward.setBackgroundResource(R.drawable.pressright);
                }else{
                    goForward.setBackgroundResource(R.drawable.defaultright);
                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webview.canGoBack()){
                    goback.setBackgroundResource(R.drawable.pressleft);
                }else{
                    goback.setBackgroundResource(R.drawable.defaultleft);
                }
                if (webview.canGoForward()){
                    goForward.setBackgroundResource(R.drawable.pressright);
                }else{
                    goForward.setBackgroundResource(R.drawable.defaultright);
                }
            }
        });

        //加载需要显示的网页
        webview.loadUrl(url);


        linear_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //前进
                if (webview.canGoForward()){
                    webview.goForward();
                }else{

                }
            }
        });
        linear_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //来判断是否能回退网页 后退
                if (webview.canGoBack()){
                    webview.goBack();
                }else{

                }
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //来判断是否能回退网页 后退
                if (webview.canGoBack()){
                    webview.goBack();
                }else{

                }
            }
        });
        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //前进
                if (webview.canGoForward()){
                    webview.goForward();
                }else{
                }
            }
        });
        linear_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        pb = (ProgressBar) v.findViewById(R.id.pb);
//        pb.setMax(100);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        /* Do something */
    };



    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
          /*  pb.setProgress(newProgress);
            if(newProgress==100){
                pb.setVisibility(View.GONE);
            }*/
        }
    }




}
