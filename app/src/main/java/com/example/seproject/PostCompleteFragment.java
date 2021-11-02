package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostCompleteFragment extends Fragment {
    public static int pid; //게시물 ID
    public static PostCompleteFragment newInstance(){
        return new PostCompleteFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.post_complete, container, false);
        RecyclerView post_member_listview = (RecyclerView)view.findViewById(R.id.post_member_listview);

        PostMember_ListItemAdapter adapter = new PostMember_ListItemAdapter();

        post_member_listview.setAdapter(adapter);
        post_member_listview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        ArrayList<PostMember_ListItem> items = new ArrayList<PostMember_ListItem>();
//        items.add(new PostMember_ListItem("User_1"));
//        items.add(new PostMember_ListItem("User_2"));
//        items.add(new PostMember_ListItem("User_3"));
//        items.add(new PostMember_ListItem("User_4"));
//        items.add(new PostMember_ListItem("User_5"));

        adapter.setPostMemberItems(items);
        return view;
    }
}