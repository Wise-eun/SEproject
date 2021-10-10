package com.example.seproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Alarm_ListItemAdapter extends BaseAdapter {

    ArrayList<Alarm_ListItem> items = new ArrayList<Alarm_ListItem>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        Alarm_ListItem listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.alarm_listview_item, parent, false);
        }

        TextView content_tv = convertView.findViewById(R.id.content_tv);

        content_tv.setText(listItem.getContent());

        return convertView;
    }

    public void addItem(Alarm_ListItem item){
        items.add(item);
    }
}
