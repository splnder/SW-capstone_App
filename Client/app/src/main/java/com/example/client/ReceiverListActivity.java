package com.example.client;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReceiverListActivity extends Activity {
    ListView receiverView;
    private SimpleAdapter adapter = null;
    private ArrayList<ReceiverItem> receiverList = new ArrayList<ReceiverItem>();
    private ReceiverListActivity thisAct =this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        receiverView =(ListView)findViewById(R.id.receiverList);


        String strList;
        try{

            HttpRequest httpRequest = new HttpRequest(getApplicationContext());
            strList = httpRequest.execute("getReceivers").get();

            //jsonParser(strList);

            JSONArray jsonArr = new JSONArray(strList);

            for(int i=0; i<jsonArr.length(); i++){
                JSONObject jObject = new JSONObject(jsonArr.get(i).toString());

                receiverList.add(new ReceiverItem(jObject.getInt("queueId"), jObject.getString("name"), jObject.getString("phone_number")));
            }



        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        receiverView = (ListView) findViewById(R.id.receiverList) ;
        ReceiverAdapter adapter = new ReceiverAdapter(this, R.layout.receiver_item, receiverList);
        receiverView.setAdapter(adapter);


        receiverView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Toast.makeText(thisAct, receiverList.get(position).getId() + "", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public boolean jsonParser(String jsonString){


        if (jsonString == null ) return false;

        jsonString = jsonString.replace("jsonFlickrApi(", "");
        jsonString = jsonString.replace(")", "");

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject photos = jsonObject.getJSONObject("photos");
            JSONArray photo = photos.getJSONArray("photo");

            receiverList.clear();

            for (int i = 0; i < photo.length(); i++) {
                JSONObject receiverInfo = photo.getJSONObject(i);

                String queueId = receiverInfo.getString("queueId");
                String name = receiverInfo.getString("name");
                String auth = receiverInfo.getString("auth");
                String phone_number = receiverInfo.getString("phone_number");
                String address = receiverInfo.getString("address");
                String alarmType = receiverInfo.getString("alarmType");

                HashMap<String, String> receiverInfoMap = new HashMap<String, String>();
                receiverInfoMap.put("queueId", queueId);
                receiverInfoMap.put("name", name);
                receiverInfoMap.put("auth", auth);
                receiverInfoMap.put("phone_number", phone_number);
                receiverInfoMap.put("address", address);
                receiverInfoMap.put("alarmType", alarmType);

                //receiverList.add(receiverInfoMap);

            }

            return true;
        } catch (JSONException e) {

            Log.e("ERROR", e.toString() );
        }

        return false;
    }

}
