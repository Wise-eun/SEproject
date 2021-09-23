package com.example.seproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileEdit extends AppCompatActivity {
    TextView textView;

    String[] job_items = {"고등학생", "대학생", "직장인", "기타"};
    String[] local_items = {"서울특별시", "경북", "경남", "기타"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        Spinner job_spinner = (Spinner) findViewById(R.id.job_spinner);
        ArrayAdapter<String> job_adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, job_items
        );

        Spinner local_spinner = (Spinner) findViewById(R.id.local_spinner);
        ArrayAdapter<String> local_adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, local_items
        );

        // 드롭다운 클릭 시 선택 창
        job_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 스피너에 어댑터 설정
        job_spinner.setAdapter(job_adapter);

        // 스피너에서 선택 했을 경우 이벤트 처리
        job_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}