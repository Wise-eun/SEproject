package com.example.seproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class PostDetailFragment extends Fragment {

    boolean participate = false; //해당 계정이 이 게시물에 참여 중이면 true, 참여중이 아니면 false(기본값)
    public PostDetailFragment() {}

    public static PostDetailFragment newInstance(){
        return new PostDetailFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.post_detail, container, false);

        TextView category_name = (TextView) view.findViewById(R.id.category_name);
        TextView post_name_tv = (TextView) view.findViewById(R.id.post_name_tv);
        TextView post_personnel_tv = (TextView) view.findViewById(R.id.post_personnel_tv);
        TextView post_date_tv = (TextView) view.findViewById(R.id.post_date_tv);
        TextView post_local_tv = (TextView) view.findViewById(R.id.post_local_tv);
        TextView post_content_tv = (TextView) view.findViewById(R.id.post_content_tv);

        ImageButton member_list_btn = (ImageButton)view.findViewById(R.id.member_list_btn);
        member_list_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(MemberListFragment.newInstance());
            }
        });

        Button post_apply_btn = (Button)view.findViewById(R.id.post_apply_btn);
        post_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleMsg = " 이 게시물에 참여 신청을 하시겠습니까? ";
                if(participate){
                    titleMsg = " 이 게시물의 참여 신청을 취소하기겠습니까?";
                }

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

        category_name.setText(CategoryFragment.category_str);


        return view;
    }
}