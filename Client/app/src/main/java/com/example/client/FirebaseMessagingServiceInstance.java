package com.example.client;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FirebaseMessagingServiceInstance extends FirebaseMessagingService {

    private static final String TAG = "Firebase";
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public String FirebaseAlarmMessage = "";
    public String FirebaseAlarmMessageCheck = "";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        //sendRegistrationToServer(token);
        writeTokenLog(this,token);
        Log.d(TAG,token);
        registerAppWithToken(token);

    }

    public void registerAppWithToken(String token){
        try{
            OkHttpClient client = new OkHttpClient();

            JSONObject jsonInput = new JSONObject();

            jsonInput.put("token",token);
            jsonInput.put("userId",R.string.user_id);

            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody reqBody = RequestBody.create(jsonInput.toString(),JSON);

            Request request = new Request.Builder()
                    .url("http://101.101.217.202:9000/user/19/has-app/true")
                    .post(reqBody)
                    .build();

            Response response = client.newCall(request).execute();

            String message = response.body().string();
            System.out.println(message);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("response","in onMessageReceived");
        if (remoteMessage != null ) {
            //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //sendNotification(remoteMessage.getNotification().getBody());
            Log.d("response",remoteMessage.getData().toString());
            sendNotification(remoteMessage.getData());

            FirebaseAlarmMessage = remoteMessage.getData().toString();

        }
        Log.d("response","before out onMessageReceived");
    }

    private void sendNotification(Map<String,String> map) {
        Intent intent = new Intent(this, ClientMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String notificationTitle = "";
        String notificationBody = "";

        if(map.get("alarmType").equals("nonactive")){
            notificationTitle = "활동 미감지 알림";
            notificationBody = map.get("senderName") + "님의 활동이 감지되지 않아요.";
        }
        else if(map.get("alarmType").equals("fall")){
            notificationTitle = "쓰러짐 알림";
            notificationBody = map.get("senderName") + "님이 쓰러졌어요.";
        }


        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    public void writeTokenLog(Context context, String content){
        String SaveFolderPath = context.getExternalFilesDir("") + "/token";
        File dir = new File(SaveFolderPath);

        if (!dir.exists()){
            dir.mkdirs();
        }

        File txtfile = new File(dir + "/my_token.txt") ;
        FileWriter fw = null ;

        String text = content;

        try {
            // open file.
            fw = new FileWriter(txtfile, true) ;

            // write file.
            fw.write(text) ;

        } catch (Exception e) {
            e.printStackTrace() ;
        }

        // close file.
        if (fw != null) {
            // catch Exception here or throw.
            try {
                fw.close() ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
