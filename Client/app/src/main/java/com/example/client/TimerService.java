package com.example.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {
    static int counter = 0;
    Timer count = new Timer();
    TimerTask limit;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        limit  = new TimerTask(){
            @Override
            public void run(){
                Log.e("카운트중", counter + "초");
                counter++;
                if(counter == 10) {
                    this.cancel();
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.client");
                    intent.putExtra("alert","nonActive");
                    intent.addFlags(Intent.FLAG_FROM_BACKGROUND);

                    startActivity(intent);
                }

            }
        };

        count.schedule(limit, 0,1000);

        return flags;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
