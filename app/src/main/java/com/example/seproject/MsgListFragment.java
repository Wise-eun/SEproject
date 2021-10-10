package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MsgListFragment extends Fragment {
    public MsgListFragment() {
    }
ImageButton write_msg_btn ;


    public static MsgListFragment newInstance() {
        return new MsgListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_list, container, false);

        write_msg_btn = (ImageButton) v.findViewById(R.id.write_msg_btn);
        write_msg_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(SearchUserFragment.newInstance());

            }
        });


        return v;
    }
}