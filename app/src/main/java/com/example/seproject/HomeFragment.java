package com.example.seproject;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    public HomeFragment(){}
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Bundle bundle = getArguments();
        String userID ;

        //로그인 이후에 제대로 아이디값 받아오는지 확인하는 용도
        if (bundle != null){
            TextView homeFragment_tv = (TextView) v.findViewById(R.id.homeFragment);
            userID = bundle.getString("userID");
            homeFragment_tv.setText(userID);

       }
        else{
            TextView homeFragment_tv = (TextView) v.findViewById(R.id.homeFragment);
            homeFragment_tv.setText("null받음");


        }

        return v;
    }
}