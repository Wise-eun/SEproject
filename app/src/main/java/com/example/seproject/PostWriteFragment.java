package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class PostWriteFragment extends Fragment {
    public PostWriteFragment(){}
    public static PostWriteFragment newInstance(){
        return new PostWriteFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.write_post, container, false);

        TextView category_name = (TextView)v.findViewById(R.id.category_name);
        category_name.setText(CategoryFragment.category_str);

        return v;
    }
}
