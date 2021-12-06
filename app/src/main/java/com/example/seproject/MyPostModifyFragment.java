package com.example.seproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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

public class MyPostModifyFragment extends Fragment {

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

    Button modify_btn;
    TextView category_name;
    TextView mypost_title_tv;
    TextView write_day_tv;
    TextView write_local_tv;
    TextView modify_personnel_tv;
    EditText content_et;


    public static MyPostModifyFragment newInstance(){
        return new MyPostModifyFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mypost_modify, container, false);

        category_name = (TextView) view.findViewById(R.id.category_name);
        mypost_title_tv = (TextView) view.findViewById(R.id.mypost_title_tv);
        write_day_tv = (TextView) view.findViewById(R.id.write_day_tv);
        write_local_tv = (TextView) view.findViewById(R.id.write_local_tv);
        content_et = (EditText) view.findViewById(R.id.content_et);
        modify_personnel_tv = (TextView) view.findViewById(R.id.modify_personnel_tv);
        modify_btn= (Button) view.findViewById(R.id.modify_btn);

        GetData_post task = new GetData_post();
        task.execute("http://steak2121.ivyro.net/loadPost.php");

        modify_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               String update_content=  content_et.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) { // 성공한 경우

                            Toast.makeText(getActivity(),"게시물이 수정되었습니다.",Toast.LENGTH_SHORT).show();

                        } else {// 실패한 경우
                            Toast.makeText(getActivity(), "수정이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            PostModifyRequest postModifyRequest = new PostModifyRequest(pid, update_content, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MyPostModifyFragment.this.getActivity());
            queue.add(postModifyRequest);



                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //수정된 게시물로 다시 이동
                        ((MainActivity)getActivity()).replaceFragment(MyPostDetailFragment.newInstance());
                        MyPostDetailFragment.pid = pid;
                    }
                }, 500); //딜레이 타임 조절





            }
        });


        return view;
    }



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

                    category_name.setText(category);
                    mypost_title_tv .setText(title);
                    modify_personnel_tv.setText(recruitment+"명");
                    write_day_tv .setText(deadline);
                    write_local_tv.setText(area);
                    content_et.setText(content);

                    break;
                }
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }
}