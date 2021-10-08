package com.example.seproject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


public class Msg_ListItemAdapter extends BaseAdapter {
    ArrayList<Msg_ListItem> items = new ArrayList();
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
        Msg_ListItem listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.msg_listview_item, parent, false);
        }


        TextView msg_type_tv = convertView.findViewById(R.id.msg_type_tv);
        TextView msg_content_tv = convertView.findViewById(R.id.msg_content_tv);
        TextView msg_date_tv = convertView.findViewById(R.id.msg_date_tv);

        if(listItem.getType().equals("보낸 메시지")){
            msg_type_tv.setTextColor(Color.RED);
        }


        msg_type_tv.setText(listItem.getType());
        msg_content_tv.setText(listItem.getContent());
        msg_date_tv.setText(listItem.getDate());


        return convertView;
    }

    public void addItem(Msg_ListItem item){
        items.add(item);
    }
}
