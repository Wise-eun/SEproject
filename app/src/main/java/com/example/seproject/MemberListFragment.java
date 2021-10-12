package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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
                if(type.equals("name")) {
                    //이름 눌렀을 때 발생시킬 이벤트
                    Toast.makeText(getActivity(), pos + "번 째 이름 클릭", Toast.LENGTH_SHORT).show();
                }
                else{
                    //평가 버튼 눌렀을 때 발생시킬 이벤트
//                    Toast.makeText(getActivity(), pos + "번 째 평가 클릭", Toast.LENGTH_SHORT).show();
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