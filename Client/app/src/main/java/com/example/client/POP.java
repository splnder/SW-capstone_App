package com.example.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class POP extends Service {

    String text;
    @Override
    public IBinder onBind(Intent intent) {
    // Service 객체와 (화면단 Activity 사이에서)
    // 통신(데이터를 주고받을) 때 사용하는 메서드
    // 데이터를 전달할 필요가 없으면 return null;
    return null;
}
    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("test", "서비스의 onCreate");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.d("test", "서비스의 onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행

        Log.d("test", "서비스의 onDestroy");
    }
}




