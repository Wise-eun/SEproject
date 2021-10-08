package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CategoryFragment extends Fragment {
    ListView listView;
    Post_ListItemAdapter adapter;
    public CategoryFragment(){}
    public static CategoryFragment newInstance(){
        return new CategoryFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category, container, false);

        Bundle bundle = getArguments();
        String category ;

        //로그인 이후에 제대로 아이디값 받아오는지 확인하는 용도
        if (bundle != null){
            TextView category_name = (TextView) v.findViewById(R.id.category_name);
            category = bundle.getString("category");
            category_name.setText(category);
        }
        else{
            TextView category_name = (TextView) v.findViewById(R.id.category_name);
            category_name.setText("null받음");
        }

        ListView listView = (ListView) v.findViewById(R.id.listView);
        adapter = new Post_ListItemAdapter();
        adapter.addItem(new Post_ListItem("writer", "[ㅇㅇ공모전] 같이 하실 분", "(1/4)", "D-16"));
        adapter.addItem(new Post_ListItem("writer", "[ㄴㄴ공모전] 같이 하실 분", "(2/4)", "D-16"));
        adapter.addItem(new Post_ListItem("writer", "[ㅁㅁ공모전] 같이 하실 분", "(3/4)", "D-16"));
        adapter.addItem(new Post_ListItem("writer", "[ㅅㅅ공모전] 같이 하실 분", "(4/4)", "D-16"));
        adapter.addItem(new Post_ListItem("writer", "[ㄹㄹ공모전] 같이 하실 분", "(1/5)", "D-16"));

        listView.setAdapter(adapter);

        return v;
    }
}
