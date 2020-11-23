package com.example.client;

import android.os.CountDownTimer;
import android.util.Log;

public class ActiveTimer extends CountDownTimer {

    public ActiveTimer(long millisInFuture, long countDownInterval)
    {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Log.e("time left",millisUntilFinished/1000 + " 초");
    }

    @Override
    public void onFinish() {
        Log.e("finish", "활동미감지");
    }
}
