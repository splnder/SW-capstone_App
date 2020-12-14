package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class SenderReceiverActivity extends Activity {

    TextInputLayout phoneField;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_target);
        phoneField = findViewById(R.id.phoneField);
    }


    public void setReceiver(View v){


        String phone_number = phoneField.getEditText().getText().toString().trim();

        if(phone_number.equals("")){
            phoneField.setError("전화번호가 입력되지 않았습니다");
            return;
        }

        try{
            HttpRequest httpRequest = new HttpRequest(getApplicationContext());
            String res = httpRequest.execute("reqToReceiver",phone_number).get();

            JSONObject jsonRes =  new JSONObject(res);

            String name = jsonRes.getString("name");

            if(name.equals("")){
                Toast.makeText(this, "가입되지 않은 회원의 번호입니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                String result = jsonRes.getString("result");
                if(result.equals("queue already exist")){
                    Toast.makeText(this, "요청 대기중인 보호자입니다.", Toast.LENGTH_SHORT).show();
                }
                else if(result.equals("connection already exist")){
                    Toast.makeText(this, "이미 등록된 보호자입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, name + "님에게 \n보호자 등록 요청이 전송되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        }catch (Exception e){

        }


    }

    public void goBack(View view) {
        finish();
    }
}