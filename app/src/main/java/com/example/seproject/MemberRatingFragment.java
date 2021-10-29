package com.example.seproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class MemberRatingFragment extends Fragment {
    ListView listView;
    Member_ListItemAdapter adapter;

    public MemberRatingFragment() {}

    public static MemberRatingFragment newInstance(){
        return new MemberRatingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.member_list, container, false);
        listView = (ListView) view.findViewById(R.id.member_listview);
        adapter = new Member_ListItemAdapter(new Member_ListItemAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int pos, String type) {
                if(type.equals("name")) {
                    //이름 눌렀을 때 발생시킬 이벤트
                    Toast.makeText(getActivity(), pos + "번 째 이름 클릭", Toast.LENGTH_SHORT).show();

//                    ((MainActivity)getActivity()).replaceFragment(ProfileDetailFragment.newInstance());
                }
                else{
                    //평가 버튼 눌렀을 때 발생시킬 이벤트
//                    Toast.makeText(getActivity(), pos + "번 째 평가 클릭", Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    RatingBar rating = new RatingBar(getActivity());
//                    rating.setMax(5);
//
//                    builder.setTitle("'User_2'에게 별점을 남기겠습니까?");
//                    builder.setView(rating);
//
//                    builder.setPositiveButton(android.R.string.ok,
//                            new DialogInterface.OnClickListener(){
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    builder.setNegativeButton("Cancel",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//                            });
//                    builder.create();
//                    builder.show();

                    String titleMsg = " 이 게시물에 참여 신청을 하시겠습니까? ";

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

            }
        });

        adapter.addItem(new Member_ListItem("User_1"));
        adapter.addItem(new Member_ListItem("User_2"));
        adapter.addItem(new Member_ListItem("chuseok"));
        adapter.addItem(new Member_ListItem("elatedae"));
        adapter.addItem(new Member_ListItem("wise_eun"));
        adapter.addItem(new Member_ListItem("my girlfriend♥"));


        listView.setAdapter(adapter);
        return view;
    }
}