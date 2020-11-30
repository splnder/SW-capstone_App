package com.example.client;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class ActiveTimerService extends Service {
    private static final int ALERT_DELAY_TIME = 3; //메세지 띄우기 전까지의 시간 (초 단위)
    private static final String WAKELOCK_TAG = "------------------------:wakelock";
    static int counter;


    Timer count = new Timer();
    TimerTask limit;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        counter=0;

        limit  = new TimerTask(){
            @Override
            public void run(){
                Log.e(getApplicationContext() + "카운트중", counter + "초");
                counter++;
                if(counter == ALERT_DELAY_TIME) {
                    counter=0;
                    PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
                    PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
                            PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE | PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
                            WAKELOCK_TAG);
                    wakeLock.acquire();


                    this.cancel();
                    Intent intent = new Intent(getApplicationContext(),ClientMainActivity.class);
                    intent.putExtra("alert","activePost");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                }

            }
        };

        count.schedule(limit, 0,1000);

        return flags;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
