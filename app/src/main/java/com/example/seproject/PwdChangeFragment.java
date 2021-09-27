package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class PwdChangeFragment extends Fragment {

    public PwdChangeFragment(){};
    public static PwdChangeFragment newInstance(){
        return new PwdChangeFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pwdchange, container, false);
        return view;
    }
}
