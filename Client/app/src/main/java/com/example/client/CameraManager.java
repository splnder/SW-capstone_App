package com.example.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.hardware.Camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.os.IBinder;

public class CameraManager extends  Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


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
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                            if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                                pictureFileDir.mkdirs();
                            }
                            Log.e("CALL", "CALL getApplicationContext()");
                            Log.e(getApplicationContext() + "","-OBJECT:");

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                            String date = dateFormat.format(new Date());
                            String photoName = PreferenceManager.getInt(getApplicationContext(), "lastEventID") + ".jpg";
                            String filename = pictureFileDir.getPath() + File.separator + photoName;
                            File mainPicture = new File(filename);


                            try {
                                FileOutputStream fos = new FileOutputStream(mainPicture);
                                fos.write(stream.toByteArray());
                                fos.close();
                                HttpSendImage.sendImage(mainPicture, getApplicationContext());
                                Log.e("ERROR", "sent");
                            } catch (Exception error) {
                                Log.e("ERROR", "send failed");
                            }
                            camera.release();
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
        return super.onStartCommand(intent, flags, startId);
    }

}
