package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;


public class Tlogin extends Activity {




    public String alarmMessage = "";
    public String alarmMessageCheck = "";




    public Intent intent;

    public GPSTracker gpsTracker;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ON  ", "LOGIN");
        Intent intent = new Intent(getApplicationContext(), Server.class);
        intent.putExtra("login","true");
        intent.putExtra("id","luttt");
        intent.putExtra("pw","luttt");
        startActivity(intent);


        this.finish();
    }
}
