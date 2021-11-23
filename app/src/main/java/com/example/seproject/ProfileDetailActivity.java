package com.example.seproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileDetailActivity extends AppCompatActivity implements Serializable {
    private Button send_msg_btn;
    private TextView user_name_tv, user_info_tv, rating_tv, self_intro_tv;


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
    public static String userJob;
    public static String userSchool;
    public static String userLocal;
    public static String userName;
    public static String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_detail);

        user_name_tv = (TextView) findViewById(R.id.user_name_tv);
        user_info_tv = findViewById(R.id.user_info_tv);
        rating_tv = findViewById(R.id.rating_tv);
        self_intro_tv = findViewById(R.id.self_intro_tv);
        send_msg_btn = findViewById(R.id.profie_edit_btn);

        ProfileDetailActivity.GetData task = new ProfileDetailActivity.GetData();
        task.execute("http://steak2121.ivyro.net/loadUser.php");

        send_msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMsgFragment.where_in = 3;
                Intent intent = new Intent(ProfileDetailActivity.this,MainActivity.class);
                intent.putExtra("from", "profile");
                startActivity(intent);

            }
        });
    }
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

                if (name.equals(userName)) // 현재 사용자 ID와 서버에있는 정보중 ID가 같은것들의 정보만 가져옴
                {
                    // user_name_tv,user_info_tv,rating_tv
                    user_name_tv.setText(userName);
                    if(job.equals(""))
                        job = "없음";
                    if(school.equals(""))
                        school = "없음";
                    if(local.equals(""))
                        local = "없음";
                    user_info_tv.setText(job+"/"+school+"/"+local);
                    float rating_num = Float.parseFloat(rating);
                    int ratingPeople_num = Integer.parseInt(ratingPeople);
                    float rating_res = rating_num/ratingPeople_num;
                    rating_tv.setText(Float.toString(rating_res));
                    self_intro_tv.setText(selfintro);


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