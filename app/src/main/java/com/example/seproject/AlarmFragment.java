package com.example.seproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AlarmFragment extends Fragment {
    Alarm_ListItemAdapter adapter;

    public AlarmFragment() {}

    public static AlarmFragment newInstance() {
        return new AlarmFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.alarm, container, false);
        ListView listView = (ListView) view.findViewById(R.id.alarm_listview);

        adapter = new Alarm_ListItemAdapter(new Alarm_ListItemAdapter.OnDeleteClickListener() {
            @Override
            public void onDelete(View v, int pos, String type) {
                if(type.equals("accept")){
                    //수락 눌렀을 때 발생시킬 이벤트
                    Toast.makeText(getActivity(), pos + "번 째 수락", Toast.LENGTH_SHORT).show();
                }
                else{
                    //거절 눌렀을 때 발생시킬 이벤트
                    Toast.makeText(getActivity(), pos + "번 째 거절", Toast.LENGTH_SHORT).show();

                }
            }
        });


        adapter.addItem(new Alarm_ListItem("User_0901님이 [개인 프로젝트 해서 포폴 만드..]글에 참여 신청을 하였습니다."));
        adapter.addItem(new Alarm_ListItem("User_0214님이 [초고수 모집합니다]글의 참여 신청을 거절하였습니다."));
        adapter.addItem(new Alarm_ListItem("User_0613님이 [[ㄴㄴ공모전] 같이 할 사람...]글의 참여 신청을 수락하였습니다."));


        listView.setAdapter(adapter);
        return view;

    }
}
