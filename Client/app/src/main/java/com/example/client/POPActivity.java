package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class POPActivity extends Activity {
    private static final String NONACTIVE_MSG = "활동없음이\n감지되었습니다";
    private static final String FALLDOWN_MSG = "쓰러짐이\n감지되었습니다";
    private static final int ALERT_DELAY_TIME = 3; //메세지 확인 누르기까지 기다리는 시간(초 단위)
    static int counter;
    Timer count = new Timer();
    TimerTask limit;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_view);

        counter=0;
        final TextView alertInfo = (TextView)findViewById(R.id.alertText);

        if(getIntent().getStringExtra("alert").equals("activePost")){
            alertInfo.setText(NONACTIVE_MSG);
        }
        else{
            alertInfo.setText(FALLDOWN_MSG);
        }

        limit  = new TimerTask(){
            @Override
            public void run(){
                Log.e(getApplicationContext() + "알람 보내기", counter + "초");
                counter++;
                if(counter == ALERT_DELAY_TIME) {
                    counter=0;
                    this.cancel();
                    Intent alertIntent = new Intent(getApplicationContext(),AlertActivity.class);
                    alertIntent.putExtra("alert",getIntent().getStringExtra("alert"));
                    startActivity(alertIntent);

                }

            }
        };

        count.schedule(limit, 0,1000);

    }


    //확인 버튼 클릭
    public void mOnClose(View v){
        limit.cancel();
        //데이터 전달하기

        Intent timerIntent = new Intent(getApplicationContext(), ActiveTimerService.class);
        startService(timerIntent);

        //팝업 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //창 바깥 클릭시 닫히지 않도록
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //뒤로가기 버튼 막기
        return;
    }


}

