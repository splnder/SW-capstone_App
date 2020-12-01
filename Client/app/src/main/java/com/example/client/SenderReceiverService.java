package com.example.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SenderReceiverService extends Service {
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        String phone_number =(String) intent.getExtras().get("phone_number");
        boolean non_active =  (boolean)intent.getExtras().get("non_active");
        boolean fall_down =  (boolean)intent.getExtras().get("non_active");
        boolean gps =  (boolean)intent.getExtras().get("non_active");



        this.onDestroy();
        return flags;
    }








    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
