package com.example.seproject;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class ProfileEditFragment extends Fragment {

    String[] job_items = {"-", "고등학생", "대학생", "직장인", "기타"};
    String[] local_items = {"-", "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시",
            "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주도", "기타"};

    String user_job;
    String user_school;
    String user_local;

    TextView user_name_tv;
    EditText schcool_insert_et, local_insert_et;

int job_num;
int local_num;

    public ProfileEditFragment(){}

    public static ProfileEditFragment newInstance(){
        return new ProfileEditFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_edit, container, false);

        user_name_tv = (TextView) view.findViewById(R.id.user_name_tv);
        schcool_insert_et =(EditText) view.findViewById(R.id.shcool_insert_et);
        local_insert_et =(EditText) view.findViewById(R.id.local_insert_et);


        user_name_tv.setText(ProfileFragment.userName);
        Spinner job_spinner = (Spinner) view.findViewById(R.id.job_spinner);

        ArrayAdapter<String> job_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,job_items);
        job_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        job_spinner.setAdapter(job_adapter);



        job_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                user_job = job_adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(!(ProfileFragment.userJob.equals("없음"))) {
            for (int i = 0; i < job_items.length; i++) {
                if (ProfileFragment.userJob.equals(job_items[i])) {

                    job_num = i;

                    break;
                }
            }

            job_spinner.setSelection(job_num);

        }



        Spinner local_spinner = (Spinner) view.findViewById(R.id.local_spinner);

        ArrayAdapter<String> local_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,local_items);
        local_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        local_spinner.setAdapter(local_adapter);



        local_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                user_local = local_adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if(!(ProfileFragment.userLocal.equals("없음") || ProfileFragment.userLocal.equals("- "))) {
            int idx = ProfileFragment.userLocal.indexOf(" ");

            String userLocal1 = ProfileFragment.userLocal.substring(0, idx);
            String userLocal2 = ProfileFragment.userLocal.substring(idx + 1);
            for (int i = 0; i < local_items.length; i++) {
                if (userLocal1.equals(local_items[i])) {

                    local_num = i;
                    break;
                }
            }
            local_spinner.setSelection(local_num);

            local_insert_et.setText(userLocal2);
        }
        if(!(ProfileFragment.userSchool.equals("없음")))
            schcool_insert_et.setText(ProfileFragment.userSchool);




        //최종적으로 수정하기 버튼을 눌렀을 경우
        Button edit_btn = (Button) view.findViewById(R.id.edit_btn);
        edit_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            user_school = schcool_insert_et.getText().toString();
            user_local = user_local + " " + local_insert_et.getText().toString();
            user_school = schcool_insert_et.getText().toString();



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

            if(user_job.equals("-") || user_job.equals("기타") )
                user_job = "";
            if(user_local.equals("-")|| user_local.equals("기타") )
                user_local = "";
            ProfileUpdateRequest updateProfileRequest = new ProfileUpdateRequest(ProfileFragment.userID, user_job,user_school,user_local, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ProfileEditFragment.this.getActivity());
            queue.add(updateProfileRequest);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //프로필 화면으로 다시 이동
                        ((MainActivity)getActivity()).replaceFragment(ProfileFragment.newInstance());

                    }
                }, 500); //딜레이 타임 조절


            }
        });

        return view;
    }
}