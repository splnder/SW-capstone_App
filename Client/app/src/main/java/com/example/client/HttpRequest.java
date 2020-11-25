package com.example.client;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest extends AsyncTask<String, Long, Boolean> {

    public static Context mContext;

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public HttpRequest(Context context) {
        mContext = context;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String latitude = strings[1];
        String longitude = strings[2];
        if(strings[0] != null && strings[0].equals("activePost")){
            try{
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
                        .url("http://101.101.217.202:9000/non-active/user/" + mContext.getString(R.string.user_id) + "/alarm")
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
        else if(strings[0] != null && strings[0].equals("fallPost")){
            try{
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
                        .url("http://101.101.217.202:9000/fall/user/" + mContext.getString(R.string.user_id) + "/alarm")
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

        return false;
    }
}
