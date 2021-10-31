package com.example.seproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PostDetailFragment extends Fragment {
    public static int pid; //게시물 ID
    boolean participate = false; //해당 계정이 이 게시물에 참여 중이면 true, 참여중이 아니면 false(기본값)
    boolean request = false; //해당 계정이 이 게시물에  참여 신청을 했을 경우이면 true, 아니면 false
    //게시물 getData에 필요한 친구들
    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_JSON2 = "loadTeam";
    private static final String TAG_JSON3 = "loadRequest";
    private static final String TAG_PID = "pid";
    private static final String TAG_WRITER = "writer";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DEADLINE = "deadline";
    private static final String TAG_RECRUIT = "recruitment";
    private static final String TAG_AREA = "area";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_CATEGORY = "category";
    private static final String  TAG_COUNT = "team";
    private String mJsonString;
    private String mJsonString_team;
    private String mJsonString_request;
    //요청테이블 getData에 필요한 친구
    private static final String TAG_REQUEST = "userName";

    //팀원테이블 getData에 필요한 친구
    private static final String TAG_TEAM = "userName";

    String post_writer;
    int what;

    TextView category_name;
    TextView post_name_tv;
    TextView post_personnel_tv;
    TextView post_date_tv;
    TextView post_local_tv;
    TextView post_content_tv;
    RecyclerView post_member_listview;
    Button post_apply_btn;
    public static PostDetailFragment newInstance(){
        return new PostDetailFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.post_detail, container, false);

        category_name = (TextView) view.findViewById(R.id.category_name);
        post_name_tv = (TextView) view.findViewById(R.id.post_name_tv);
        post_personnel_tv = (TextView) view.findViewById(R.id.post_personnel_tv);
        post_date_tv = (TextView) view.findViewById(R.id.post_date_tv);
        post_local_tv = (TextView) view.findViewById(R.id.post_local_tv);
        post_content_tv = (TextView) view.findViewById(R.id.post_content_tv);
       // post_member_listview = (RecyclerView)view.findViewById(R.id.post_member_listview);


        ImageButton member_list_btn = (ImageButton)view.findViewById(R.id.member_list_btn);
        member_list_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(MemberListFragment.newInstance());
            }
        });

        post_apply_btn = (Button)view.findViewById(R.id.post_apply_btn);

        post_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleMsg;
                if(participate){ //팀원으로 소속되어있는 경우
                    titleMsg = " 참여중인 프로젝트를 나가시겠습니까?";
                }
                else if(request){ //참가신청만 했을 경우
                    titleMsg = " 이 게시물의 참여 신청을 취소하시겠습니까?";
                }
                else{ // 아무것도 아닌경우
                    titleMsg = " 이 게시물에 참여 신청을 하시겠습니까? ";

                }


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(titleMsg);

//                builder.setView(R.drawable.thank_you);

//                builder.setMessage(" ( 참여 신청들은 모두 거절됩니다. ) ");

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //팀원이 취소하기 버튼 누를경우
                        if(participate){ //팀원 table에서 delete , 알림 table insert
                            what = 0;
                            participate = false;
                            //신청하기 버튼 활성화
                            post_apply_btn.setText("신청하기");
                        }
                        //신청자가 취소하기 버튼 누를경우
                        else if(request){ //요청 table에서 delete, 알람 table에서 delete
                            what =1;
                            request = false;
                            //신청하기 버튼 활성화
                            post_apply_btn.setText("신청하기");

                        }
                        //신청하는 경우우
                        else{ // 요청 table insert, 알림 tble insert
                            what= 2;
                            request = true;
                            //신청하기 버튼 비활성화
                            post_apply_btn.setText("취소하기");
                        }



                        Response.Listener<String> responseListener = new Response.Listener<String>() {



                            @Override
                            public void onResponse(String response) {




                            }
                        };

                        //서버로 Volley를 이용해서 요청
                        PostSignRequest postSignRequest = new PostSignRequest(what, pid, MainActivity.userName,post_writer,post_name_tv.getText().toString(), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(PostDetailFragment.this.getActivity());
                        queue.add(postSignRequest);









                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        category_name.setText(CategoryFragment.category_str);



        PostDetailFragment.GetData_team task2 = new PostDetailFragment.GetData_team();
        task2.execute("http://steak2121.ivyro.net/loadTeam.php");

        PostDetailFragment.GetData_request task3= new PostDetailFragment.GetData_request();
        task3.execute("http://steak2121.ivyro.net/loadRequest.php");


        PostDetailFragment.GetData_post task = new PostDetailFragment.GetData_post();
        task.execute("http://steak2121.ivyro.net/loadPost.php");

        return view;
    }


    private class GetData_post extends AsyncTask<String, Void, String> {
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
                showResult_post();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = "http://steak2121.ivyro.net/loadPost.php";


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



    private void showResult_post() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String pid_str = item.getString(TAG_PID);
                String writer = item.getString(TAG_WRITER);
                String title = item.getString(TAG_TITLE);
                String deadline = item.getString(TAG_DEADLINE);
                String recruitment = item.getString(TAG_RECRUIT);
                String area = item.getString(TAG_AREA);
                String content = item.getString(TAG_CONTENT);
                String category = item.getString(TAG_CATEGORY);
                String userCount = item.getString(TAG_COUNT);


                if(pid_str.equals(Integer.toString(pid))){//pid 같을 경우


                    post_writer = writer;
                    category_name.setText(category);
                    post_name_tv .setText(title);
                    post_personnel_tv.setText("("+userCount + "/"+recruitment+")");
                    post_date_tv .setText(deadline);
                    post_local_tv.setText(area);
                    post_content_tv.setText(content);
                    //  post_member_listview = (RecyclerView)view.findViewById(R.id.post_member_listview);










                }


            }


        } catch (JSONException  e) {

            Log.d(TAG, "showResult : ", e);
        }


    }

//////////////////////////////////////////TEAM 정보 불러오기



    private class GetData_team extends AsyncTask<String, Void, String> {
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

            Log.d(TAG, "response  team- " + result);


            if (result == null) {


            } else {
                mJsonString_team = result;
                showResult_team();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = "http://steak2121.ivyro.net/loadTeam.php";


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



    private void showResult_team() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString_team);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON2);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String pid_str = item.getString(TAG_PID);
                String userName = item.getString(TAG_TEAM);

                if(pid_str.equals(Integer.toString(pid))){//pid 같을 경우

//팀원 추가 (닉네임)
                    //post_member_listview.add

                    if(userName.equals(MainActivity.userName))//팀원 이름이랑 내 이름이랑 같을때
                    {
                        //취소하기 버튼 활성화 .
                        participate = true;
                        post_apply_btn.setText("취소하기");
                    }
                }


            }


        } catch (JSONException  e) {

            Log.d(TAG, "showResult : ", e);
        }


    }


    //////////////////////////////////////////REQUEST 정보 불러오기



    private class GetData_request extends AsyncTask<String, Void, String> {
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

            Log.d(TAG, "response  request- " + result);


            if (result == null) {


            } else {
                mJsonString_request = result;
                showResult_request();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = "http://steak2121.ivyro.net/loadRequest.php";


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

    private void showResult_request() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString_request);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON3);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String pid_str = item.getString(TAG_PID);
                String userName = item.getString(TAG_TEAM);

                if(pid_str.equals(Integer.toString(pid))){//pid 같을 경우

//팀원 추가 (닉네임)
                    //post_member_listview.add

                    if(userName.equals(MainActivity.userName))//request에 있는 이름이랑 내 이름이랑 같을때
                    {
                        //취소하기 버튼 활성화 .
                        request = true;
                        post_apply_btn.setText("취소하기");
                    }
                }


            }


        } catch (JSONException  e) {

            Log.d(TAG, "showResult : ", e);
        }


    }


}

