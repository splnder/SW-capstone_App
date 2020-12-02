package com.example.client;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ActivenessCheckService extends Service {

    BroadcastReceiver br;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(getApplicationContext(), "활동감지가 활성화되었습니다.", Toast.LENGTH_LONG).show();


        br = new com.example.client.ScreenOnOffBroadcastReceiver();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        this.registerReceiver(br, filter);

        //Log.d("test", "service onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if( intent == null)
        {
            br = new com.example.client.ScreenOnOffBroadcastReceiver();
            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            this.registerReceiver(br, filter);
        }
        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "활동감지가 비활성화되었습니다.", Toast.LENGTH_LONG).show();
        
        super.onDestroy();
        unregisterReceiver(br);
        Intent intent_for_touch_detect = new Intent(getApplicationContext(), AlwaysOnTopService.class);

        if(intent_for_touch_detect != null) {
            stopService(intent_for_touch_detect);
        }
        //Log.d("test", "service onDestroy");
    }


}
