package com.example.client;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest extends AsyncTask<String, Long, String> {

    public static Context mContext;

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public HttpRequest(Context context) {
        mContext = context;
    }

    private String latitude;
    private String longitude;


    @Override
    protected String doInBackground(String... strings) {
        String getVal="";
        Log.e("InHttpRequest","시작");
        if(strings.length == 5) {
            try{

                String phoneNum = strings[1];
                boolean non_active = strings[2].equals("t");
                boolean fall_down = strings[3].equals("t");
                boolean gps = strings[4].equals("t");


                OkHttpClient client = new OkHttpClient();
                JSONObject jsonInput = new JSONObject();
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                Date today = Calendar.getInstance().getTime();
                String time = dateFormat.format(today);

                jsonInput.put( "phone_number", phoneNum);
                jsonInput.put("non_active", non_active);
                jsonInput.put("fall_down",fall_down);
                jsonInput.put("gps",gps);

                MediaType JSON = MediaType.get("application/json; charset=utf-8");
                RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);

                Request request = new Request.Builder()
                        .addHeader("Cookie", PreferenceManager.getString(mContext, "sessionID"))
                        .url("http://101.101.217.202:9000/user/set-receiver")
                        .post(reqBody)
                        .build();


                Response response = client.newCall(request).execute();
                JSONObject jsonRes =  new JSONObject(response.body().string());
                Log.e("res JSON", String.valueOf(jsonRes));
                getVal = jsonRes.getString("name");


                Log.e("RECEIVED", String.valueOf(response));
                Log.e("RECEIVED", getVal);

            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        else if(strings.length == 3){
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

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);

                    Request request = new Request.Builder()
                            .addHeader("Cookie", PreferenceManager.getString(mContext, "sessionID"))
                            .url("http://101.101.217.202:9000/non-active/user/alarm")
                            .post(reqBody)
                            .build();

                    Log.e("activePost", latitude +" "+ longitude);

                    Response response = client.newCall(request).execute();


                    getVal = response.body().string();
                    Log.e("SEND", String.valueOf(request));
                    Log.e("RECEIVED", String.valueOf(response));
                    System.err.println("sessionID = " + PreferenceManager.getString(mContext, "sessionID"));
                    System.err.println("send EventID = " + getVal);

                    PreferenceManager.setInt(mContext, "lastEventID",  Integer.valueOf(getVal));
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
                            .addHeader("Cookie", PreferenceManager.getString(mContext, "sessionID"))
                            .url("http://101.101.217.202:9000/fall/user/alarm")
                            .post(reqBody)
                            .build();

                    Log.e("activePost", latitude +" "+ longitude);

                    Response response = client.newCall(request).execute();


                    getVal = response.body().string();

                    System.err.println("send EventID = " + getVal);


                    PreferenceManager.setInt(mContext, "lastEventID",  Integer.valueOf(getVal));

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
            else if(strings[0].equals("login")){
                try{
                    latitude = strings[1];
                    longitude = strings[2];
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonInput = new JSONObject();
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date today = Calendar.getInstance().getTime();
                    String time = dateFormat.format(today);

                    jsonInput.put( "userID", latitude);
                    jsonInput.put("password", longitude);

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);

                    Request request = new Request.Builder()
                            .url("http://101.101.217.202:9000/login")
                            .post(reqBody)
                            .build();

                    Log.e("LOGIN", latitude +"/"+ longitude);

                    Response response = client.newCall(request).execute();
                    JSONObject jsonRes =  new JSONObject(response.body().string());
                    Log.e("res JSON", String.valueOf(jsonRes));
                    getVal = jsonRes.getString("code");

                    List<String> cookieList = response.headers().values("Set-Cookie");
                    if(cookieList == null){
                        Log.e("--------------------------------------", "LOGIN FAILED");
                        return "-99";
                    }

                    for (String cookie : cookieList) {
                        Log.e("쿠키 목록들:::::::::::::::", cookie);

                    }


                    String jsessionid = (cookieList .get(0).split(";"))[0];//세션 ID 얻기
                    PreferenceManager.setString(mContext, "sessionID",  jsessionid);
                    PreferenceManager.setBoolean(mContext, "isSessionExist",  true);

                    Log.e("HTTP STATUS", String.valueOf(response.code()));
                    Log.e("SET sessionID to", PreferenceManager.getString(mContext, "sessionID"));

                    Log.e("자동 로그인",String.valueOf(PreferenceManager.getBoolean(mContext, "isSessionExist")));

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        }
        else if(strings.length == 2){
            if(strings[0].equals("tokenPost")){
                try{
                    Log.d("token","in registerAppWithToken and try" +  PreferenceManager.getString(mContext, "sessionID"));
                    OkHttpClient client = new OkHttpClient();

                    JSONObject jsonInput = new JSONObject();

                    jsonInput.put("token", strings[1]);

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);
                    Request request = new Request.Builder()
                            .addHeader("Cookie", PreferenceManager.getString(mContext, "sessionID"))
                            .url("http://101.101.217.202:9000/user/has-app/true")
                            .post(reqBody)
                            .build();

                    Response response = client.newCall(request).execute();

                    Log.e("RECEIVED", String.valueOf(response));
                    String getVala = response.body().toString();
                    Log.e("RECEIVED", getVala);
                    Log.e("lksjkljkldjsd","kkkkkw");
                } catch (Exception e) {
                    Log.e("token",e.toString());
                }
            }
            if(strings[0].equals("tokenDelete")){
                try{
                    Log.d("token","in registerAppWithToken and try" +  PreferenceManager.getString(mContext, "sessionID"));
                    OkHttpClient client = new OkHttpClient();

                    JSONObject jsonInput = new JSONObject();

                    jsonInput.put("token", strings[1]);

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);
                    Request request = new Request.Builder()
                            .addHeader("Cookie", PreferenceManager.getString(mContext, "sessionID"))
                            .url("http://101.101.217.202:9000/user/token")
                            .delete(reqBody)
                            .build();

                    Response response = client.newCall(request).execute();

                    Log.e("RECEIVED", String.valueOf(response));
                    String getVala = response.body().toString();
                    Log.e("RECEIVED", getVala);
                    Log.e("lksjkljkldjsd","kkkkkw");
                } catch (Exception e) {
                    Log.e("token",e.toString());
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
                            .addHeader("Cookie", PreferenceManager.getString(mContext, "sessionID"))
                            .url("http://101.101.217.202:9000/home-in/user/alarm")
                            .post(reqBody)
                            .build();

                    Log.e("homeInPost", latitude +" "+ longitude);

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
                            .addHeader("Cookie", PreferenceManager.getString(mContext, "sessionID"))
                            .url("http://101.101.217.202:9000/home-out/user/alarm")
                            .post(reqBody)
                            .build();

                    Log.e("homeOutPost", latitude +" "+ longitude);

                    Response response = client.newCall(request).execute();

                    String message = response.body().string();
                    System.out.println(message);

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
            else if(strings[0].equals("autoLogin")){
                try{

                    Log.e("START", "autoLogin");
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonInput = new JSONObject();

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");

                    Request request = new Request.Builder()
                            .addHeader("Cookie", PreferenceManager.getString(mContext, "sessionID"))
                            .url("http://101.101.217.202:9000/check-session")
                            .build();

                    Response response = client.newCall(request).execute();
                    JSONObject jsonRes =  new JSONObject(response.body().string());
                    Log.e("res JSON", String.valueOf(jsonRes));

                    String name = (String) jsonRes.getString("name");
                    getVal = jsonRes.getString("code");

                    Log.e("NAME", name);

                    Log.e("CODE", getVal);
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
            else if(strings[0].equals("getReceivers")){
                try{

                    Log.e("GET", "Receivers");
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonInput = new JSONObject();

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");

                    Request request = new Request.Builder()
                            .addHeader("Cookie", PreferenceManager.getString(mContext, "sessionID"))
                            .url("http://101.101.217.202:9000/user/queue")
                            .build();

                    Response response = client.newCall(request).execute();


                    getVal = response.body().string();
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        }

        Log.e("HttpRequest GOT",getVal);
        return getVal;
    }
}
