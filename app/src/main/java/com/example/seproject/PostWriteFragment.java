package com.example.seproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PostWriteFragment extends Fragment {
    String[] local_items = {"서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종",
            "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주도", "기타"};
    Spinner post_local_spinner;
    TextView category_name, date_tv, write_personnel_tv;
    Button minus_btn, plus_btn;
    EditText content_et;

    String post_local;
    DatePickerDialog datePickerDialog;


    public static PostWriteFragment newInstance(){
        return new PostWriteFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.write_post, container, false);

        category_name = (TextView)view.findViewById(R.id.category_name);
        post_local_spinner = (Spinner) view.findViewById(R.id.post_local_spinner);
        write_personnel_tv = (TextView)view.findViewById(R.id.write_personnel_tv);
        minus_btn = (Button)view.findViewById(R.id.minus_btn);
        plus_btn = (Button)view.findViewById(R.id.plus_btn);
        content_et = (EditText)view.findViewById(R.id.content_et);
        date_tv = (TextView)view.findViewById(R.id.date_tv);


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
//

        return view;
    }


}
