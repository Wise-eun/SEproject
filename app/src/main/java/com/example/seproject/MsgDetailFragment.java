package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class MsgDetailFragment extends Fragment {

    public static MsgDetailFragment newInstance() {
        return new MsgDetailFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_detail, container, false);




        return v;
    }
}
