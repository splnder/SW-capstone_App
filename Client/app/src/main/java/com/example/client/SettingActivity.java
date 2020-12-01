package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
    }


    public void setSenderReceiver(View v){

        Intent SRIntent = new Intent(getApplicationContext(), SenderReceiverActivity.class);
        startActivity(SRIntent);

    }
}
