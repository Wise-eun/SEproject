package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class MyPostModifyFragment extends Fragment {

    public MyPostModifyFragment() {}

    public static MyPostModifyFragment newInstance(){
        return new MyPostModifyFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.mypost_modify, container, false);

        return view;
    }
}