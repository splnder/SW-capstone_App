package com.example.client;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpSendImage {




    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static String latitude;
    private static String longitude;


    /*
    public static void sendEvent(String... strings) {
        if(strings.length == 3){
            if(strings[0].equals("activePost")){
                try{
                    latitude = strings[1];
                    longitude = strings[2];
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonInput = new JSONObject();
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date today = Calendar.getInstance().getTime();
                    String time = dateFormat.format(today);

                    jsonInput.put( "latitude", latitude);
                    jsonInput.put("longitude", longitude);
                    jsonInput.put("timestamp",time);
                    jsonInput.put("userId",mContext.getString(R.string.user_id));

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);

                    Request request = new Request.Builder()
                            .url("http://101.101.217.202:9000/non-active/user/alarm")
                            .post(reqBody)
                            .build();

                    Log.e("activePost",latitude+" "+longitude);

                    Response response = client.newCall(request).execute();

                    String message = response.body().string();

                    System.out.println(message);

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
            else if(strings[0].equals("fallPost")){
                try{
                    latitude = strings[1];
                    longitude = strings[2];
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonInput = new JSONObject();
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date today = Calendar.getInstance().getTime();
                    String time = dateFormat.format(today);

                    jsonInput.put( "latitude", latitude);
                    jsonInput.put("longitude", longitude);
                    jsonInput.put("timestamp",time);
                    jsonInput.put("userId",mContext.getString(R.string.user_id));

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);

                    Request request = new Request.Builder()
                            .url("http://101.101.217.202:9000/fall/user/alarm")
                            .post(reqBody)
                            .build();

                    Log.e("activePost",latitude+" "+longitude);

                    Response response = client.newCall(request).execute();

                    String message = response.body().string();
                    System.out.println(message);

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        }
        else if(strings.length == 1){
            if(strings[0].equals("homeInPost")){
                try{
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonInput = new JSONObject();
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date today = Calendar.getInstance().getTime();
                    String time = dateFormat.format(today);

                    jsonInput.put("timestamp",time);
                    jsonInput.put("userId",mContext.getString(R.string.user_id));

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);

                    Request request = new Request.Builder()
                            .url("http://101.101.217.202:9000/home-in/user/alarm")
                            .post(reqBody)
                            .build();

                    Log.e("homeInPost",latitude+" "+longitude);

                    Response response = client.newCall(request).execute();

                    String message = response.body().string();
                    System.out.println(message);

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
            else if(strings[0].equals("homeOutPost")){
                try{
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonInput = new JSONObject();
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date today = Calendar.getInstance().getTime();
                    String time = dateFormat.format(today);

                    jsonInput.put("timestamp",time);
                    jsonInput.put("userId",mContext.getString(R.string.user_id));

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);

                    Request request = new Request.Builder()
                            .url("http://101.101.217.202:9000/home-out/user/alarm")
                            .post(reqBody)
                            .build();

                    Log.e("homeOutPost",latitude+" "+longitude);

                    Response response = client.newCall(request).execute();

                    String message = response.body().string();
                    System.out.println(message);

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        }
    }
    */

    public static void sendImage(File file, Context mContext){

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("files", file.getName(), RequestBody.create(MultipartBody.FORM, file))
                .build();

        Request request = new Request.Builder()
                .url("http://101.101.217.202:9000/test/postImage")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override public void onResponse(Call call, Response response) throws IOException {
                Log.d("TEST : ", response.body().string());
            }
        });
    }
}
