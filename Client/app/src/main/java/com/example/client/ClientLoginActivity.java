package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientLoginActivity extends AppCompatActivity
{
    private EditText et_id, et_pass;
    private Button btn_login, btn_loginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_loginRegister = findViewById(R.id.btn_loginRegister);

        btn_login.setOnClickListener(new View.OnClickListener()  // 로그인 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {
                final String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean( "success");
                            if(success)
                            {
                                String userId = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");
                                Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ClientLoginActivity.this, ClientMainActivity.class) ;
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPass);

                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                ClientLogin login = new ClientLogin(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ClientLoginActivity.this);
                queue.add(login);
            }
        });

        btn_loginRegister.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ClientLoginActivity.this, ClientLoginRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}