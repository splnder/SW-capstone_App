package com.example.client;



import android.os.Bundle;
import androidx.annotation.Nullable;
import android.app.Activity;
import android.content.Intent;

public class Server extends Activity{




    public String alarmMessage = "";
    public String alarmMessageCheck = "";




    public Intent intent;

    public GPSTracker gpsTracker;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(getIntent().hasExtra("activePost")){
            gpsTracker = new GPSTracker(getApplicationContext());
            activePost(gpsTracker.getLatitude()+"",gpsTracker.getLongitude()+"");
            finish();
        }
        else if(getIntent().hasExtra("fallPost")){
            gpsTracker = new GPSTracker(getApplicationContext());
            fallPost(gpsTracker.getLatitude()+"",gpsTracker.getLongitude()+"");
            finish();
        }
        this.onDestroy();
    }


    public void activePost(String latitude, String longitude) {
        HttpRequest httpRequest = new HttpRequest(getApplicationContext());
        httpRequest.execute("activePost",latitude,longitude);

    }

    public void fallPost(String latitude, String longitude) {
        HttpRequest httpRequest = new HttpRequest(getApplicationContext());
        httpRequest.execute("fallPost",latitude,longitude);
    }

}