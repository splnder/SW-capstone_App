package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


import java.util.List;

public class WebViewActivity extends Activity {

    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        setContentView(R.layout.web_view);

        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }

        String cookie = PreferenceManager.getString(getApplicationContext(),"sessionID");

        Log.e("stored", PreferenceManager.getString(getApplicationContext(), "sessionID") );
        Log.e("used", "JSESSIONID" + "=" + cookie.split("=")[1]);

        String cookieString = PreferenceManager.getString(getApplicationContext(), "sessionID");
        String cookieString1 = PreferenceManager.getString(getApplicationContext(), "sessionID") + "; Domain=" + "101.101.217.202;";
        String cookieString2 = PreferenceManager.getString(getApplicationContext(), "sessionID")  + "; Domain=" + "http://101.101.217.202:9000;";
        String cookieString3 =  PreferenceManager.getString(getApplicationContext(), "sessionID")  + "; Domain=" + "http://101.101.217.202/;";
        String cookieString4 = PreferenceManager.getString(getApplicationContext(), "sessionID")  + "; Domain=" + "http://101.101.217.202;";

        Log.e("dddd",cookieString1);
        Log.e("dddd",cookieString2);
        Log.e("dddd",cookieString3);
        Log.e("dddd",cookieString4);

        cookieManager.setCookie("101.101.217.202",cookie);
        //cookieManager.setCookie("http://101.101.217.202/","BF74A6A8C15844374FFAD98F0B954F22");




        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebClient());
        if(getIntent().hasExtra("pushAlarmAccess")){
            if(getIntent().getBooleanExtra("pushAlarmAccess", false)){
                webView.loadUrl("http://101.101.217.202/alarms");
            }
        }
        else{
            webView.loadUrl("http://101.101.217.202/");
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);

        /*
        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cmsg)
            {

                Log.e("onConsloeMessage",cmsg.message().toString());
                return true;

            }


        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(webView view, String url) {
                String myCookies = CookieManager.getInstance().getCookie(url);
                Log.e("DDDDDDDDDDDDDDDDD",url);
                Log.e("EEEEEEEEEEEEEEEEEEE", "All the cookies in a string:" + myCookies);
                view.loadUrl("javascript:console.log(document.cookie);");
            }
            @Override
            public void onLoadResource(webView view, String url) {
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
        webView.loadUrl("http://101.101.217.202/monitoring");
        String cookie1 = cookieManager.getCookie("http://101.101.217.202");
        Log.d("eddage", "Login Cookie : " + cookie1);

        */

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



    public void logout(View view) {
        PreferenceManager.setBoolean(getApplicationContext(), "isSessionExist", false);
        Intent loginIntent = new Intent(getApplicationContext(), ClientMainActivity.class);

        HttpRequest httpRequestForToken = new HttpRequest(getApplicationContext());
        preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.e("jlkjlksd","jkljlkjsd"+preferences.getString("token","0"));
        httpRequestForToken.execute("tokenDelete", preferences.getString("token","0"));

        startActivity(loginIntent);
        finish();
    }

}
class WebClient extends WebViewClient {
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
