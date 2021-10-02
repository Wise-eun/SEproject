package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class MsgListFragment extends Fragment {
    public MsgListFragment() {
    }

    public static MsgListFragment newInstance() {
        return new MsgListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_list, container, false);
        return v;
    }
}