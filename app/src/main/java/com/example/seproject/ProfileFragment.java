package com.example.seproject;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileFragment extends Fragment {

    private Button profile_edit_btn;
    public TextView user_name_tv,user_info_tv,rating_tv;
    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "userID";
    private static final String TAG_Password = "userPassword";
    private static final String TAG_NAME = "userName";
    private static final String TAG_rating = "rating";
    private static final String TAG_ratingPeople = "ratingPeople";
    private static final String TAG_job = "job";
    private static final String  TAG_school= "school";
    private static final String TAG_local = "local";
    private static final String TAG_selfintro = "selfintro";
    public static String selfIntro;
    private String mJsonString;
    public static String userID;
    public static String userPW;
    public static String userName;
    public static String userJob;
    public static String userSchool;
    public static String userLocal;

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profile_edit_btn = (Button) view.findViewById(R.id.send_msg_btn);

        user_name_tv = (TextView) view.findViewById(R.id.user_name_tv);
        user_info_tv = (TextView) view.findViewById(R.id.user_info_tv);
        rating_tv = (TextView) view.findViewById(R.id.rating_tv);

        //번들로 받아온거 저장~!
        Bundle bundle = getArguments();
        userID = bundle.getString("userID");

        //로그인 이후에 제대로 아이디값 받아오는지 확인하는 용도
        if (bundle != null){
            user_name_tv.setText(userID);
        }
        else{

            user_name_tv.setText("null받음");
        }


        GetData task = new GetData();

        task.execute("http://steak2121.ivyro.net/loadUser.php");


        profile_edit_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(ProfileEditFragment.newInstance());

            }
        });

        TextView about_me_tv = (TextView)view.findViewById(R.id.about_me_tv);
        about_me_tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(SelfIntroEditFragment.newInstance());
            }
        });

        TextView pwd_change_tv = (TextView)view.findViewById(R.id.pwd_change_tv);
        pwd_change_tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(PwdChangeFragment.newInstance());
            }
        });

        TextView writing_tv = (TextView)view.findViewById(R.id.writing_tv);
        writing_tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(MyPostFragment.newInstance());
            }
        });

        return view;
    }


    ////data Load


    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
/*
            progressDialog = ProgressDialog.show(v.this,
                    "Please Wait", null, true, true);
       */
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //progressDialog.dismiss();

            Log.d(TAG, "response  - " + result);


            if (result == null) {


            } else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = "http://steak2121.ivyro.net/loadUser.php";


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }

    }

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String pw = item.getString(TAG_Password);
                String name = item.getString(TAG_NAME);
                String rating = item.getString(TAG_rating);
                String ratingPeople = item.getString(TAG_ratingPeople);
                String job = item.getString(TAG_job);
                String school = item.getString(TAG_school);
                String local = item.getString(TAG_local);
                String selfintro = item.getString(TAG_selfintro);

                if (id.equals(userID)) // 현재 사용자 ID와 서버에있는 정보중 ID가 같은것들의 정보만 가져옴
                {
                   // user_name_tv,user_info_tv,rating_tv
                    user_name_tv.setText(name);
                    if(job.equals(""))
                        job = "없음";
                    if(school.equals(""))
                        school = "없음";
                    if(local.equals(""))
                        local = "없음";
                    user_info_tv.setText(job+"/"+school+"/"+local);
                    int rating_num = Integer.parseInt(rating);
                    int ratingPeople_num = Integer.parseInt(ratingPeople);
                    int rating_res = rating_num/ratingPeople_num;
                    rating_tv.setText(Integer.toString(rating_res));


                    selfIntro = String.copyValueOf(selfintro.toCharArray()); //자기소개 복사.
                    userName = String.copyValueOf(name.toCharArray());//사용자 닉네임 복사
                    userJob = String.copyValueOf(job.toCharArray());//사용자 직업 복사
                    userSchool = String.copyValueOf(school.toCharArray());//사용자 학교 복사
                    userLocal = String.copyValueOf(local.toCharArray());//사용자 지역 복사
                    userPW = String.copyValueOf(pw.toCharArray());//사용자 비번 복사

                }
                /*
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_NUM, num);
*/

            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }



}