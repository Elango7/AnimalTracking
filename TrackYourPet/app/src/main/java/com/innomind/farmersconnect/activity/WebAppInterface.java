package com.innomind.farmersconnect.activity;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * Created by Shaik on 12/08/2016.
 */
public class WebAppInterface {
    protected MainActivity parentActivity;
    protected WebView mWebView;

    public WebAppInterface(MainActivity _activity, WebView _webView)  {
        parentActivity = _activity;
        mWebView = _webView;

    }


    @JavascriptInterface
    public String modifyString(String inputString) {
        return inputString + " from Java side";
    }
}