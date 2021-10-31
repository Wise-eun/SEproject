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
        Context context = parent.getContext();
        Alarm_ListItem listItem = items.get(position);
        int type = listItem.getType(); //type=0 참여신청, type=1 참여신청거절, type=2 참여신청수락, type=3 메신저
        String sender = listItem.getSender();
        String title = listItem.getTitle();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.alarm_listview_item, parent, false);
        }

        TextView content_tv = convertView.findViewById(R.id.content_tv);
        Button accept_btn = convertView.findViewById(R.id.accept_btn);
        Button refuse_btn = convertView.findViewById(R.id.refuse_btn);

        if(type==0){
            content_tv.setText(sender + "님이 [" + title + "]글에 참여 신청을 하였습니다.");
        }
        else if(type==1){
            content_tv.setText(sender + "님이 [" + title + "]글의 참여 신청을 거절하였습니다.");
        }
        else if(type==2){
            content_tv.setText(sender + "님이 [" + title + "]글에 참여 신청을 수락하였습니다.");
        }
        else{
            content_tv.setText(sender + "님이 메시지를 보냈습니다.");
        }

        if(type==0){
            //평가 가능한 상태
            accept_btn.setVisibility(View.VISIBLE);
            refuse_btn.setVisibility(View.VISIBLE);
        }
        else{
            accept_btn.setVisibility(View.GONE);
            refuse_btn.setVisibility(View.GONE);
        }


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
