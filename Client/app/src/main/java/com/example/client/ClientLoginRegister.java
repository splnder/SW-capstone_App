package com.example.client;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ClientLoginRegister extends StringRequest
{
    final static private String URL = "http://118.67.128.105/phpmyadmin/ClientRegister.php";
    private Map<String, String> map;

    public ClientLoginRegister(String userID, String userPassword, String userName, int userBirth, String userPhone, String userAddress, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("userAge", userBirth + "");
        map.put("userPhone", userPhone);
        map.put("userAddress", userAddress);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return map;
    }
}