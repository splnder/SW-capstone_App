package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class POPActivity extends Activity {
    private static final int ALERT_DELAY_TIME = 10; //초단위로 미감지 시간 제어
    static int counter = 0;
    Timer count = new Timer();
    TimerTask limit;

    @Override

    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_view);

        limit  = new TimerTask(){
            @Override
            public void run(){
                Log.e("알람 보내기", counter + "초");
                counter++;
                if(counter == ALERT_DELAY_TIME) {
                    counter=0;
                    this.cancel();
                    Intent popIntent = new Intent(getApplicationContext(),AlertActivity.class);
                    startActivity(popIntent);

                }

            }
        };

        count.schedule(limit, 0,1000);

    }


    //확인 버튼 클릭
    public void mOnClose(View v){
        limit.cancel();
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
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

