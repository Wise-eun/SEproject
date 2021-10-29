package com.example.seproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MemberListFragment extends Fragment {
    ListView listView;
    Member_ListItemAdapter adapter;

    public static MemberListFragment newInstance(){
        return new MemberListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.member_list, container, false);
        listView = (ListView) view.findViewById(R.id.member_listview);
        adapter = new Member_ListItemAdapter(new Member_ListItemAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int pos, String type) {
                Member_ListItem member = (Member_ListItem) adapter.getItem(pos);
                if(type.equals("name")) {
                    //이름 눌렀을 때 발생시킬 이벤트
                    ProfileDetailActivity.userName = member.getName();

                    Intent intent = new Intent(getContext(), ProfileDetailActivity.class);
                    startActivity(intent);
                }
                else{
                    Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.rating_dialog);
                    TextView dialog_title = (TextView)dialog.findViewById(R.id.dialog_title);
                    Button submit_btn = (Button)dialog.findViewById(R.id.submit_btn);
                    submit_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.cancel();

                        }
                    });

                    Button cancel_btn = (Button)dialog.findViewById(R.id.cancel_btn);
                    cancel_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.cancel();
                        }
                    });

                    RatingBar dialog_ratingbar = (RatingBar)dialog.findViewById(R.id.dialog_ratingbar);
                    dialog_title.setText("'" + member.getName() + "'님에게 평점을 남기겠습니까?");
                    dialog.show();
                }

            }
        });

        adapter.addItem(new Member_ListItem("User_1"));
        adapter.addItem(new Member_ListItem("User_2"));
        adapter.addItem(new Member_ListItem("chuseok"));
        adapter.addItem(new Member_ListItem("elatedae"));
        adapter.addItem(new Member_ListItem("wise_eun"));




        listView.setAdapter(adapter);
        return view;
    }
}