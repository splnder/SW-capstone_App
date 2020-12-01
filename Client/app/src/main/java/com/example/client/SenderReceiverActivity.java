package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SenderReceiverActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_target);
    }


    public void setReceiver(View v){


        String phone_number = "010-1111-1111";
        String non_active = "t";
        String fall_down = "f";
        String gps = "t";

        HttpRequest httpRequest = new HttpRequest(getApplicationContext());
        httpRequest.execute("receiverPost",phone_number, non_active, fall_down, gps);
        //                                  phone_number      non_active          fall_down     gps


        finish();
    }
}