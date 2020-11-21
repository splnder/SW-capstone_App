package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

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

public class Server extends AppCompatActivity{
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public void activePost() {
        try{
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add( "timestamp", "2020-11-20T15:55:14.009+00:00")
                    .add( "status", String.valueOf(500))
                    .add( "error", "Internal Server Error")
                    .add( "message", "")
                    .add("path", "/non-active/user/19/alarm")
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
        try{
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonInput = new JSONObject();

            jsonInput.put( "latitude", 37.282913);
            jsonInput.put("longitude", 127.04607);
            jsonInput.put("timestamp","2020-11-21T08:26:41.414Z");
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
}