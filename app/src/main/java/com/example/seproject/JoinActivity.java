package com.example.seproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class JoinActivity  extends AppCompatActivity implements Serializable {

    private EditText id_insert_join, pwd_insert_join, pwd_check_insert_join, name_insert;
    private Button join_btn_join, id_overlap_btn, name_overlap_btn;

    private AlertDialog dialog;
    private boolean validate = false, validate2 = false;

    protected void onCreate(Bundle savedInstanceState) { //액티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        id_insert_join = findViewById(R.id.id_insert_join_et);
        pwd_insert_join = findViewById(R.id.pwd_insert_join_et);
        pwd_check_insert_join = findViewById(R.id.pwd_check_insert_et);
        name_insert = findViewById(R.id.name_insert_et);
        join_btn_join = findViewById(R.id.join_btn_join);

        id_overlap_btn = findViewById(R.id.id_overlap_btn);
        name_overlap_btn = findViewById(R.id.name_overlap_btn);



        //회원가입 버튼 클릭 시 수행
        join_btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText에 현재 입력되어있는 값을 가져옴
                String userID = id_insert_join.getText().toString();
                String userPassword = pwd_insert_join.getText().toString();
                String userName = name_insert.getText().toString();
                String userPasswordCheck = pwd_check_insert_join.getText().toString();



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(!(userPassword.equals(userPasswordCheck))){
                                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                            }
                            else if(userID.equals("") || userPassword.equals("") || userPasswordCheck.equals("")) {
                                Toast.makeText(getApplicationContext(), "모두 기입하였는지 한번 더 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                            else if(validate == false){
                                Toast.makeText(getApplicationContext(), "ID중복확인을 진행해주세요.", Toast.LENGTH_SHORT).show();
                            }
                            else if(validate2 == false){
                                Toast.makeText(getApplicationContext(), "닉네임중복확인을 진행해주세요.", Toast.LENGTH_SHORT).show();
                            }
                            else if (success) {
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }






                            else {
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



//ID중복검사 버튼 클릭시 수행
        id_overlap_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userID = id_insert_join.getText().toString();
                if (validate) {
                    return; //검증 완료
                }

                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                id_insert_join.setEnabled(false); //아이디값 고정
                                validate = true; //검증 완료

                            }
                            else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                };
                IDValidateRequest idvalidateRequest = new IDValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(idvalidateRequest);
            }
        });


//닉네임중복검사 버튼 클릭시 수행
        name_overlap_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userName = name_insert.getText().toString();
                /*
                if (validate2) {
                    return; //검증 완료
                }*/

                if (userName.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("닉네임을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                //name_insert.setEnabled(false); //닉네임값 고정
                                validate2 = true; //검증 완료

                            }
                            else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("이미 존재하는 닉네임입니다.").setNegativeButton("확인", null).create();
                                validate2 = false;
                                dialog.show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                };
                NameValidateRequest namevalidateRequest = new NameValidateRequest(userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(namevalidateRequest);
            }
        });



    }
}
