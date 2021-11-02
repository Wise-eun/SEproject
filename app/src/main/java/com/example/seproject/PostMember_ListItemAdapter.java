package com.example.seproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostMember_ListItemAdapter extends RecyclerView.Adapter<PostMember_ListItemAdapter.ViewHolder> {
    ArrayList<PostMember_ListItem> items;

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView user_name_tv;
        ImageView leader_img;

        public ViewHolder(View itemView){
            super(itemView);
            user_name_tv = (TextView) itemView.findViewById(R.id.user_name_tv);
            leader_img = (ImageView) itemView.findViewById(R.id.leader_img);
        }
        void onBind(PostMember_ListItem item){
            user_name_tv.setText(item.getName());
            if(item.getIsLeader()){
                leader_img.setVisibility(View.VISIBLE);
            }
            else{
                leader_img.setVisibility(View.INVISIBLE);
            }
        }
    }

    @NonNull
    @Override
    public PostMember_ListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.post_member_listview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostMember_ListItemAdapter.ViewHolder viewHolder, int position) {
        viewHolder.onBind(items.get(position));
    }

    public void setPostMemberItems(ArrayList<PostMember_ListItem> items){
        this.items = items;
        notifyDataSetChanged();
    }
    @Override public int getItemCount() {
        return items.size();
    }

}