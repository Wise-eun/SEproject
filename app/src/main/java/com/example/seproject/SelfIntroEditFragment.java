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

public class SelfIntroEditFragment extends Fragment {
    public SelfIntroEditFragment(){}

    Button btn_self;
    EditText et_self;
    String new_intro;

    public static SelfIntroEditFragment newInstance(){
        return new SelfIntroEditFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.selfintro_edit, container, false);

        btn_self = (Button) v.findViewById(R.id.btn_self);
        et_self = (EditText) v.findViewById(R.id.self_intro_insert_et);

        et_self.setText(ProfileFragment.selfIntro);


        btn_self.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               // ((MainActivity)getActivity()).replaceFragment(ProfileEditFragment.newInstance());

                new_intro = et_self.getText().toString();


                //새로운 자기소개로 업데이트 해줘야함..
                //고치고 나서 db에는 바껴있는데 다시 접속하면 안 바껴있음 ㅠㅠ
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 성공한 경우

                                Toast.makeText(getActivity(),"성공적으로 자기소개가 수정되었습니다.",Toast.LENGTH_SHORT).show();




                            } else {// 실패한 경우
                                Toast.makeText(getActivity(), "수정이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                UpdateSelfintroRequest updateSelfIntroRequestRequest = new UpdateSelfintroRequest(ProfileFragment.userID, new_intro, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelfIntroEditFragment.this.getActivity());
                queue.add(updateSelfIntroRequestRequest);

                ((MainActivity)getActivity()).replaceFragment(ProfileFragment.newInstance());

            }
        });


        return v;
    }
}
