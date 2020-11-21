package com.example.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AlarmList extends AppCompatActivity {

    Server server;
    FirebaseMessagingServiceInstance FMS = new FirebaseMessagingServiceInstance();
    Toolbar toolbar;
    ListView listview;
    public ArrayList<String> items;
    public ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        items = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(AlarmList.this, android.R.layout.simple_list_item_single_choice, items);

        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        if((server.alarmMessage != null && server.alarmMessage != server.alarmMessageCheck))
        {
            items.add(server.alarmMessage);
        }
        else if((FMS.FirebaseAlarmMessage != null && FMS.FirebaseAlarmMessage != FMS.FirebaseAlarmMessageCheck))
        {
            items.add(FMS.FirebaseAlarmMessage);
        }
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