package com.example.seproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProfileFragment extends Fragment {
    public ProfileFragment(){}
    private Button profile_edit_btn;

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Button profile_edit_btn = (Button) v.findViewById(R.id.profile_edit_btn);
        profile_edit_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(ProfileEditFragment.newInstance());

            }
        });

        TextView about_me_tv = (TextView)v.findViewById(R.id.about_me_tv);
        about_me_tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(SelfIntroductionEditFragment.newInstance());
            }
        });

        TextView pwd_change_tv = (TextView)v.findViewById(R.id.pwd_change_tv);
        pwd_change_tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(PwdChangeFragment.newInstance());
            }
        });

        TextView writing_tv = (TextView)v.findViewById(R.id.writing_tv);
        writing_tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(MyPostFragment.newInstance());
            }
        });

        return v;
    }
}