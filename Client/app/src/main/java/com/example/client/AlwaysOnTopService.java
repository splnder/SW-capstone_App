package com.example.client;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class AlwaysOnTopService extends Service {

    com.example.client.Writer writer = new com.example.client.Writer();
    Intent pop;
    WindowManager wm;
    View mView;

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();


        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                /*ViewGroup.LayoutParams.MATCH_PARENT*/300,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.LEFT | Gravity.TOP;
        mView = inflate.inflate(R.layout.view_in_service, null);

        mView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent){
                writer.writeTimeLog(getApplicationContext(),"TOUCH_DETECT");
                Log.d("touch","in mView"+ getApplicationContext().toString());

                /*
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.on_off_log");
                intent.putExtra("alert","nonActive");
                intent.addFlags(Intent.FLAG_FROM_BACKGROUND);

                startActivity(intent);
                */


                Intent timerIntent = new Intent(getApplicationContext(), ActiveTimerService.class);
                timerIntent.putExtra("reset", 1);
                startService(timerIntent);


                onDestroy();
                return false;
            }
        });

        wm.addView(mView, params);
    }

    public void putPopIntent(Intent pop){
        this.pop = pop;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(wm != null) {
            if(mView != null) {
                wm.removeView(mView);
                mView = null;
            }
            wm = null;
        }
    }

}
