package com.example.seproject;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class WorkFragment extends Fragment {
    ListView listView;
    ListItemAdapter adapter;

    public WorkFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_work, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        adapter = new ListItemAdapter();
        adapter.addItem(new ListItem("writer", "[ㅇㅇ공모전] 같이 하실 분", "(1/4)", "D-16"));
        adapter.addItem(new ListItem("writer", "[ㄴㄴ공모전] 같이 하실 분", "(2/4)", "D-16"));
        adapter.addItem(new ListItem("writer", "[ㅁㅁ공모전] 같이 하실 분", "(3/4)", "D-16"));
        adapter.addItem(new ListItem("writer", "[ㅅㅅ공모전] 같이 하실 분", "(4/4)", "D-16"));
        adapter.addItem(new ListItem("writer", "[ㄹㄹ공모전] 같이 하실 분", "(1/5)", "D-16"));

        listView.setAdapter(adapter);
        return view;


//        return inflater.inflate(R.layout.fragment_work, container, false);
    }
}