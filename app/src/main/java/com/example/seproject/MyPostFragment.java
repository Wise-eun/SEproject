package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class MyPostFragment extends Fragment {
    ListView post_listView;
    ListItemAdapter post_adapter;

    public MyPostFragment(){}
    public static MyPostFragment newInstance(){
        return new MyPostFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mypost, container, false);
        ListView listView = (ListView) view.findViewById(R.id.post_listview);
        post_adapter = new ListItemAdapter();
        post_adapter.addItem(new ListItem("writer", "[ㅇㅇ공모전] 같이 하실 분", "(1/4)", "D-16"));
        post_adapter.addItem(new ListItem("writer", "[ㄴㄴ공모전] 같이 하실 분", "(2/4)", "D-16"));
        post_adapter.addItem(new ListItem("writer", "[ㅁㅁ공모전] 같이 하실 분", "(3/4)", "D+20"));
        post_adapter.addItem(new ListItem("writer", "[ㅅㅅ공모전] 같이 하실 분", "(4/4)", "완료"));

        listView.setAdapter(post_adapter);
        return view;
    }
}
