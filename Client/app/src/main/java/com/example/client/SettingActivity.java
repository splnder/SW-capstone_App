package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends Activity {

    Intent activeIntent;
    Intent fallIntent;
    Intent gpsIntent;

    Button actBtn;
    Button fallBtn;
    Button gpsBtn;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        activeIntent = new Intent(getApplicationContext(),ActivenessCheckService.class);
        fallIntent = new Intent(getApplicationContext(), FalldownDetect.class);
        gpsIntent = new Intent(getApplicationContext(),GPSListener.class);


        actBtn = (Button)findViewById(R.id.actButton);
        fallBtn = (Button)findViewById(R.id.fallButton);
        gpsBtn = (Button)findViewById(R.id.gpsButton);
        if(PreferenceManager.getBoolean(getApplicationContext(), "non-actService")){
            startService(activeIntent);
            actBtn.setText("켜진");
        }
        else{
            actBtn.setText("꺼진");
        }

        if(PreferenceManager.getBoolean(getApplicationContext(), "fallFuncService")){
            startService(fallIntent);
            fallBtn.setText("켜진");
        }
        else{
            fallBtn.setText("꺼진");
        }

        if(PreferenceManager.getBoolean(getApplicationContext(), "gpsFuncService")){
            startService(gpsIntent);
            gpsBtn.setText("켜진");
        }
        else{
            gpsBtn.setText("꺼진");
        }
    }

    public void setAct(View view) {
        if(PreferenceManager.getBoolean(getApplicationContext(), "non-actService")){
            stopService(activeIntent);

            actBtn.setText("꺼진");
            PreferenceManager.setBoolean(getApplicationContext(), "non-actService", false);
        }
        else{
            startService(activeIntent);

            actBtn.setText("켜진");
            PreferenceManager.setBoolean(getApplicationContext(), "non-actService", true);
        }
    }

    public void setFall(View view) {
        if(PreferenceManager.getBoolean(getApplicationContext(), "fallFuncService")){
            stopService(fallIntent);

            fallBtn.setText("꺼진");
            PreferenceManager.setBoolean(getApplicationContext(), "fallFuncService", false);
        }
        else{
            startService(fallIntent);

            fallBtn.setText("켜진");
            PreferenceManager.setBoolean(getApplicationContext(), "fallFuncService", true);
        }
    }

    public void setGPS(View view) {
        if(PreferenceManager.getBoolean(getApplicationContext(), "gpsFuncService")){
            stopService(gpsIntent);

            gpsBtn.setText("꺼진");
            PreferenceManager.setBoolean(getApplicationContext(), "gpsFuncService", false);
        }
        else{
            startService(gpsIntent);

            gpsBtn.setText("켜진");
            PreferenceManager.setBoolean(getApplicationContext(), "gpsFuncService", true);
        }
    }


    public void setSenderReceiver(View v){

        Intent SRIntent = new Intent(getApplicationContext(), SenderReceiverActivity.class);
        startActivity(SRIntent);

    }

    public void logout(View view) {
        PreferenceManager.setBoolean(getApplicationContext(), "isSessionExist", false);
        Intent loginIntent = new Intent(getApplicationContext(), ClientMainActivity.class);

        actBtn.setText("꺼진");
        fallBtn.setText("꺼진");
        gpsBtn.setText("꺼진");
        stopService(activeIntent);
        stopService(fallIntent);
        stopService(gpsIntent);

        startActivity(loginIntent);
        finish();
    }
}
