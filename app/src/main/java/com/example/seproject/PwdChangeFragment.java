package com.example.seproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PwdChangeFragment extends Fragment {

    public PwdChangeFragment(){}

    public static PwdChangeFragment newInstance(){
        return new PwdChangeFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pwdchange, container, false);

        EditText currentPwd_insert_et = view.findViewById(R.id.currentPwd_insert_et);
        EditText newPwd_check_insert_et = view.findViewById(R.id.newPwd_check_insert_et);
        EditText newPwd_insert_et = view.findViewById(R.id.newPwd_insert_et);

        Button pwd_edit_btn = view.findViewById(R.id.pwd_edit_btn);
        pwd_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = ProfileFragment.userID;
                String currentPwd_insert = currentPwd_insert_et.getText().toString();
                String newPwd_check_insert = newPwd_check_insert_et.getText().toString();
                String newPwd_insert = newPwd_insert_et.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){ //로그인 성공한 경우
                                if(newPwd_check_insert.equals(newPwd_insert)) {
                                    //비밀번호 수정 성공

                                    Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response2) {
                                            Toast.makeText(getActivity(), "비밀번호가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                            ((MainActivity) getActivity()).replaceFragment(ProfileFragment.newInstance());
//                                            try{
//                                                JSONObject jsonObject2 = new JSONObject(response2);
//                                                boolean success2 = jsonObject2.getBoolean("success");
//                                                if(success2) {
//                                                    Toast.makeText(getActivity(), "비밀번호가 수정되었습니다.", Toast.LENGTH_SHORT).show();
//                                                    ((MainActivity) getActivity()).replaceFragment(ProfileFragment.newInstance());
//                                                }
//                                            }catch (JSONException e2) {
//                                                e2.printStackTrace();
//                                                Toast.makeText(getActivity(), "오류발생", Toast.LENGTH_SHORT).show();
//                                                //db상에 수정은 되는데 오류발생이라고 떠요...
//                                            }
                                        }

                                    };

                                    PwdChangeRequest pwdChangeRequest = new PwdChangeRequest(userID,newPwd_insert, responseListener2);
                                    RequestQueue queue2 = Volley.newRequestQueue(PwdChangeFragment.this.getActivity());
                                    queue2.add(pwdChangeRequest);
                                }
                                else {
                                    Toast.makeText(getActivity(), "새 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            else{//로그인 실패한 경우
                                Toast.makeText(getActivity(),"현재 비밀번호가 올바르지 않습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                PwdCorrectRequest pwdCorrectRequest = new PwdCorrectRequest(userID,currentPwd_insert,responseListener);
                RequestQueue queue = Volley.newRequestQueue(PwdChangeFragment.this.getActivity());
                queue.add(pwdCorrectRequest);

            }
        });

        return view;
    }
}

