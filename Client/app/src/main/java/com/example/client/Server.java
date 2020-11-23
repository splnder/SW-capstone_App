package com.example.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
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

    public String alarmMessage = "";
    public String alarmMessageCheck = "";

    static int userid = 19 ;

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
                    .url("http://101.101.217.202:9000/fall/user/" + userid + "/alarm")
                    .post(reqBody)
                    .build();

            Response response = client.newCall(request).execute();

            String message = response.body().string();
            System.out.println(message);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


}