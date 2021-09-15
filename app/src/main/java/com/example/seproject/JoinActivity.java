package com.example.seproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class JoinActivity  extends AppCompatActivity implements Serializable {

    private EditText id_insert_join, pwd_insert_join, name_insert;
    private Button join_btn_join, id_overlap_btn, name_overlap_btn;


    protected void onCreate(Bundle savedInstanceState) { //액티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        id_insert_join = findViewById(R.id.id_insert_join);
        pwd_insert_join = findViewById(R.id.pwd_insert_join);
        name_insert = findViewById(R.id.name_insert);

        join_btn_join = findViewById(R.id.join_btn_join);
        pwd_insert_join = findViewById(R.id.pwd_insert_join);
        name_insert = findViewById(R.id.name_insert);

        //회원가입 버튼 클릭 시 수행
        join_btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText에 현재 입력되어있는 값을 가져옴
                String userID = id_insert_join.getText().toString();
                String userPassword = pwd_insert_join.getText().toString();
                String userName = name_insert.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 요청
                JoinRequest joinRequest = new JoinRequest(userID, userPassword, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(joinRequest);
            }
        });


    }
}
