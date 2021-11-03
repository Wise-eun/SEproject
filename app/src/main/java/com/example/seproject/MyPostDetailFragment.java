package com.example.seproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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
import java.util.ArrayList;

public class MyPostDetailFragment extends Fragment {
    public static int pid; //게시물 ID

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
ArrayList<String> request_users= new ArrayList<String>();;
    //팀원테이블 getData에 필요한 친구
    private static final String TAG_TEAM = "userName";

    int whereIn;

    TextView category_name;
    TextView post_name_tv;
    TextView post_personnel_tv;
    TextView post_date_tv;
    TextView post_local_tv;
    TextView post_content_tv;
    RecyclerView post_member_listview;

    String post_writer;


    public MyPostDetailFragment() {}

    public static MyPostDetailFragment newInstance(){
        return new MyPostDetailFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.mypost_detail, container, false);


        category_name = (TextView) view.findViewById(R.id.category_name);
        post_name_tv = (TextView) view.findViewById(R.id.post_name_tv);
        post_personnel_tv = (TextView) view.findViewById(R.id.post_personnel_tv);
        post_date_tv = (TextView) view.findViewById(R.id.post_date_tv);
        post_local_tv = (TextView) view.findViewById(R.id.post_local_tv);
        post_content_tv = (TextView) view.findViewById(R.id.post_content_tv);
        post_member_listview = (RecyclerView)view.findViewById(R.id.post_member_listview);


        request_users = new ArrayList<String>();
        Button post_complete_btn = view.findViewById(R.id.post_complete_btn);
        Button post_delete_btn = view.findViewById(R.id.post_delete_btn);
        Button post_edit_btn = view.findViewById(R.id.post_edit_btn);

        ImageButton member_list_btn = (ImageButton)view.findViewById(R.id.member_list_btn);
        member_list_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(MemberListFragment.newInstance());
                MemberListFragment.pid = pid;
                MemberListFragment.writer = post_writer;
                Member_ListItemAdapter.isRating = false;
            }
        });

        post_complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleMsg = " 이 게시물을 완료하시겠습니까? ";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(titleMsg);
                ArrayList<MyPostCompleteRequest> requests  = new ArrayList<MyPostCompleteRequest> ();
                RequestQueue queue = Volley.newRequestQueue(MyPostDetailFragment.this.getActivity());
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        for(int i=0;i<request_users.size();i++) {


                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            };
                            if(i==0) {
                                requests.add(new MyPostCompleteRequest(3, pid, request_users.get(i), MainActivity.userName, responseListener));
                            }
                            else{
                                requests.add(new MyPostCompleteRequest(2, pid, request_users.get(i), MainActivity.userName, responseListener));
                            }
                        }

                        for(int j=0;j<requests.size();j++)
                            queue.add(requests.get(j));

                        ((MainActivity)getActivity()).replaceFragment(PostCompleteFragment.newInstance());
                        PostCompleteFragment.pid = pid;

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



        post_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleMsg = " 이 게시물을 삭제하시겠습니까? ";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(titleMsg);

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {



                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        };


                        MyPostDeleteRequest myPostDeleteRequest = new MyPostDeleteRequest(pid, MainActivity.userName, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(MyPostDetailFragment.this.getActivity());
                        queue.add(myPostDeleteRequest);

                        ((MainActivity)getActivity()).replaceFragment(HomeFragment.newInstance());

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

        post_edit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(MyPostModifyFragment.newInstance());
                MyPostModifyFragment.pid = pid;
            }
        });



        MyPostDetailFragment.GetData_post task = new MyPostDetailFragment.GetData_post();
        task.execute("http://steak2121.ivyro.net/loadPost.php");


        MyPostDetailFragment.GetData_team task2 = new MyPostDetailFragment.GetData_team();
        task2.execute("http://steak2121.ivyro.net/loadTeam.php");

        MyPostDetailFragment.GetData_request task3= new MyPostDetailFragment.GetData_request();
        task3.execute("http://steak2121.ivyro.net/loadRequest.php");


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


                    break;
                }


            }


        } catch (JSONException e) {

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

            PostMember_ListItemAdapter adapter = new PostMember_ListItemAdapter();

            post_member_listview.setAdapter(adapter);
            post_member_listview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

            ArrayList<PostMember_ListItem> items = new ArrayList<PostMember_ListItem>();


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String pid_str = item.getString(TAG_PID);
                String userName = item.getString(TAG_TEAM);

                if(pid_str.equals(Integer.toString(pid))){//pid 같을 경우

//팀원 추가 (닉네임)
                    //post_member_listview.add
                    if(post_writer.equals(userName)){
                        items.add(new PostMember_ListItem(userName, true));
                    }
                    else{
                        items.add(new PostMember_ListItem(userName, false));
                    }
                }


            }

            adapter.setPostMemberItems(items);


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

                        request_users.add(userName); //요청한 사람들 Name 가져와서 배열에 저장함

                }


            }
            Log.d(TAG, "===========================================users   " );
            for(int i=0; i<request_users.size();i++)
                Log.d(TAG, "=========users :  " + request_users.get(i));
        } catch (JSONException  e) {

            Log.d(TAG, "showResult : ", e);
        }


    }



}