package com.example.seproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Post_ListItemAdapter extends BaseAdapter {
    private ArrayList<Post_ListItem> items = new ArrayList<Post_ListItem>();
    private Context context;

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
        Post_ListItem listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.post_listview_item, parent, false);
        }

        TextView writer_tv = convertView.findViewById(R.id.writer_tv);
        TextView title_tv = convertView.findViewById(R.id.title_tv);
        TextView personnel_tv = convertView.findViewById(R.id.personnel_tv);
        TextView day_tv = convertView.findViewById(R.id.day_tv);

        writer_tv.setText(listItem.getWriter());
        title_tv.setText(listItem.getTitle());
        personnel_tv.setText(listItem.getPersonnel());
        day_tv.setText(listItem.getDay());

        return convertView;
    }

    public void addItem(Post_ListItem item){
        items.add(item);
    }

    public void clearItems() {items.clear();}
}
