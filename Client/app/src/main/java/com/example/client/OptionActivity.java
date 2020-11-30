package com.example.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import android.os.Bundle;
import android.widget.Toast;

public class OptionActivity extends AppCompatActivity {

    private Switch sw1, sw2, sw3;
    SharedPreferences.Editor prefEdotor;
    private Button button4;
    Toolbar toolbar;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        sw1 = findViewById(R.id.switch1);
        sw2 = findViewById(R.id.switch2);
        sw3 = findViewById(R.id.switch3);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean silent1 = settings.getBoolean("switchkey1", true);
        boolean silent2 = settings.getBoolean("switchkey2", true);
        boolean silent3 = settings.getBoolean("switchkey3", true);
        sw1.setChecked(silent1);
        sw2.setChecked(silent2);
        sw3.setChecked(silent3);


        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 활동 감지 센서 on

                }else{
                    // 활동 감지 센서 off

                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("switchkey1", isChecked);
                editor.commit();
            }
        });


        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 쓰러짐 감지 센서 on

                }else{
                    // 쓰러짐 감지 센서 off
                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("switchkey2", isChecked);
                editor.commit();
            }
        });


        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // GPS 감지 센서 on

                }else{
                    // GPS 감지 센서 off
                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("switchkey3", isChecked);
                editor.commit();
            }
        });

        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            { Intent intent = new Intent(OptionActivity.this, AlarmTarget.class);
                startActivity(intent);
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}