package com.example.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import android.content.pm.PackageManager;
import android.location.Location;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.time.*;

public class Server extends AppCompatActivity{
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;

    public static String alarmMessage = null;
    public static String alarmMessageCheck = null;

    static int userid = 19 ;

    private Location location;
    private LocationListener gpsLocationListener;
    private Map<String,String> map;

    public void activePost() {
        try{
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonInput = new JSONObject();
            LocalDateTime currentDateTime = LocalDateTime.now();
            String time = currentDateTime.toString();

            jsonInput.put( "latitude", 37.282913);
            jsonInput.put("longitude", 127.04607);
            jsonInput.put("timestamp",time);
            jsonInput.put("userId",19);

            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);

            Request request = new Request.Builder()
                    .url("http://101.101.217.202:9000/non-active/user/" + userid + "/alarm")
                    .post(reqBody)
                    .build();

            Response response = client.newCall(request).execute();

            String message = response.body().string();
            System.out.println(message);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void fallPost() {
        getGPS();
        String latitude = null;
        String longitude = null;
        if(map != null){
            latitude = map.get("latitude");
            longitude = map.get("longitude");
        }
        else{
            latitude = "37.282913";
            longitude = "127.04607";
        }

        try{
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonInput = new JSONObject();
            LocalDateTime currentDateTime = LocalDateTime.now();
            String time = currentDateTime.toString();

            jsonInput.put( "latitude", latitude);
            jsonInput.put("longitude", longitude);
            jsonInput.put("timestamp",time);
            jsonInput.put("userId",19);

            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);

            Request request = new Request.Builder()
                    .url("http://101.101.217.202:9000/fall/user/19/alarm")
                    .post(reqBody)
                    .build();

            Response response = client.newCall(request).execute();

            String message = response.body().string();
            System.out.println(message);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void getGPS(){
        map = new HashMap<String,String>();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        gpsLocationListener = new LocationListener() {
            public void onLocationChanged(Location currentLocation) {
                location = currentLocation;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,
                1,
                gpsLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000,
                1,
                gpsLocationListener);

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if(location == null){
                    Log.d("getGPS","location is null");
                }
                else{
                    Log.d("getGPS",location.getLatitude()+""+location.getLongitude());
                    map.put("latitude", String.valueOf(location.getLatitude()));
                    map.put("longitude", String.valueOf(location.getLongitude()));
                }
                locationManager.removeUpdates(gpsLocationListener);
                this.cancel();
            }
        };

        Timer timer = new Timer();
        timer.schedule(tt, 10000 ,1000);

        return;
    }

    public class serverReceive {

        OkHttpClient client = new OkHttpClient();

        public void run() throws Exception {
            Request request = new Request.Builder()
                    .url("http://101.101.217.202:9000/alarm-type")
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            System.out.println(response.body().string());

            alarmMessage = response.body().string();
        }
    }
}