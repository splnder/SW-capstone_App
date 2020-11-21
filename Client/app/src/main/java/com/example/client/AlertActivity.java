package com.example.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

public class AlertActivity extends Activity {
    Server server = new Server();

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_view);

        //api request
        new Thread(new Runnable() {
            @Override public void run() {
                server.activePost();
            }
        }).start();

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }


}