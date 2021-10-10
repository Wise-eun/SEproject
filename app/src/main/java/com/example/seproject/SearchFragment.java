package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
    Bundle bundle = getArguments();
    String search_input;

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

        return v;
    }
}