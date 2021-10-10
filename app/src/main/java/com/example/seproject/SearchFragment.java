package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
    Bundle bundle = getArguments();
    String search_input;
    ListView listView;
    Post_ListItemAdapter adapter;

    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "userID";


    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search, container, false);
        EditText search_et = (EditText) v.findViewById(R.id.search_et);
        if (bundle != null){
            search_input = bundle.getString("search");
            search_et.setText(search_input);
        }
        else{
            search_et.setText("메롱메롱");
        }

        ListView listView = (ListView) v.findViewById(R.id.search_listview);
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