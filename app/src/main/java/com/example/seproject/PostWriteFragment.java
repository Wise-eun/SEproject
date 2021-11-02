package com.example.seproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PostWriteFragment extends Fragment {
    String[] local_items = {"서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종",
            "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주도", "기타"};
    Spinner post_local_spinner;
    TextView category_name, date_tv, write_personnel_tv,mypost_title_tv;
    Button minus_btn, plus_btn,post_btn;
    EditText content_et;
    public static int pid;
    public static String post_local;
    DatePickerDialog datePickerDialog;
    String title;
    String writer;
    String deadline;
    int recruitment;
    String area;
    String content;
    String category;

    public static PostWriteFragment newInstance(){
        return new PostWriteFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.write_post, container, false);

        pid = CategoryFragment.last_pid; // 마지막 pid를 가져옴

        category_name = (TextView)view.findViewById(R.id.category_name);
        post_local_spinner = (Spinner) view.findViewById(R.id.post_local_spinner);
        write_personnel_tv = (TextView)view.findViewById(R.id.write_personnel_tv);
        minus_btn = (Button)view.findViewById(R.id.minus_btn);
        plus_btn = (Button)view.findViewById(R.id.plus_btn);
        content_et = (EditText)view.findViewById(R.id.content_et);
        mypost_title_tv = (EditText)view.findViewById(R.id.mypost_title_tv);
        date_tv = (TextView)view.findViewById(R.id.date_tv);
        post_btn = (Button)view.findViewById(R.id.post_btn);

        category_name.setText(CategoryFragment.category_str);

        ArrayAdapter<String> local_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,local_items);
        local_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        post_local_spinner.setAdapter(local_adapter);
        post_local_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(),Integer.toString(position),Toast.LENGTH_SHORT); //본인이 원하는 작업.
                post_local = local_adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(write_personnel_tv.getText().toString()) > 2){
                    write_personnel_tv.setText(String.valueOf(Integer.parseInt(write_personnel_tv.getText().toString())-1));
                }
            }
        });
        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_personnel_tv.setText(String.valueOf(Integer.parseInt(write_personnel_tv.getText().toString())+1));

            }
        });

        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date_tv.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        post_btn.setOnClickListener(new View.OnClickListener() { //작성버튼을 눌렀을 경우

            @Override
            public void onClick(View v) {
                pid = CategoryFragment.last_pid+1;
                writer = MainActivity.userName;
                title =mypost_title_tv.getText().toString();
                deadline = date_tv.getText().toString();
                recruitment = Integer.parseInt(write_personnel_tv.getText().toString());
                area = post_local;
                content =content_et.getText().toString();
                category =CategoryFragment.category_str;
                Log.d("TAG", "===========================================================") ;
                Log.d("TAG", "response pid = " + pid);
                Log.d("TAG", "response writer = " + writer);
                Log.d("TAG", "response title = " + title);
                Log.d("TAG", "response deadline = " + deadline);
                Log.d("TAG", "response recruitment = " + recruitment);
                Log.d("TAG", "response area = " + area);
                Log.d("TAG", "response content = " + content);
                Log.d("TAG", "response category = " + category);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {

                                Toast.makeText(getActivity(),"게시물 작성 완료.",Toast.LENGTH_SHORT).show();
                                ((MainActivity)getActivity()).replaceFragment(CategoryFragment.newInstance()); //화면전환

                            }
                            else{
                                Toast.makeText(getActivity(),"게시물 작성 실패.",Toast.LENGTH_SHORT).show();
                                return ;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청
                PostWriteRequest postWriteRequest = new PostWriteRequest(pid, writer,title, deadline, recruitment,area,content,category,responseListener);
                // public PostWriteRequest(int pid, String writer, String title,String deadline, int recruitment, String area, String content, String category,Response.Listener<Strin
                RequestQueue queue = Volley.newRequestQueue(PostWriteFragment.this.getActivity());
                queue.add(postWriteRequest);


            }
        });


        return view;
    }


}

