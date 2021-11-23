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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
import java.util.Calendar;
import java.util.Date;

public class MyPostFragment extends Fragment {
    ListView listView;
    Post_ListItemAdapter post_adapter;

    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
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


    public MyPostFragment(){}
    public static MyPostFragment newInstance(){
        return new MyPostFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mypost, container, false);
        listView = (ListView) view.findViewById(R.id.post_listview);
        post_adapter = new Post_ListItemAdapter();

        MyPostFragment.GetData_post task = new MyPostFragment.GetData_post();
        task.execute("http://steak2121.ivyro.net/loadPost.php");

        return view;
    }


    ////data Load Post

    private class GetData_post extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) { }
            else {
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
                String category2 = item.getString(TAG_CATEGORY);
                String userCount = item.getString(TAG_COUNT);

                if(writer.equals(MainActivity.userName)){//글쓴이와 사용자의 이름이 같을경우

                    Calendar cal = Calendar.getInstance();
                    cal.setTime( new Date(System.currentTimeMillis()));
                    String today_str = new SimpleDateFormat("yyyy-MM-dd").format( cal.getTime()); // 오늘날짜

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    Date date = new Date(dateFormat.parse(deadline).getTime()); //deadline 문자열을 date형식으로 바꿈
                    Date today = new Date(dateFormat.parse(today_str).getTime()); // 오늘날짜 문자열을 date형식으로 바꿈

                    long calculate = date.getTime() - today.getTime();
                    int Ddays = (int) (calculate / ( 24*60*60*1000));
                    Log.d(TAG, "Ddays = "+Ddays);
                    if(Ddays >=0){ //디데이가 안지났을때 보이도록함
                        String Ddays_str = "D-";
                        Ddays_str = Ddays_str.concat( Integer.toString(Ddays));

                        int pid = Integer.parseInt(pid_str);
                        post_adapter.addItem(new Post_ListItem(pid,writer, title, "("+userCount + "/"+recruitment+")", Ddays_str,deadline));

                    }
                    else if(Math.abs(Ddays) < 60){
                        String Ddays_str = "D+";
                        Ddays_str = Ddays_str.concat(Integer.toString(Math.abs(Ddays)));

                        int pid = Integer.parseInt(pid_str);
                        post_adapter.addItem(new Post_ListItem(pid, writer, title, "(" + userCount + "/" + recruitment + ")", Ddays_str, deadline));

                    }
                    else{
                        String Ddays_str = "완료";

                        int pid = Integer.parseInt(pid_str);
                        post_adapter.addItem(new Post_ListItem(pid, writer, title, "(" + userCount + "/" + recruitment + ")", Ddays_str, deadline));

                    }
                }

                post_adapter.sortItem();
                listView.setAdapter(post_adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Post_ListItem item = (Post_ListItem) post_adapter.getItem(position);

                        try {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new Date(System.currentTimeMillis()));

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String today_str = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()); // 오늘날짜
                            Date today = new Date(dateFormat.parse(today_str).getTime()); // 오늘날짜 문자열을 date형식으로 바꿈

                            if(today.after(dateFormat.parse(item.getDeadline())) ){ // deadline이 지난경우
                                ((MainActivity) getActivity()).replaceFragment(PostCompleteFragment.newInstance());
                                PostCompleteFragment.pid = item.getPid();
                            }
                            else{ // 현재 모집중인 상태
                                ((MainActivity) getActivity()).replaceFragment(MyPostDetailFragment.newInstance());
                                MyPostDetailFragment.pid = item.getPid();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (JSONException | ParseException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
