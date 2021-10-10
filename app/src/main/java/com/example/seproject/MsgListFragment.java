package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class MsgListFragment extends Fragment {
    public MsgListFragment() {
    }

    Msg_ListItemAdapter adapter;
ImageButton write_msg_btn;

    public static MsgListFragment newInstance() {
        return new MsgListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_list, container, false);
        ListView listView = (ListView) v.findViewById(R.id.msg_list_listview);
        adapter = new Msg_ListItemAdapter();
        adapter.addItem(new Msg_ListItem("User_name", "아 넵! 010-0000-0000입니다!", "2021-09-23 18:31"));
        adapter.addItem(new Msg_ListItem("User_2", "인원이 다 차서..! 전화번호 알려주세요ㅎㅎ", "2021-09-23 18:25"));
        adapter.addItem(new Msg_ListItem("User_non", "저 메시지 답장 좀 부탁드려요...", "2021-09-21 17:30"));
        adapter.addItem(new Msg_ListItem("User_many", "아 그거 말씀인데요 저는 그렇게 생각한 적이...", "2021-09-21 17:30"));


        write_msg_btn = (ImageButton)v.findViewById(R.id.write_msg_btn);



        write_msg_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(SearchUserFragment.newInstance());

            }
        });


        listView.setAdapter(adapter);
        return v;
    }

}