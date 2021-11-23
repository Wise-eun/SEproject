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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CategoryFragment extends Fragment {
    private ListView listView;
    private Post_ListItemAdapter adapter;
    public static String category_str = "";
    public static int deletion;

    private static final String TAG_DELETE = "deletion";
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
    private static final String TAG_NAME = "userName";
    private String mJsonString;
    String category ;
    public static int last_pid;
    private AlertDialog dialog;
    TextView category_name;
    ImageButton write_btn;



    public static CategoryFragment newInstance(){
        return new CategoryFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category, container, false);

        Bundle bundle = getArguments();

        category_name = (TextView) v.findViewById(R.id.category_name);

        if (bundle != null){
            category = bundle.getString("category");
            category_name.setText(category);
            category_str = category;
        }
        else{
            category_name.setText("error");
        }

        listView = (ListView) v.findViewById(R.id.listView);
        adapter = new Post_ListItemAdapter();

        write_btn = (ImageButton) v.findViewById(R.id.trash_btn);
        write_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(deletion <5 )
                ((MainActivity)getActivity()).replaceFragment(PostWriteFragment.newInstance());
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(CategoryFragment.this.getActivity());
                    dialog = builder.setMessage("게시글 삭제 횟수 5회 초과로 더이상 게시글을 작성하실수 없습니다.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
            }

        });

        CategoryFragment.GetData task = new CategoryFragment.GetData();
        task.execute("http://steak2121.ivyro.net/loadUser.php");

        CategoryFragment.GetData_post task2 = new CategoryFragment.GetData_post();
        task2.execute("http://steak2121.ivyro.net/loadPost.php");

        return v;
    }

/////date Load User
private class GetData extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    String errorString = null;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (result == null) {}
        else {
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
                String name = item.getString(TAG_NAME);
                String  deletion_str = item.getString(TAG_DELETE);

                if (name.equals(MainActivity.userName)) // 현재 사용자 ID와 서버에있는 정보중 ID가 같은것들의 정보만 가져옴
                {
                    deletion = Integer.parseInt(deletion_str); //삭제횟수 저장
                }
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }


    ////data Load Post

   private class GetData_post extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;


        @Override
        protected void onPreExecute() {}

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {}
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

            if(category2.equals(category)){//카테고리 같을 경우

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
                    adapter.addItem(new Post_ListItem(pid,writer, title, "("+userCount + "/"+recruitment+")", Ddays_str,deadline));

                 }
            }

            last_pid = Integer.parseInt(pid_str); // 마지막 게시물 번호받기
            adapter.sortItem();

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Post_ListItem item = (Post_ListItem) adapter.getItem(position);

                    if(item.getWriter().equals(MainActivity.userName))//작성자와 해당사용자가 같을경우
                    {
                        ((MainActivity) getActivity()).replaceFragment(MyPostDetailFragment.newInstance());
                        MyPostDetailFragment.pid = item.getPid();
                    }
                    else {
                        ((MainActivity) getActivity()).replaceFragment(PostDetailFragment.newInstance());
                        PostDetailFragment.pid = item.getPid();
                    }
                    }
            });
            }

        }
        catch (JSONException | ParseException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
