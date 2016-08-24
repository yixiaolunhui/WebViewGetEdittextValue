package com.dalong.webview;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    public final String URL="http://114.255.201.228:86/videoalliance/smsNumber.do?cpid=sohu&backurl=aHR0cDovL20udHYuc29odS5jb20vcGxheT92aWQ9NjczNjM%3D";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mWebView=(WebView)findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "dalong");
        mWebView.setWebViewClient(new MyWebViewClient());
//        mWebView.loadUrl(URL);
        mWebView.loadUrl("file:///android_asset/index.html");
    }

    final class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        public void onPageFinished(WebView view, String url) {
            //这个方法放到点击验证码时调用
            view.loadUrl("javascript:window.dalong.getMobile(" +
                    "document.getElementById('usermob').value);");
            super.onPageFinished(view, url);
        }
    }

    /**
     * js方法回调
     */
    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void getMobile(String mobile) {
            Log.d("HTML", "电话:"+mobile);
        }
    }
}
