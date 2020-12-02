package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class AlertActivity extends Activity {
    private static final String NONACTIVE_MSG = "활동 없음";
    private static final String FALLDOWN_MSG = "쓰러짐";
    private AlertActivity popup = this;
    private String event;

    private int picSize = 500;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_view);
        final TextView alertInfo = (TextView)findViewById(R.id.alertText);

        if(getIntent().getStringExtra("alert").equals("activePost")){
            alertInfo.setText(NONACTIVE_MSG);
            event = "A";
        }
        else{
            alertInfo.setText(FALLDOWN_MSG);
            event = "F";
        }

        new Thread(new Runnable() {
            @Override public void run() {
                Intent intent = new Intent(getApplicationContext(), Server.class);
                intent.putExtra(getIntent().getStringExtra("alert"),"true");
                startActivity(intent);
            }
        }).start();

    }


    //확인 버튼 클릭
    public void mOnClose(View v){
        
        //촬영 후 데이터 전송
        openCam();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    private void openCam(){
        Camera camera = null;
        Camera.CameraInfo camInfo = new Camera.CameraInfo();

        int frontCamera = 1;

        Camera.getCameraInfo(frontCamera, camInfo);

        try {
            camera = Camera.open(frontCamera);
            //Log.e("ERROR", "get cam instance");

            try {
                camera.setPreviewTexture(new SurfaceTexture(0));
                camera.startPreview();
                Log.e("ERROR", "set cam preview texture");

                try {
                    camera.takePicture(null, null, new Camera.PictureCallback() {

                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            File pictureFileDir = new File("/sdcard/Captured");

                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, null);
                            //찍은 byte형태의 사진을 bitmap으로 변환
                            Matrix mat = new Matrix();

                            mat.postRotate(270);
                            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            //돌아가있던 이미지를 회전시킴

                            double aspectRatio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
                            int targetHeight = (int) (picSize * aspectRatio);
                            Bitmap result = Bitmap.createScaledBitmap(bitmap, picSize, targetHeight, false);


                            result.compress(Bitmap.CompressFormat.PNG, 100, stream);


                            if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                                pictureFileDir.mkdirs();
                            }

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                            String date = dateFormat.format(new Date());
                            String photoName = PreferenceManager.getInt(getApplicationContext(), "lastEventID") + ".jpg";
                            String filename = pictureFileDir.getPath() + File.separator + photoName;
                            File mainPicture = new File(filename);


                            try {
                                FileOutputStream fos = new FileOutputStream(mainPicture);
                                fos.write(stream.toByteArray());
                                fos.close();
                                HttpSendImage.sendImage(mainPicture, event, getApplicationContext());
                                Log.e("ERROR", "sent");
                            } catch (Exception error) {
                                Log.e("ERROR", "send failed");
                            }
                            camera.release();
                            popup.finish();
                            //콜백함수 끝나고 팝업창 닫기
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                Log.e("ERROR", "fail to set cam preview texture");
                e.printStackTrace();
            }



        } catch (RuntimeException e) {

            //Log.e("ERROR", "cam unavailable");
            //e.printStackTrace();
        }



    }

}