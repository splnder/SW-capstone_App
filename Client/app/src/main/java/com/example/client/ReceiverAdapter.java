package com.example.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReceiverAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ReceiverItem> data; //Item 목록을 담을 배열
    private int layout;

    public ReceiverAdapter(Context context, int layout, ArrayList<ReceiverItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() { //리스트 안 Item의 개수를 센다.
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        ReceiverItem friendsItem = data.get(position);



        TextView info = (TextView) convertView.findViewById(R.id.name);
        info.setText(friendsItem.getName());


        TextView phone = (TextView) convertView.findViewById(R.id.phone);
        phone.setText(friendsItem.getPhone());


        return convertView;
    }
}
