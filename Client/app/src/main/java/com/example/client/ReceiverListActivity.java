package com.example.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ReceiverListActivity extends Activity {
    ListView receiverView;
    private ReceiverAdapter adapter = null;
    private ArrayList<ReceiverItem> receiverList = new ArrayList<ReceiverItem>();
    private ReceiverListActivity thisAct =this;
    private JSONArray jsonArr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        receiverView =(ListView)findViewById(R.id.receiverList);


        String strList;
        try{

            HttpRequest httpRequest = new HttpRequest(getApplicationContext());
            strList = httpRequest.execute("getReceiverList").get();

            jsonArr = new JSONArray(strList);

            for(int i=0; i<jsonArr.length(); i++){
                JSONObject jObject = new JSONObject(jsonArr.get(i).toString());

                receiverList.add(new ReceiverItem(jObject.getInt("id"), jObject.getString("name"), jObject.getString("phone_number"), jObject.getInt("auth"), jObject.getString("address")));
            }



        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        updateListView();

    }

    void updateListView(){

        receiverView = (ListView) findViewById(R.id.receiverList) ;
        adapter = new ReceiverAdapter(this, R.layout.receiver_item, receiverList);
        receiverView.setAdapter(adapter);


        receiverView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                Intent setReceiverIntent = new Intent(getApplicationContext(), ReceiverListDelete.class);
                setReceiverIntent.putExtra("receiverID",receiverList.get(position).getId());
                setReceiverIntent.putExtra("name",receiverList.get(position).getName());
                setReceiverIntent.putExtra("phone",receiverList.get(position).getPhone());
                setReceiverIntent.putExtra("auth",receiverList.get(position).getAuth());
                setReceiverIntent.putExtra("address",receiverList.get(position).getAddress());
                startActivityForResult(setReceiverIntent, 4);

            }
        });
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data){
        if(req == 4)
            if(data.getBooleanExtra("status", false)){
                receiverList.clear();
                Log.e("REMAIN",receiverList.toString());
                int removedID = data.getIntExtra("id", -99999);
                try{
                    for(int i=0; i<jsonArr.length(); i++){
                        JSONObject jObject = new JSONObject(jsonArr.get(i).toString());

                        if(removedID != jObject.getInt("id"))
                            receiverList.add(new ReceiverItem(jObject.getInt("id"), jObject.getString("name"), jObject.getString("phone_number"), jObject.getInt("auth"), jObject.getString("address")));
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESULT",receiverList.toString());

                updateListView();

            }


    }

}
