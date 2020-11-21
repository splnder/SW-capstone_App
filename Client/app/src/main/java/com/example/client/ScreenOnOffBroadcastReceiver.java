package com.example.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenOnOffBroadcastReceiver extends BroadcastReceiver {

    Intent intent_for_touch_detect;
    Writer writer = new Writer();

    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction() == intent.ACTION_SCREEN_ON) {
            writer.writeTimeLog(context,"On");
            intent_for_touch_detect = new Intent(context.getApplicationContext(), AlwaysOnTopService.class);
            context.getApplicationContext().startService(intent_for_touch_detect);
        }
        else if(intent.getAction() == intent.ACTION_SCREEN_OFF){
            writer.writeTimeLog(context,"Off");
            if(intent_for_touch_detect != null){
                context.getApplicationContext().stopService(intent_for_touch_detect);
            }
        }
    }


}
