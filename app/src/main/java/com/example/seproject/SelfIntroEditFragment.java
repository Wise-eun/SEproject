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

    Button modify_btn;
    EditText self_intro_insert_et;
    String new_intro;

    public static SelfIntroEditFragment newInstance(){
        return new SelfIntroEditFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.selfintro_edit, container, false);

        modify_btn = (Button) view.findViewById(R.id.modify_btn);
        self_intro_insert_et = (EditText) view.findViewById(R.id.self_intro_insert_et);

        self_intro_insert_et.setText(ProfileFragment.selfIntro);


        modify_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               // ((MainActivity)getActivity()).replaceFragment(ProfileEditFragment.newInstance());

                new_intro = self_intro_insert_et.getText().toString();


                //새로운 자기소개로 업데이트 해줘야함..
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

                SelfIntroUpdateRequest updateSelfintroRequestRequest = new SelfIntroUpdateRequest(ProfileFragment.userID, new_intro, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelfIntroEditFragment.this.getActivity());
                queue.add(updateSelfintroRequestRequest);



            }
        });


        return view;
    }
}
