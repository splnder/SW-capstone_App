package com.example.client;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        WebView myWebView = new WebView(this);
        setContentView(myWebView);
        myWebView.getSettings().setJavaScriptEnabled(true);
        final CookieManager cookieManager = CookieManager.getInstance();
        CookieManager.setAcceptFileSchemeCookies(true);
        CookieManager.getInstance().setAcceptCookie(true);
        String cookie = PreferenceManager.getString(getApplicationContext(),"sessionID");
        String cookieString1 = "JSESSIONID" + "=" + cookie.split("=")[1] + "; Domain=" + "http://101.101.217.202:9000/;";
        String cookieString2 = "JSESSIONID" + "=" + cookie.split("=")[1] + "; Domain=" + "http://101.101.217.202:9000;";
        String cookieString3 = "JSESSIONID" + "=" + cookie.split("=")[1] + "; Domain=" + "http://101.101.217.202/;";
        String cookieString4 = "JSESSIONID" + "=" + cookie.split("=")[1] + "; Domain=" + "http://101.101.217.202;";
        Log.e("dddd",cookieString1);
        Log.e("dddd",cookieString2);
        Log.e("dddd",cookieString3);
        Log.e("dddd",cookieString4);
        cookieManager.setCookie("http://101.101.217.202:9000/",cookieString1);
        cookieManager.setCookie("http://101.101.217.202:9000",cookieString2);
        cookieManager.setCookie("http://101.101.217.202/",cookieString3);
        cookieManager.setCookie("http://101.101.217.202",cookieString4);
        //cookieManager.setCookie("http://101.101.217.202/","BF74A6A8C15844374FFAD98F0B954F22");

        myWebView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cmsg)
            {

                /* process JSON */
                Log.e("onConsloeMessage",cmsg.message().toString());
                return true;

            }
        });


        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                String myCookies = CookieManager.getInstance().getCookie(url);
                Log.e("DDDDDDDDDDDDDDDDD",url);
                Log.e("EEEEEEEEEEEEEEEEEEE", "All the cookies in a string:" + myCookies);
                view.loadUrl("javascript:console.log(document.cookie);");
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                if(url.contains(":9000/login")){
                    Log.e("sddddddddddddddssss",url);
                    view.loadUrl("javascript:console.log(document.cookie.toString());");
                    String myCookies = CookieManager.getInstance().getCookie(url);
                    Log.e("DDDDDDDDDDDDDDDDD",url);
                    Log.e("EEEEEEEEEEEEEEEEEEE", "All the cookies in a string:" + myCookies);
                    String cookie = cookieManager.getCookie("http://101.101.217.202");
                    Log.d("eddage", "Login Cookie : " + cookie);
                }
                super.onLoadResource(view, url);
            }
        });
        myWebView.loadUrl("http://101.101.217.202/monitoring");
        String cookie1 = cookieManager.getCookie("http://101.101.217.202");
        Log.d("eddage", "Login Cookie : " + cookie1);

        /*
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        CookieSyncManager.getInstance().startSync();
        //도메인 url, 쿠키
        cookieManager.setCookie("user domain url", "cookie value");
        cookieManager.get
        */

        super.onCreate(savedInstanceState);
    }
}
