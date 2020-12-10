package com.example.client;

public class ReceiverItem {
    private int id;
    private String name;
    private String phone;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }


    public ReceiverItem(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;

    }
}
