package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class ReceiverAccept extends Activity {
    Integer receiverID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiver_accept_view);


        final TextView receiverInfo = (TextView)findViewById(R.id.receiverInfo);
        receiverID = getIntent().getIntExtra("receiverID", -9);
        Integer auth = getIntent().getIntExtra("auth", -9);
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String address = getIntent().getStringExtra("address");

        String authName = auth ==1 ? "보호자" : "보호센터";

        receiverInfo.setText(authName + "\n이름 : " +name+ "\n전화번호 : " +phone+ "\n주소 : " +address);



    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        return;
    }


    public void reject(View view) {

        Intent data = new Intent();
        data.putExtra("id", receiverID);
        try{
            HttpRequest httpRequest = new HttpRequest(getApplicationContext());
            String res = httpRequest.execute("rejectReceiver", String.valueOf(receiverID)).get();
            if(res.equals(String.valueOf(receiverID))){
                Toast.makeText(this, "성공적으로 제거되었습니다", Toast.LENGTH_LONG).show();

                data.putExtra("status", true);
                setResult(4,data);
            }
            else{
                Toast.makeText(this, "오류 발생"+res, Toast.LENGTH_LONG).show();

                data.putExtra("status", false);
                setResult(4,data);
            }


        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();
    }

    public void accept(View view) {

        Intent data = new Intent();
        data.putExtra("id", receiverID);
        try{
            HttpRequest httpRequest = new HttpRequest(getApplicationContext());
            String res = httpRequest.execute("acceptReceiver", String.valueOf(receiverID)).get();
            if(!res.isEmpty()){
                Toast.makeText(this, "성공적으로 추가되었습니다", Toast.LENGTH_LONG).show();

                data.putExtra("status", true);
                setResult(4,data);
            }
            else{
                Toast.makeText(this, "오류 발생"+res, Toast.LENGTH_LONG).show();

                data.putExtra("status", false);
                setResult(4,data);
            }
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();
    }
}

