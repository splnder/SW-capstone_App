package com.example.client;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Writer {

    public void writeTimeLog(Context context, String content){
        String SaveFolderPath = context.getExternalFilesDir("") + "/on_off_log";
        //Log.d("write_log",SaveFolderPath);
        File dir = new File(SaveFolderPath);

        if (!dir.exists()){
            //Log.d("Dir","not_exist");
            dir.mkdirs();
        }

        File txtfile = new File(dir + "/log.txt") ;
        FileWriter fw = null ;

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String formatDate = sdfNow.format(date);

        String text = formatDate + " " + content + "\n";

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
