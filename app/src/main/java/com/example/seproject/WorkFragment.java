package com.example.seproject;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.seproject.databinding.MypostDetailBinding;

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

public class WorkFragment extends Fragment {

    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_JSON2 = "loadTeam";
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
    //팀원테이블 getData에 필요한 친구
    private static final String TAG_TEAM = "userName";
int type =0;
    Date today;
    ArrayList<String> pids = new ArrayList<String>();

    private ListView listView;
    private Post_ListItemAdapter adapter;

    RadioGroup radio_group;
    public static WorkFragment newInstance(){
        return new WorkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_work, container, false);

        listView = (ListView) view.findViewById(R.id.listView);

        adapter = new Post_ListItemAdapter();

        radio_group = (RadioGroup) view.findViewById(R.id.radio_group);
        radio_group.setOnCheckedChangeListener(radioGroupButtonChangeListener);


        pids = new ArrayList<String>();

        WorkFragment.GetData_team task2 = new WorkFragment.GetData_team();
        task2.execute("http://steak2121.ivyro.net/loadTeam.php");








        return view;
    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener()
    { @Override
    public void onCheckedChanged(RadioGroup radioGroup,  int i)
    {
        if(i == R.id.radio_button_all)
        {
            type =0;
            adapter = new Post_ListItemAdapter();

            WorkFragment.GetData_post task = new WorkFragment.GetData_post();
            task.execute("http://steak2121.ivyro.net/loadPost.php");



        }
        else if(i == R.id.radio_button_ing){
            type =1;
            adapter = new Post_ListItemAdapter();

            WorkFragment.GetData_post task = new WorkFragment.GetData_post();
            task.execute("http://steak2121.ivyro.net/loadPost.php");

        }
        else if(i == R.id.radio_button_complete)
        {
            type =2;
            adapter = new Post_ListItemAdapter();

            WorkFragment.GetData_post task = new WorkFragment.GetData_post();
            task.execute("http://steak2121.ivyro.net/loadPost.php");

        }
    }

    };


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



                    if(userName.equals(MainActivity.userName))//팀원 이름이랑 내 이름이랑 같을때
                    {
                        pids.add( pid_str); //pid 추가
                        }



            }


        } catch (JSONException  e) {

            Log.d(TAG, "showResult : ", e);
        }


        WorkFragment.GetData_post task = new WorkFragment.GetData_post();
        task.execute("http://steak2121.ivyro.net/loadPost.php");



    }

    ////data Load Post

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
            if(type == 0){ //전체 눌렀을때
                adapter.clearItems();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    String pid_str = item.getString(TAG_PID);
                    String writer = item.getString(TAG_WRITER);
                    String title = item.getString(TAG_TITLE);
                    String deadline = item.getString(TAG_DEADLINE);
                    String recruitment = item.getString(TAG_RECRUIT);
                    String area = item.getString(TAG_AREA);
                    String content = item.getString(TAG_CONTENT);
                    String category2 = item.getString(TAG_CATEGORY);
                    String userCount = item.getString(TAG_COUNT);

                    if (pids.contains(pid_str)) {//내가 해당 게시물 팀에 속해있는 경우

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new Date(System.currentTimeMillis()));
                        String today_str = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()); // 오늘날짜

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        Date date = new Date(dateFormat.parse(deadline).getTime()); //deadline 문자열을 date형식으로 바꿈
                        today = new Date(dateFormat.parse(today_str).getTime()); // 오늘날짜 문자열을 date형식으로 바꿈

                        long calculate = date.getTime() - today.getTime();
                        int Ddays = (int) (calculate / (24 * 60 * 60 * 1000));
                        Log.d(TAG, "Ddays = " + Ddays);
                        if (Ddays >= 0) { //디데이가 안지났을때 보이도록함
                            String Ddays_str = "D-";
                            Ddays_str = Ddays_str.concat(Integer.toString(Ddays));

                            int pid = Integer.parseInt(pid_str);
                            adapter.addItem(new Post_ListItem(pid, writer, title, "(" + userCount + "/" + recruitment + ")", Ddays_str,deadline));


                            Log.d(TAG, "date = " + date + " Today = " + today);


                        }
                        else if(Math.abs(Ddays) < 60){
                            String Ddays_str = "D+";
                            Ddays_str = Ddays_str.concat(Integer.toString(Math.abs(Ddays)));

                            int pid = Integer.parseInt(pid_str);
                            adapter.addItem(new Post_ListItem(pid, writer, title, "(" + userCount + "/" + recruitment + ")", Ddays_str, deadline));


                            Log.d(TAG, "date = " + date + " Today = " + today);

                        }
                        else{
                            String Ddays_str = "완료";

                            int pid = Integer.parseInt(pid_str);
                            adapter.addItem(new Post_ListItem(pid, writer, title, "(" + userCount + "/" + recruitment + ")", Ddays_str, deadline));

                        }
                    }

                }

            }

            else if( type == 1 ){ //진행중 눌렀을때
                adapter.clearItems();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    String pid_str = item.getString(TAG_PID);
                    String writer = item.getString(TAG_WRITER);
                    String title = item.getString(TAG_TITLE);
                    String deadline = item.getString(TAG_DEADLINE);
                    String recruitment = item.getString(TAG_RECRUIT);
                    String area = item.getString(TAG_AREA);
                    String content = item.getString(TAG_CONTENT);
                    String category2 = item.getString(TAG_CATEGORY);
                    String userCount = item.getString(TAG_COUNT);


                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date(System.currentTimeMillis()));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String today_str = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()); // 오늘날짜
                    today = new Date(dateFormat.parse(today_str).getTime()); // 오늘날짜 문자열을 date형식으로 바꿈
                    Date date = new Date(dateFormat.parse(deadline).getTime()); //deadline 문자열을 date형식으로 바꿈

                    if (pids.contains(pid_str) && today.before(date)) {//내가 해당 게시물 팀에 속해있는 경우 & 오늘이 deadline보다 적은경우


                        today = new Date(dateFormat.parse(today_str).getTime()); // 오늘날짜 문자열을 date형식으로 바꿈

                        long calculate = date.getTime() - today.getTime();
                        int Ddays = (int) (calculate / (24 * 60 * 60 * 1000));
                        Log.d(TAG, "Ddays = " + Ddays);
                        if (Ddays >= 0) { //디데이가 안지났을때 보이도록함
                            String Ddays_str = "D-";
                            Ddays_str = Ddays_str.concat(Integer.toString(Ddays));

                            int pid = Integer.parseInt(pid_str);
                            adapter.addItem(new Post_ListItem(pid, writer, title, "(" + userCount + "/" + recruitment + ")", Ddays_str,deadline));


                            Log.d(TAG, "date = " + date + " Today = " + today);


                        }



                    }



                }
            }

            else if(type == 2){//완료 눌렀을때
                adapter.clearItems();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    String pid_str = item.getString(TAG_PID);
                    String writer = item.getString(TAG_WRITER);
                    String title = item.getString(TAG_TITLE);
                    String deadline = item.getString(TAG_DEADLINE);
                    String recruitment = item.getString(TAG_RECRUIT);
                    String area = item.getString(TAG_AREA);
                    String content = item.getString(TAG_CONTENT);
                    String category2 = item.getString(TAG_CATEGORY);
                    String userCount = item.getString(TAG_COUNT);


                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date(System.currentTimeMillis()));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String today_str = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()); // 오늘날짜
                    today = new Date(dateFormat.parse(today_str).getTime()); // 오늘날짜 문자열을 date형식으로 바꿈
                    Date date = new Date(dateFormat.parse(deadline).getTime()); //deadline 문자열을 date형식으로 바꿈

                    if (pids.contains(pid_str) && today.after(date)) {//내가 해당 게시물 팀에 속해있는 경우 & 오늘이 deadline보다 많은경우


                        today = new Date(dateFormat.parse(today_str).getTime()); // 오늘날짜 문자열을 date형식으로 바꿈

                        long calculate = date.getTime() - today.getTime();
                        int Ddays = (int) (calculate / (24 * 60 * 60 * 1000));
                        Log.d(TAG, "Ddays = " + Ddays);
                        if (Ddays >= 0) { //디데이가 안지났을때 보이도록함
                            String Ddays_str = "D-";
                            Ddays_str = Ddays_str.concat(Integer.toString(Ddays));

                            int pid = Integer.parseInt(pid_str);
                            adapter.addItem(new Post_ListItem(pid, writer, title, "(" + userCount + "/" + recruitment + ")", Ddays_str,deadline));


                            Log.d(TAG, "date = " + date + " Today = " + today);


                        }
                        else if(Math.abs(Ddays) < 60){
                            String Ddays_str = "D+";
                            Ddays_str = Ddays_str.concat(Integer.toString(Math.abs(Ddays)));

                            int pid = Integer.parseInt(pid_str);
                            adapter.addItem(new Post_ListItem(pid, writer, title, "(" + userCount + "/" + recruitment + ")", Ddays_str, deadline));


                            Log.d(TAG, "date = " + date + " Today = " + today);

                        }
                        else{
                            String Ddays_str = "완료";

                            int pid = Integer.parseInt(pid_str);
                            adapter.addItem(new Post_ListItem(pid, writer, title, "(" + userCount + "/" + recruitment + ")", Ddays_str, deadline));

                        }

                    }



                }


            }

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Post_ListItem item = (Post_ListItem) adapter.getItem(position);
                    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");


                    try {
                        if(today.after(transFormat.parse(item.getDeadline())) ) { // deadline이 지난경우
                            ((MainActivity) getActivity()).replaceFragment(PostCompleteFragment.newInstance());
                            PostCompleteFragment.pid = item.getPid();
                        }
                        else if (item.getWriter().equals(MainActivity.userName)){ // 현재 모집중인 상태
                            ((MainActivity) getActivity()).replaceFragment(MyPostDetailFragment.newInstance());
                            MyPostDetailFragment.pid = item.getPid();
                        }
                        else{
                            ((MainActivity) getActivity()).replaceFragment(PostDetailFragment.newInstance());
                            PostDetailFragment.pid = item.getPid();
                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (JSONException | ParseException e) {

            Log.d(TAG, "showResult : ", e);
        }


    }





}