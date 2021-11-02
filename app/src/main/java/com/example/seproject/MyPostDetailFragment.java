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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        RecyclerView post_member_listview = (RecyclerView)view.findViewById(R.id.post_member_listview);

        PostMember_ListItemAdapter adapter = new PostMember_ListItemAdapter();

        post_member_listview.setAdapter(adapter);
        post_member_listview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        ArrayList<PostMember_ListItem> items = new ArrayList<PostMember_ListItem>();
        items.add(new PostMember_ListItem("User_1"));
        items.add(new PostMember_ListItem("User_2"));
        items.add(new PostMember_ListItem("User_3"));
        items.add(new PostMember_ListItem("User_4"));
        items.add(new PostMember_ListItem("User_5"));

        adapter.setPostMemberItems(items);

        post_complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleMsg = " 이 게시물을 완료하시겠습니까? ";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(titleMsg);

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
                String titleMsg = " 이 게시물을 삭제하시겠습니까? ";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(titleMsg);

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
                ((MainActivity)getActivity()).replaceFragment(MyPostModifyFragment.newInstance());
            }
        });

        return view;
    }
}