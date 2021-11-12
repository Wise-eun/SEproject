package com.example.seproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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

public class MemberListFragment extends Fragment {
    ListView listView;
    Member_ListItemAdapter adapter;
    public static int pid; //게시물 ID
    public static String writer;

    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "loadTeam";
    private static final String TAG_JSON2 = "loadRating";

    private static final String TAG_PID = "pid";
    private static final String TAG_PID2 = "pid";

    private static final String TAG_USERNAME = "userName";
    private static final String TAG_SENDER = "sender";
    private static final String TAG_RECEIVER = "receiver";

    private String mJsonString_team;
    private String mJsonString_rating;

    View view;

    public static MemberListFragment newInstance(){
        return new MemberListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.member_list, container, false);
        listView = (ListView) view.findViewById(R.id.member_listview);

        MemberListFragment.GetData_team task = new MemberListFragment.GetData_team();
        task.execute("http://steak2121.ivyro.net/loadTeam.php");

        MemberListFragment.GetData_rating task2 = new MemberListFragment.GetData_rating();
        task2.execute("http://steak2121.ivyro.net/loadRating.php");

        return view;
    }

    private class GetData_team extends AsyncTask<String, Void, String> {
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
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            adapter = new Member_ListItemAdapter(new Member_ListItemAdapter.OnClickListener() {
                @Override
                public void onClick(View v, int pos, String type) {
                    Member_ListItem member = (Member_ListItem) adapter.getItem(pos);
                    if(type.equals("name")) {
                        //이름 눌렀을 때 발생시킬 이벤트
                        if(!member.getName().equals(MainActivity.userName)){
                            ProfileDetailActivity.userName = member.getName();

                            Intent intent = new Intent(getContext(), ProfileDetailActivity.class);
                            startActivity(intent);
                        }
                    }
                    else{
                        String userName = member.getName();

                        Dialog dialog = new Dialog(getContext());

                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.rating_dialog);
                        TextView dialog_title = (TextView)dialog.findViewById(R.id.dialog_title);
                        Button submit_btn = (Button)dialog.findViewById(R.id.submit_btn);
                        submit_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RatingBar dialog_ratingbar = (RatingBar)dialog.findViewById(R.id.dialog_ratingbar);

                                Float rating = dialog_ratingbar.getRating();

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
//                                        try {
//                                            JSONObject jsonObject = new JSONObject(response);
//                                            boolean success = jsonObject.getBoolean("success");
//                                            if (success) {
//                                                Toast.makeText(getActivity(),"평가 완료",Toast.LENGTH_SHORT).show();
//                                            }
//                                            else{
//                                                Toast.makeText(getActivity(),"평가 실패",Toast.LENGTH_SHORT).show();
//                                                return ;
//                                            }
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }

                                    }
                                };
                                //서버로 Volley를 이용해서 요청

                                RatingRequest ratingRequest = new RatingRequest(MainActivity.userName, userName, pid, rating, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(MemberListFragment.this.getActivity());
                                queue.add(ratingRequest);

                                dialog.cancel();

                                listView.setAdapter(adapter);
                            }
                        });

                        Button cancel_btn = (Button)dialog.findViewById(R.id.cancel_btn);
                        cancel_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        dialog_title.setText("'" + userName + "'님에게 평점을 남기겠습니까?");
                        dialog.show();


                    }

                }
            });

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String pid_str = item.getString(TAG_PID);
                String userName = item.getString(TAG_USERNAME);

                if(pid_str.equals(Integer.toString(pid))){//pid 같을 경우
                    //팀원 추가 (닉네임)
                    if(writer.equals(userName)){
                        adapter.addItem(new Member_ListItem(userName, true, true));
                    }
                    else{
                        adapter.addItem(new Member_ListItem(userName, false, true));
                    }
                }
            }

            listView.setAdapter(adapter);

        } catch (JSONException  e) {

            Log.d(TAG, "showResult : ", e);
        }


    }

    private class GetData_rating extends AsyncTask<String, Void, String> {
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
                mJsonString_rating = result;
                showResult_rating();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = "http://steak2121.ivyro.net/loadRating.php";

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

    private void showResult_rating() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString_rating);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON2);

            ArrayList<String> receiver_arr = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String pid_str = item.optString(TAG_PID2, "no value");
                String sender = item.optString(TAG_SENDER, "no value");
                String receiver = item.optString(TAG_RECEIVER, "no value");

                if(sender.equals(MainActivity.userName)){
                    receiver_arr.add(receiver);
                }
            }

            for (int i = 0 ; i<receiver_arr.size(); i++){
                for (int j = 0; j<adapter.getCount(); j++){
                    Member_ListItem item = (Member_ListItem) adapter.getItem(j);
                    if (item.getName().equals(receiver_arr.get(i))){
                        item.setComplete(false);
                        break;
                    }
                }
            }

            listView.setAdapter(adapter);

        } catch (JSONException  e) {

            Log.d(TAG, "showResult : ", e);
        }
    }
}