package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

public class AlarmPopupActivity extends Activity {

    FirebaseMessagingServiceInstance fms = new FirebaseMessagingServiceInstance();

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_view);

        if (fms.remoteMessage != null ) {

            Intent AlarmPopupIntent = new Intent(getApplicationContext(), AlertFinalActivity.class);
            startActivity(AlarmPopupIntent);;
        }

        Intent AlarmPopupIntent = new Intent(getApplicationContext(), AlertFinalActivity.class);
        startActivity(AlarmPopupIntent);
    }


    //확인 버튼 클릭
    public void mOnClose(View v){

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