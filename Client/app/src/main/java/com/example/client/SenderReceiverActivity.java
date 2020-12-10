package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SenderReceiverActivity extends Activity {

    TextInputLayout phoneField;

    String non_active = "t";
    String fall_down = "t";
    String gps = "t";

    Button actBtn;
    Button fallBtn;
    Button gpsBtn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_target);
        phoneField = findViewById(R.id.phoneField);
        actBtn = (Button)findViewById(R.id.actButton);
        fallBtn = (Button)findViewById(R.id.fallButton);
        gpsBtn = (Button)findViewById(R.id.gpsButton);
    }


    public void setReceiver(View v){


        String phone_number = phoneField.getEditText().getText().toString().trim();

        if(phone_number.equals("")){
            phoneField.setError("전화번호가 입력되지 않았습니다");
            return;
        }

        if(non_active.equals("f") && fall_down.equals("f") && gps.equals("f")){
            finish();
            return;
        }

        //Toast.makeText(this, phone_number + "/" + non_active + "/" + fall_down+ "/" + gps, Toast.LENGTH_SHORT).show();
        try{
            HttpRequest httpRequest = new HttpRequest(getApplicationContext());
            String name = httpRequest.execute("receiverPost",phone_number, non_active, fall_down, gps).get();

            if(name.equals("")){
                Toast.makeText(this, "가입되지 않은 회원의 번호입니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, name + "님이 보호자로 등록되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }

        }catch (Exception e){

        }
        //                                  phone_number      non_active          fall_down     gps


    }

    public void setAct(View view) {
        if(non_active.equals("t")){
            actBtn.setText("제외하고");
            non_active= "f";
        }
        else{
            actBtn.setText("포함하고");
            non_active= "t";
        }
    }

    public void setFall(View view) {
        if(fall_down.equals("t")){
            fallBtn.setText("제외한");
            fall_down= "f";
        }
        else{
            fallBtn.setText("포함한");
            fall_down= "t";
        }
    }

    public void setGPS(View view) {
        if(gps.equals("t")){
            gpsBtn.setText("제외하고");
            gps= "f";
        }
        else{
            gpsBtn.setText("포함하고");
            gps= "t";
        }
    }

    public void goBack(View view) {
        finish();
    }
}