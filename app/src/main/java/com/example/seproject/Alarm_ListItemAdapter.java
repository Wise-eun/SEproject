package com.example.seproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Alarm_ListItemAdapter extends BaseAdapter {

    private ArrayList<Alarm_ListItem> items = new ArrayList<Alarm_ListItem>();
    private Context context;
    private OnDeleteClickListener listener;

    public interface OnDeleteClickListener{
        void onDelete(View v, int pos, String type);
    }

    public Alarm_ListItemAdapter(OnDeleteClickListener listener){
        super();
        this.listener = listener;
    }

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
        Button accept_btn = convertView.findViewById(R.id.accept_btn);
        Button refuse_btn = convertView.findViewById(R.id.refuse_btn);

        content_tv.setText(listItem.getContent());

        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(view, position, "accept");
            }
        });

        refuse_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                listener.onDelete(view, position, "refuse");
            }
        });

        return convertView;
    }

    public void addItem(Alarm_ListItem item){
        items.add(item);
    }
}
