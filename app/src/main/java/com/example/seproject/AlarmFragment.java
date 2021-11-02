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
import android.widget.ImageButton;
import android.widget.ListView;

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

public class AlarmFragment extends Fragment {
    private Alarm_ListItemAdapter adapter;
    ListView listView;
    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_SENDER = "sender";
    private static final String TAG_RECEIVER = "receiver";
    private static final String TAG_PID = "pid";
    private static final String TAG_TYPE = "type";
    private static final String TAG_TITLE = "title";
    private String mJsonString;
    String userID,userName;


    public static AlarmFragment newInstance() {
        return new AlarmFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.alarm, container, false);
        listView = (ListView) view.findViewById(R.id.alarm_listview);


        userID = MainActivity.userID;
        userName = MainActivity.userName;

        adapter = new Alarm_ListItemAdapter(new Alarm_ListItemAdapter.OnDeleteClickListener() {
            @Override
            public void onDelete(View v, int pos, String type) {
                Alarm_ListItem alarm = (Alarm_ListItem) adapter.getItem(pos);

                if(type.equals("accept")){
                    //수락 눌렀을 때 발생시킬 이벤트
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(alarm.getSender() + "님의 참여 신청을 수락하시겠습니까? ");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
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
                else{
                    //거절 눌렀을 때 발생시킬 이벤트
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(alarm.getSender() + "님의 참여 신청을 거절하시겠습니까? ");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    builder.show();                }
            }
        });

//        adapter.addItem(new Alarm_ListItem("User_0901", "개인 프로젝트 해서 포폴 만드...", 0));
//        adapter.addItem(new Alarm_ListItem("User_0214","초고수 모집합니다.", 1));
//        adapter.addItem(new Alarm_ListItem("User_0214", "ㄴㄴ 공모전", 2));
//        adapter.addItem(new Alarm_ListItem("User_0214", "", 3));
//        adapter.addItem(new Alarm_ListItem("User_0214", "개인 프로젝트 해서 포폴 만드...", 4));

        ImageButton trash_btn = (ImageButton)view.findViewById(R.id.trash_btn);
        trash_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(" 모든 소식을 삭제하시겠습니까? ");

//                builder.setView(R.drawable.thank_you);

                builder.setMessage(" ( 참여 신청들은 모두 거절됩니다. ) ");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
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


        AlarmFragment.GetData task = new AlarmFragment.GetData();
        task.execute("http://steak2121.ivyro.net/loadAlarm.php");

        return view;

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

            String serverURL = "http://steak2121.ivyro.net/loadAlarm.php";


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

            for (int i = jsonArray.length()-1; i >= 0 ; i--) {

                JSONObject item = jsonArray.getJSONObject(i);

                String sender = item.getString(TAG_SENDER);
                String receiver = item.getString(TAG_RECEIVER);
                String type = item.getString(TAG_TYPE);
                String pid_str = item.getString(TAG_PID);
                String title = item.getString(TAG_TITLE);

                if(userName.equals(receiver)){
                    adapter.addItem(new Alarm_ListItem(sender, title , Integer.parseInt(type)));
                }


            }
            listView.setAdapter(adapter);


        }
        catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

}
