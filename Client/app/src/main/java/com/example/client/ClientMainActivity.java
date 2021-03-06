 package com.example.client;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.ExecutionException;


 public class ClientMainActivity extends AppCompatActivity
 {
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;
    boolean mainStatus = false;
    FirebaseMessagingServiceInstance FMS = new FirebaseMessagingServiceInstance();


     private static final int GPS_ENABLE_REQUEST_CODE = 2001;
     private static final int PERMISSIONS_REQUEST_CODE = 100;
     String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //private Intent gpsListenerIntent;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);


        if(getIntent().hasExtra("alert")){
            Log.e("ALERT:", getIntent().getStringExtra("alert"));

            Intent popIntent = new Intent(getApplicationContext(), AlertChanceActivity.class);
            popIntent.putExtra("alert",getIntent().getStringExtra("alert"));
            startActivityForResult(popIntent, 1);
        }

        mainStatus = true;
        ignoreBatteryOptimization();
        checkPermission();
        checkCameraPermission();

        if (!checkLocationServicesStatus()) {
            Log.e("in oncreate","checkPermission");
            showDialogForLocationServiceSetting();
        }else {
            Log.e("in oncreate","checkPermission");
            checkRunTimePermission();
        }

        //기본 SharedPreferences 환경과 관련된 객체를 얻어옵니다.
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // SharedPreferences 수정을 위한 Editor 객체를 얻어옵니다.
        editor = preferences.edit();
        saveDate();

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

                        editor.putString("token",token[0]);
                        editor.apply();

                        // Log and toast
                        Log.d("mainActivity", token[0]);
                    }
                });

        editor.putString("token","aaaa");
        editor.apply();






        //자동로그인 부분
        autoLogin();


        //gpsListenerIntent = new Intent(getApplicationContext(),GPSListener.class);
        //startService(gpsListenerIntent);
    }

     private void autoLogin() {
         String code;
         Integer auth= -9;
         if(com.example.client.PreferenceManager.getBoolean(getApplicationContext(), "isSessionExist")){
             try {
                 HttpRequest httpRequest = new HttpRequest(getApplicationContext());
                 code = httpRequest.execute("autoLogin").get();
                 Log.e("code", "code is : " + code);
                 if(!code.equals(""))
                     auth= Integer.valueOf(code);

                 if(auth == 0){//피보호자
                     Log.e("대상","피보호자");
                     Intent settingIntent = new Intent(getApplicationContext(), SettingActivity.class);
                     startActivity(settingIntent);

                     overridePendingTransition(R.anim.horizon_exit, R.anim.none);
                     finish();
                 }

                 else if(auth ==1 || auth == 2) {//보호자, 보호센터
                     Log.e("대상","보호자, 센터");

                     Intent webViewIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                     startActivity(webViewIntent);
                     finish();
                 }


             } catch (ExecutionException e) {
                 e.printStackTrace();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }

     public void saveDate(){
        editor.putString("home_latitude","37.2725965");
        editor.putString("home_longitude","127.0422395");
        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Intent activeIntent = new Intent(getApplicationContext(),ActivenessCheckService.class);
        //startService(activeIntent);
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
                Intent intent2 = new Intent(ClientMainActivity.this, OptionActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if(gpsListenerIntent!=null){
        //    stopService(gpsListenerIntent);
        //}
        mainStatus = false;
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

    public void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("camera", "권한 설정 완료");
            } else {
                Log.d("camera", "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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

        else if(requestCode == GPS_ENABLE_REQUEST_CODE){
            //사용자가 GPS 활성 시켰는지 검사
            if (checkLocationServicesStatus()) {
                if (checkLocationServicesStatus()) {

                    Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                    checkRunTimePermission();
                    return;
                }
            }
        }
    }

     @Override
     public void onRequestPermissionsResult(int permsRequestCode,
                                            @NonNull String[] permissions,
                                            @NonNull int[] grandResults) {

         if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

             // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

             boolean check_result = true;


             // 모든 퍼미션을 허용했는지 체크합니다.

             for (int result : grandResults) {
                 if (result != PackageManager.PERMISSION_GRANTED) {
                     check_result = false;
                     break;
                 }
             }


             if ( check_result ) {

                 //위치 값을 가져올 수 있음
                 ;
             }
             else {
                 // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                 if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                         || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                     Toast.makeText(ClientMainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                     finish();


                 }else {

                     Toast.makeText(ClientMainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                 }
             }

         }
     }

     void checkRunTimePermission(){

         //런타임 퍼미션 처리
         // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
         int hasFineLocationPermission = ContextCompat.checkSelfPermission(ClientMainActivity.this,
                 Manifest.permission.ACCESS_FINE_LOCATION);
         int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(ClientMainActivity.this,
                 Manifest.permission.ACCESS_COARSE_LOCATION);


         if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                 hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

             // 2. 이미 퍼미션을 가지고 있다면
             // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


             // 3.  위치 값을 가져올 수 있음



         } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

             // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
             if (ActivityCompat.shouldShowRequestPermissionRationale(ClientMainActivity.this, REQUIRED_PERMISSIONS[0])) {

                 // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                 Toast.makeText(ClientMainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                 // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
             }
             // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
             // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
             ActivityCompat.requestPermissions(ClientMainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);

         }

     }

     //여기부터는 GPS 활성화를 위한 메소드들
     private void showDialogForLocationServiceSetting() {

         AlertDialog.Builder builder = new AlertDialog.Builder(ClientMainActivity.this);
         builder.setTitle("위치 서비스 비활성화");
         builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                 + "위치 설정을 수정하실래요?");
         builder.setCancelable(true);
         builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int id) {
                 Intent callGPSSettingIntent
                         = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                 startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
             }
         });
         builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int id) {
                 dialog.cancel();
             }
         });
         builder.create().show();
     }



     public boolean checkLocationServicesStatus() {
         LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

         return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                 || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
     }



    public void mbonk(View v){

        Toast.makeText(this, "CALL CAMERA", Toast.LENGTH_SHORT).show();
        Log.e("CAMERA", "CALL CAMERA" + 1);
        new Thread(new Runnable() {
            @Override public void run() {
                Intent cameraIntent = new Intent(getApplicationContext(), CameraManager.class);
                startService(cameraIntent);
            }
        }).start();
    }

     public void mbonk2(View v){

         Toast.makeText(this, "CALL LOGIN", Toast.LENGTH_SHORT).show();
         Intent loginIntent = new Intent(getApplicationContext(),Tlogin.class);
         startActivity(loginIntent);

     }
     public boolean loginOnClick(View v){

         TextInputLayout idField = findViewById(R.id.idField);
         String id= idField.getEditText().getText().toString().trim();

         TextInputLayout pwField = findViewById(R.id.pwField);
         String pw= pwField.getEditText().getText().toString().trim();

         boolean empty = false;
         if(id.equals("")){
             idField.setError("값이 입력되지 않았습니다");
             empty = true;
         }else{idField.setError(null);}
         if(pw.equals("")) {
             pwField.setError("값이 입력되지 않았습니다");
             empty = true;
         }else{pwField.setError(null);}
         Log.w("login","로그인 하는중");

         if(empty) return false;

         return login(id,pw);
     }
     public boolean startCheck(View v){
         Log.e("시작","시작");


         return true;
     }


     private boolean login(String id, String pw){

         Integer auth=-9;

         try {

             Log.w("앱에서 보낸값",id+", "+pw);

             HttpRequest httpRequest = new HttpRequest(getApplicationContext());
             String result = httpRequest.execute("login",id,pw).get();

             Log.e("받은값",result);
             auth= Integer.valueOf(result);

         } catch (Exception e) {
             Log.e("에러",e.getMessage());
             return false;
         }

         if(auth == 0){//피보호자
             Log.e("대상","피보호자");
             Intent settingIntent = new Intent(getApplicationContext(), SettingActivity.class);
             startActivity(settingIntent);

             finish();
             overridePendingTransition(R.anim.horizon_exit, R.anim.none);
         }

         else if(auth ==1 || auth == 2) {//보호자, 보호센터
             HttpRequest httpRequestForToken = new HttpRequest(getApplicationContext());
             preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
             Log.e("ddd",preferences.getString("token","0")+"djkjlkjsk");
             httpRequestForToken.execute("tokenPost", preferences.getString("token","0"));
             Log.e("대상","보호자, 센터");
             Intent webViewIntent = new Intent(getApplicationContext(), WebViewActivity.class);

             finish();
             startActivity(webViewIntent);
         }

         else{
             Log.e("에러","잘못 기입");

             if(auth ==-100){
                 Log.e("오기입","비번틀림");
                 return false;
             }
             else if(auth ==-200){
                 Log.e("오기입","아이디틀림");
                 return false;
             }
             Log.e("기타","특수경우");
             return false;
         }

         finish();
         return true;
     }
}
