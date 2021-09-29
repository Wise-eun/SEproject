package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CategoryFragment extends Fragment {
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

        return v;
    }
}
