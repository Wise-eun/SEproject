package com.example.seproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


public class Member_ListItemAdapter extends BaseAdapter {
    ArrayList<Member_ListItem> items = new ArrayList<Member_ListItem>();
    Context context;
    OnClickListener listener;


    public interface OnClickListener{
        void onClick(View v, int pos, String type); //이거 다시 작성
    }

    public Member_ListItemAdapter(OnClickListener listener){
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
        Member_ListItem listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.member_listview_item, parent, false);
        }

        TextView name_tv = convertView.findViewById(R.id.name_tv);
        Button rating_btn = convertView.findViewById(R.id.rating_btn);

        name_tv.setText(listItem.getName());

        name_tv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                listener.onClick(view, position, "name");
            }
        });

        rating_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                listener.onClick(view, position, "rating");
            }
        });

        return convertView;
    }

    public void addItem(Member_ListItem item){
        items.add(item);
    }
}