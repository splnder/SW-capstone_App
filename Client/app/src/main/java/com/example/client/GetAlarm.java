package com.example.client;

public class GetAlarm {

    String user;
    int id;
    String alarm;


    public GetAlarm(String user, int id, String alarm) {
        this. user =  user;
        this.id = id;
        this.alarm = alarm;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("getAlarm{");
        sb.append("user=' ").append(user).append('\'');
        sb.append(", id=' ").append(id).append('\'');
        sb.append(", alarme=' ").append(alarm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
