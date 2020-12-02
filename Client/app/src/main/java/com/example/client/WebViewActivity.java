package com.example.client;

import android.app.Activity;
import android.content.Intent;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {


        setContentView(R.layout.web_view);

        WebView myWebView = new WebView(this);
        myWebView.getSettings().setJavaScriptEnabled(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(myWebView, true);


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

        cookieManager.setCookie("101.101.217.202",cookieString);
        //cookieManager.setCookie("http://101.101.217.202/","BF74A6A8C15844374FFAD98F0B954F22");



        myWebView = (WebView) findViewById(R.id.webview); //레이아웃에서 웹뷰를 가져온다
        myWebView.setWebViewClient(new WebClient()); //액티비티 내부에서 웹브라우저가 띄워지도록 설정
        myWebView.loadUrl("http://101.101.217.202/");
        WebSettings webSettings = myWebView.getSettings(); //getSettings를 사용하면 웹에대헤 다양한 설정을 할 수 있는 WebSettings타입을 가져올 수 있다.
        webSettings.setJavaScriptEnabled(true); //자바스크립트가 사용가능 하도록 설정


        /*
        myWebView.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cmsg)
            {

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
