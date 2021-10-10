package com.example.seproject;

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



    EditText currentPwd_insert_et,newPwd_insert_et,newPwd_check_insert_et;
    Button pwd_edit_btn;

    public PwdChangeFragment(){};
    public static PwdChangeFragment newInstance(){
        return new PwdChangeFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pwdchange, container, false);

        currentPwd_insert_et =(EditText) view.findViewById(R.id.currentPwd_insert_et);
        newPwd_insert_et =(EditText) view.findViewById(R.id.newPwd_insert_et);
        newPwd_check_insert_et =(EditText) view.findViewById(R.id.newPwd_check_insert_et);
        pwd_edit_btn =(Button) view.findViewById(R.id.pwd_edit_btn);




        pwd_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPwd_insert_et.getText().toString().equals("") ||newPwd_insert_et.getText().toString().equals("") || newPwd_check_insert_et.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "모두 기입하였는지 한번 더 확인해주세요.", Toast.LENGTH_SHORT).show();

                else if(currentPwd_insert_et.getText().toString().equals(ProfileFragment.userPW) != true)//현재 비번이랑 다를경우
                    Toast.makeText(getActivity(), "현재 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();

                else if(newPwd_check_insert_et.getText().toString().equals(ProfileFragment.userPW) )//현재 비번이랑 수정사항이 같을경우
                    Toast.makeText(getActivity(), "변경 사항이 없습니다.", Toast.LENGTH_SHORT).show();

                else if(newPwd_check_insert_et.getText().toString().equals(newPwd_insert_et.getText().toString())!= true )//수정 비밀번호 확인
                    Toast.makeText(getActivity(), "비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show();
                else {//비밀번호 변경 성공했을 경우
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    };

                    PwdUpdateRequest pwdUpdateRequest = new PwdUpdateRequest(ProfileFragment.userID, newPwd_check_insert_et.getText().toString(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(PwdChangeFragment.this.getActivity());
                    queue.add(pwdUpdateRequest);


                    Toast.makeText(getActivity(), "비밀번호가 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).replaceFragment(ProfileFragment.newInstance()); //프로필 화면으로 다시 이동
                }
            }

        });



        return view;
    }
}