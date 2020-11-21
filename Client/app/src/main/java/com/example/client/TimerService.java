package com.example.client;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {
    private static final int ALERT_DELAY_TIME = 10; //초단위로 미감지 시간 제어
    private static final String WAKELOCK_TAG = "AAAAAAAAAAAAA:wakelock";
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
                if(counter == ALERT_DELAY_TIME) {
                    counter=0;
                    PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
                    PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE,
                            WAKELOCK_TAG);
                    wakeLock.acquire();


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
