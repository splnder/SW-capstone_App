package com.example.client;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import static java.lang.Math.abs;


public class FalldownDetect extends Service implements SensorEventListener {

    private static final String TAG = "DetectFall";

    private SensorManager sensorManager;
    Sensor accelerometer;


    private double acc, acc0;
    private double THRESHOLD_2 = 6.0;
    private double THRESHOLD_3 = 1.0;
    private int flag = 0;

    //gyro
    private SensorManager mSensorManager;
    //Using the Gyroscope
    private Sensor mGgyroSensor;

    //Roll and Pitch
    private double pitch;
    private double roll;
    private double yaw;

    //timestamp and dt
    private double timestamp;
    private double dt;
    private double change;
    private double MAXch = 0;

    // for radian -> dgree
    private double RAD2DGR = 180 / Math.PI;
    private static final float NS2S = 1.0f/1000000000.0f;
    Server server = new Server();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(getApplicationContext(), "쓰러짐 감지가 활성화되었습니다.", Toast.LENGTH_LONG).show();

        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mGgyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(FalldownDetect.this,accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(FalldownDetect.this, mGgyroSensor,SensorManager.SENSOR_DELAY_NORMAL);

        Log.d(TAG, "onCreate: Registered accelormeter listener");

        createQueue(100);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == mGgyroSensor) {

            double gyroX = event.values[0];
            double gyroY = event.values[1];
            double gyroZ = event.values[2];

            dt = (event.timestamp - timestamp) * NS2S;
            timestamp = event.timestamp;

            if (dt - timestamp * NS2S != 0) {

                pitch = pitch + gyroY * dt;
                roll = roll + gyroX * dt;
                yaw = yaw + gyroZ * dt;

                change = abs(gyroX * dt + gyroY * dt + gyroZ * dt);
                MAXch = MAXch > change ? MAXch : change;
               /* if (MAXch > 0.7)
                    text_MAXch.setText("쓰러짐");*/
            }
        }

        if(event.sensor == accelerometer) {
            acc = Math.sqrt(Math.pow(event.values[0], 2)
                    + Math.pow(event.values[1], 2)
                    + Math.pow(event.values[2], 2)
            );

            if (acc0 == 0) acc0 = acc;

            enqueue(acc - acc0);
            acc0 = acc;
            //printQueue();


            //if (maxQueue() > THRESHOLD_2) {
            if(MAXch > 0.6 && maxQueue() > THRESHOLD_2){
                System.out.println("fall?");
                flag = 1;
            }

            if(flag ==1)
            {
                if (accFalldown()) {
                    System.out.println("fall!!!!");
                    flag=0;
                    //api request
                    new Thread(new Runnable() {
                        @Override public void run() {

                            Intent popIntent = new Intent(getApplicationContext(), AlertChanceActivity.class);
                            popIntent.putExtra("alert","fallPost");
                            popIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(popIntent);

                        }
                    }).start();

                } else {
                    MAXch = 0;
                }
            }
        }
    }


    private int front;
    private int rear;
    private int queSize = 100;
    private double[] accArr;

    public void createQueue(int queSize) {
        front = -1;
        rear = -1;
        this.queSize = queSize;
        accArr = new double[this.queSize];
    }


    public boolean isEmpty(){
        if(front==rear) {
            front = -1;
            rear = -1;
        }
        return (front == rear);
    }

    public boolean isFull(){
        if(rear == this.queSize-1 || rear == front-1){
            return true;
        }
        else
            return false;
    }

    public void enqueue(double acc)
    {
        if(isFull()){
            if(rear == this.queSize-1){
                rear = (front+1)%this.queSize;
                front = (rear+1)%this.queSize;
            }
            else {
                rear = front;
                front = (rear+1)%this.queSize;
            }
        }
        else{
            rear++;
        }
        accArr[rear] = acc;
    }

    public void printQueue() {
        if(isEmpty()) {
            System.out.println("Queue is empty!");
        } else {
            System.out.print("Queue elements : ");
            for(int i=0; i<queSize; i++) {
                System.out.print(accArr[i] + " ");
            }
            System.out.println();
        }
    }

    public double maxQueue(){
        double max = -1;
        for(int i = 0; i<queSize;i++)
        {
            if(accArr[i]> max) {
                max = accArr[i];
            }
        }
        return max;
    }

    public double avgQueue()
    {
        double avg;
        double sum=0;
        int count = 0;
        for(int i = 0; i<queSize;i++)
        {
            if(accArr[i] != 0.0) {
                sum = accArr[i];
                count++;
            }
        }
        return (sum/count);
    }

    public double avg;
    private int count =0;
    public boolean accFalldown()
    {
        if(count>100 && avgQueue()<THRESHOLD_3){
            count=0;
            return true;
        }
        else count++;
        return false;
    }


    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "쓰러짐 감지가 비활성화되었습니다.", Toast.LENGTH_LONG).show();

        super.onDestroy();
    }

}