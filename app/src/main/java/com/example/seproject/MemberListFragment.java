package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class MemberListFragment extends Fragment {
    ListView listView;
    Member_ListItemAdapter adapter;

    public MemberListFragment() {}

    public static MemberListFragment newInstance(){
        return new MemberListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.member_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.member_listview);
        adapter = new Member_ListItemAdapter();
        adapter.addItem(new Member_ListItem("User_1"));
        adapter.addItem(new Member_ListItem("User_2"));
        adapter.addItem(new Member_ListItem("chuseok"));
        adapter.addItem(new Member_ListItem("elatedae"));
        adapter.addItem(new Member_ListItem("wise_eun"));
        adapter.addItem(new Member_ListItem("my girlfriend♥"));

        listView.setAdapter(adapter);
        return view;
    }
}