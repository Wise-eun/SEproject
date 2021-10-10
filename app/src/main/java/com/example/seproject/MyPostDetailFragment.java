package com.example.seproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class MyPostDetailFragment extends Fragment {

    public MyPostDetailFragment() {}

    public static MyPostDetailFragment newInstance(){
        return new MyPostDetailFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.mypost_detail, container, false);

        Button post_complete_btn = view.findViewById(R.id.post_complete_btn);
        Button post_delete_btn = view.findViewById(R.id.post_delete_btn);
        Button post_edit_btn = view.findViewById(R.id.post_edit_btn);


        post_complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleMsg = " 이 게시물에 참여 신청을 하시겠습니까? ";
//                if(participate){
//                    titleMsg = " 이 게시물의 참여 신청을 취소하기겠습니까?";
//                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(titleMsg);

//                builder.setView(R.drawable.thank_you);

//                builder.setMessage(" ( 참여 신청들은 모두 거절됩니다. ) ");

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });



        post_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleMsg = " 이 게시물에 참여 신청을 하시겠습니까? ";
//                if(participate){
//                    titleMsg = " 이 게시물의 참여 신청을 취소하기겠습니까?";
//                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(titleMsg);

//                builder.setView(R.drawable.thank_you);

//                builder.setMessage(" ( 참여 신청들은 모두 거절됩니다. ) ");

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        post_edit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        return view;
    }
}