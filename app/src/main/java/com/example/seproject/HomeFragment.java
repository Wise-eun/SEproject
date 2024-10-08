package com.example.seproject;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private ImageButton alarm_btn;
    private ImageButton message_btn;
    private ImageButton search_btn;
    private ImageButton idea_category;
    private ImageButton advertise_category;
    private ImageButton ucc_category;
    private ImageButton design_category;
    private ImageButton computer_category;
    private ImageButton science_category;
    private ImageButton literature_category;
    private ImageButton supporters_category;
    private ImageButton etc_category;

    public static EditText search_et;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        alarm_btn = (ImageButton) v.findViewById(R.id.alarm_btn);
        alarm_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(AlarmFragment.newInstance());
            }
        });

        message_btn = (ImageButton) v.findViewById(R.id.message_btn);
        message_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(MsgListFragment.newInstance());
            }
        });

        search_btn = (ImageButton) v.findViewById(R.id.search_btn);
        search_et = (EditText) v.findViewById(R.id.search_et);
        search_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String search_input = search_et.getText().toString();
                if (search_input.equals("")){
                    androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    AlertDialog dialog;
                    dialog = builder.setMessage("검색어를 입력하지 않았습니다.").setNegativeButton("확인", null).create();
                    dialog.show();
                }
                else{
                    ((MainActivity)getActivity()).replaceFragment(SearchFragment.newInstance());
                }
            }
        });

        idea_category = (ImageButton) v.findViewById(R.id.idea_category);
        idea_category.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).bundlePutString("category", "기획/아이디어");
                ((MainActivity)getActivity()).replaceFragment(CategoryFragment.newInstance());
            }
        });

        advertise_category = (ImageButton) v.findViewById(R.id.advertise_category);
        advertise_category.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).bundlePutString("category", "광고/마케팅");
                ((MainActivity)getActivity()).replaceFragment(CategoryFragment.newInstance());
            }
        });
        ucc_category = (ImageButton) v.findViewById(R.id.ucc_category);
        ucc_category.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).bundlePutString("category", "영상/UCC/사진");
                ((MainActivity)getActivity()).replaceFragment(CategoryFragment.newInstance());
            }
        });
        design_category = (ImageButton) v.findViewById(R.id.design_category);
        design_category.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).bundlePutString("category", "미술/디자인");
                ((MainActivity)getActivity()).replaceFragment(CategoryFragment.newInstance());
            }
        });
        computer_category = (ImageButton) v.findViewById(R.id.computer_category);
        computer_category.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).bundlePutString("category", "IT/컴퓨터");
                ((MainActivity)getActivity()).replaceFragment(CategoryFragment.newInstance());
            }
        });
        science_category = (ImageButton) v.findViewById(R.id.science_category);
        science_category.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).bundlePutString("category", "과학/공학");
                ((MainActivity)getActivity()).replaceFragment(CategoryFragment.newInstance());
            }
        });

        literature_category = (ImageButton) v.findViewById(R.id.literature_category);
        literature_category.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).bundlePutString("category", "문학/글");
                ((MainActivity)getActivity()).replaceFragment(CategoryFragment.newInstance());
            }
        });
        supporters_category = (ImageButton) v.findViewById(R.id.supporters_category);
        supporters_category.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).bundlePutString("category", "서포터즈/봉사활동");
                ((MainActivity)getActivity()).replaceFragment(CategoryFragment.newInstance());
            }
        });
        etc_category = (ImageButton) v.findViewById(R.id.etc_category);
        etc_category.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).bundlePutString("category", "기타");
                ((MainActivity)getActivity()).replaceFragment(CategoryFragment.newInstance());
            }
        });

        return v;
    }
}