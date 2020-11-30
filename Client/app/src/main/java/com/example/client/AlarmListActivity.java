package com.example.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AlarmListActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ListView listview;
    private ArrayAdapter adapter;
    private static final String TAG = AlarmListActivity.class.getSimpleName();
    private static List<GetAlarm> alarmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new HttpAsyncTask().execute("http://101.101.217.202:9000/user/20/alarm");
    }

    private static class HttpAsyncTask extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params){

            String strUrl = params[0];

            try {
                Request request = new Request.Builder()
                        .url(strUrl)
                        .build();

                Response response = client.newCall(request).execute();

                JSONArray jsonArray = new JSONArray(response.body().string());
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String user = jsonObject.getString("user");
                    int id = jsonObject.getInt("id");
                    String alarm = jsonObject.getString("alarm");
                    GetAlarm getAlarm = new GetAlarm(user, id, alarm);
                    alarmList.add(getAlarm);

                }
                //Log.d(TAG, "doInBackground: " + response.body().string());
                //System.out.println(response.body().string());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    protected void listView() {

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, alarmList);

        listview = findViewById(R.id.listview1);
        listview.setAdapter(adapter) ;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}