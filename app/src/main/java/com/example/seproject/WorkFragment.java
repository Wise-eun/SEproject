package com.example.seproject;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class WorkFragment extends Fragment {
    ListView listView;
    Post_ListItemAdapter adapter;

    public WorkFragment() {}

    public static WorkFragment newInstance(){
        return new WorkFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_work, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        adapter = new Post_ListItemAdapter();
        adapter.addItem(new Post_ListItem("writer", "[ㅇㅇ공모전] 같이 하실 분", "(1/4)", "D-16"));
        adapter.addItem(new Post_ListItem("writer", "[ㄴㄴ공모전] 같이 하실 분", "(2/4)", "D-16"));
        adapter.addItem(new Post_ListItem("writer", "[ㅁㅁ공모전] 같이 하실 분", "(3/4)", "D-16"));
        adapter.addItem(new Post_ListItem("writer", "[ㅅㅅ공모전] 같이 하실 분", "(4/4)", "D-16"));
        adapter.addItem(new Post_ListItem("writer", "[ㄹㄹ공모전] 같이 하실 분", "(1/5)", "D-16"));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Post_ListItem item = (Post_ListItem) adapter.getItem(position);
                Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).replaceFragment(PostDetailFragment.newInstance());
            }
        });

        listView.setAdapter(adapter);
        return view;
    }
}