package com.example.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class ReceiverSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiver_setting);
    }

    public void gotoQueue(View view) {

        Intent intent = new Intent(this, ReceiverQueueActivity.class);
        startActivity(intent);
        finish();
    }

    public void gotoList(View view) {
        Intent intent = new Intent(this, ReceiverListActivity.class);
        startActivity(intent);
        finish();
    }
}

