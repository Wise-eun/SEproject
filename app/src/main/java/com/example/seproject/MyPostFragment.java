package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class MyPostFragment extends Fragment {
    ListView listView;
    Post_ListItemAdapter post_adapter;

    public MyPostFragment(){}
    public static MyPostFragment newInstance(){
        return new MyPostFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mypost, container, false);
        listView = (ListView) view.findViewById(R.id.post_listview);
        post_adapter = new Post_ListItemAdapter();
        post_adapter.addItem(new Post_ListItem("writer", "[ㅇㅇ공모전] 같이 하실 분", "(1/4)", "D-16"));
        post_adapter.addItem(new Post_ListItem("writer", "[ㄴㄴ공모전] 같이 하실 분", "(2/4)", "D-16"));
        post_adapter.addItem(new Post_ListItem("writer", "[ㅁㅁ공모전] 같이 하실 분", "(3/4)", "D+20"));
        post_adapter.addItem(new Post_ListItem("writer", "[ㅅㅅ공모전] 같이 하실 분", "(4/4)", "완료"));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Post_ListItem item = (Post_ListItem) post_adapter.getItem(position);
                Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).replaceFragment(PostDetailFragment.newInstance());
            }
        });

        listView.setAdapter(post_adapter);
        return view;
    }
}
