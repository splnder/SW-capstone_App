package com.example.client;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.time.*;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Server extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;

    public static String alarmMessage = null;
    public static String alarmMessageCheck = null;

    public void activePost() {
        try {
            OkHttpClient client = new OkHttpClient();


            RequestBody formBody = new FormBody.Builder()
                    .add("latitude", "0")
                    .add("longitude", "0")
                    .add("timestamp", "2020-11-21T13:41:47.596Z")
                    .add("userId", "19")
                    .build();

            Request request = new Request.Builder()
                    .url("http://101.101.217.202:9000/non-active/user/19/alarm")
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();

            String message = response.body().string();
            System.out.println(message);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void fallPost() {
        try {
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
