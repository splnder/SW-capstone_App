package com.example.client;

public class ReceiverItem {
    private int id;
    private String name;
    private String phone;
    private int auth;
    private String address;

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getAuth() {return auth;}

    public String getAddress() {return address;}


    public ReceiverItem(int id, String name, String phone, int auth, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.auth = auth;
        this.address = address;
    }
}
