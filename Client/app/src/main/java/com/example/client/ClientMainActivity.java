 package com.example.client;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Timer;
import java.util.TimerTask;

 public class ClientMainActivity extends AppCompatActivity
{
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;
    Toolbar toolbar;
    private Button button1, button2, button3;
    FirebaseMessagingServiceInstance FMS = new FirebaseMessagingServiceInstance();
    Server server = new Server();
    AlarmList alarmlist = new AlarmList();
    public android.view.View View;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        ignoreBatteryOptimization();
        checkPermission();

        Intent activeIntent = new Intent(getApplicationContext(),ActivenessCheckService.class);
        startService(activeIntent);
        Log.e("error","165161656156165156165156");


        if(getIntent().hasExtra("alert")){
            Log.e("error", getIntent().getStringExtra("alert"));
            Intent popIntent = new Intent(getApplicationContext(),POPActivity.class);
            startActivityForResult(popIntent, 1);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }

        //FCM token
        final String[] token = {""};
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("mainActivity", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token[0] = task.getResult();

                        // Log and toast
                        Log.d("mainActivity", token[0]);
                        Toast.makeText(ClientMainActivity.this, token[0], Toast.LENGTH_SHORT).show();
                    }
                });



        Intent intent = new Intent(ClientMainActivity.this, DetectFall.class);
        startService(intent);

        Timer timer = new Timer();

        TimerTask TT = new TimerTask() {
            @Override
            public void run() {
                Popup1(View);
                Popup2(View);
            }
        };
        timer.schedule(TT, 0, 1000);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {
                Intent intent3 = new Intent(ClientMainActivity.this, ActivityList.class);
                startActivity(intent3);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {
                Intent intent4 = new Intent(ClientMainActivity.this, FalldownList.class);
                startActivity(intent4);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {
                Intent intent5 = new Intent(ClientMainActivity.this, GPSList.class);
                startActivity(intent5);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item1:
                Intent intent = new Intent(ClientMainActivity.this, OptionActivity.class);
                startActivity(intent);
                break;

            case R.id.item2:
                Intent intent2 = new Intent(ClientMainActivity.this, AlarmList.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Popup1(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("알 림");
        dialog.setMessage(server.alarmMessage);
        dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "확인", Toast.LENGTH_SHORT).show();
            }
        });
        if (server.alarmMessage != server.alarmMessageCheck) {
            dialog.show();
            server.alarmMessageCheck = server.alarmMessage;
        }
    }

    public void Popup2(View view)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("알 림");
        dialog.setMessage(FMS.FirebaseAlarmMessage);
        dialog.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "확인", Toast.LENGTH_SHORT).show();
            }
        });
        if(FMS.FirebaseAlarmMessage != FMS.FirebaseAlarmMessageCheck) {
            dialog.show();
            FMS.FirebaseAlarmMessageCheck = FMS.FirebaseAlarmMessage;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent( getApplicationContext(),ActivenessCheckService.class);
        stopService(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void ignoreBatteryOptimization(){
        PowerManager pm= (PowerManager) getSystemService(Context.POWER_SERVICE);
        String packageName= getPackageName();
        if (pm.isIgnoringBatteryOptimizations(packageName) ){


        } else {    // 메모리 최적화가 되어 있다면, 풀기 위해 설정 화면 띄움.
            Intent intent=new Intent();
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + packageName));
            startActivityForResult(intent,0);
        }
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(this)) {              // 체크
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                //Log.d("not agree","not agree");
                onDestroy();
            }
        }
    }
}
