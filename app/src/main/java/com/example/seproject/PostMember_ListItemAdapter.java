package com.example.seproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostMember_ListItemAdapter extends RecyclerView.Adapter<Holder> {
    ArrayList<PostMember_ListItem> items;

    PostMember_ListItemAdapter(ArrayList<PostMember_ListItem> list) { this.items = items; }

    @NonNull @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.post_member_listview_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
    }

    @Override public int getItemCount() {
        return items.size();
    }
}

class Holder extends RecyclerView.ViewHolder {
    TextView user_name_tv;
    public Holder(@NonNull View itemView) {
        super(itemView);
        user_name_tv = itemView.findViewById(R.id.user_name_tv);
    }
}