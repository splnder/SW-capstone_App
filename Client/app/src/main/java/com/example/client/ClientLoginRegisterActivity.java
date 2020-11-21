package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientLoginRegisterActivity extends AppCompatActivity
{
    private EditText et_id, et_pass, et_name, et_birth, et_phone, et_address;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login_register);

        et_id = findViewById(R.id.et_id); // 아이디 값 찾기
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_birth = findViewById(R.id.et_birth);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.et_address);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener()  // 회원가입 버튼 클릭
        {
            @Override
            public void onClick(View v)
            {
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                int userBirth = Integer.parseInt(et_birth.getText().toString());
                String userPhone = et_phone.getText().toString();
                String userAddress = et_address.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) // 회원 가입 메세지
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean( "success");
                            if(success)
                            {
                                Toast.makeText(getApplicationContext(), "회원 가입이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ClientLoginRegisterActivity.this, ClientLoginActivity.class) ;
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "회원 가입 입력이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버에 요청
                ClientLoginRegister registerLogin = new ClientLoginRegister(userID, userPass, userName, userBirth, userPhone, userAddress, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ClientLoginRegisterActivity.this);
                queue.add(registerLogin);
            }
        });
    }
}