 package com.example.client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

 public class ClientMainActivity extends AppCompatActivity
{
    Toolbar toolbar;
    private Button button1, button2, button3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        Log.d("mainActivity", "start....");
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

        Log.d("mainActivity", "end.....");

        Intent intent = new Intent(ClientMainActivity.this, DetectFall.class);
        startService(intent);

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
}
