package com.talenguyen.androidframework.utils;

import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewUtil {

    public static void config(WebView webView) {
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.setInitialScale(1);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
    }
}