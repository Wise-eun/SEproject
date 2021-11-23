package com.example.seproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
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
    private static final String TAG_DELETETITLE = "deleteTitle";
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

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    boolean success = jsonObject.getBoolean("success");
//                    if (!success)
//                    {// 실패한 경우
//                        Toast.makeText(getActivity(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        };

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

                            AlarmRequest alarmRequest = new AlarmRequest(alarm.getType(), alarm.getPid(), alarm.getSender(), userName, 0, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(AlarmFragment.this.getActivity());
                            queue.add(alarmRequest);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //프로필 화면으로 다시 이동
                                    getActivity().getSupportFragmentManager().popBackStack();
                                    ((MainActivity) getActivity()).replaceFragment(AlarmFragment.newInstance());
                                }
                            }, 500); //딜레이 타임 조절



//                            adapter.deleteItem(pos);
//                            adapter.notifyDataSetChanged();

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

                            AlarmRequest alarmRequest = new AlarmRequest(alarm.getType(), alarm.getPid(), alarm.getSender(), userName, 1, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(AlarmFragment.this.getActivity());
                            queue.add(alarmRequest);

//                            adapter.deleteItem(pos);
//                            adapter.notifyDataSetChanged();
                            getActivity().getSupportFragmentManager().popBackStack();
                            ((MainActivity) getActivity()).replaceFragment(AlarmFragment.newInstance());
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


            }
        });

        ImageButton trash_btn = (ImageButton)view.findViewById(R.id.trash_btn);
        trash_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(" 모든 소식을 삭제하시겠습니까? ");

                builder.setMessage(" ( 참여 신청들은 모두 거절됩니다. ) ");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                        for(int i = 0 ; i<adapter.getCount(); i++){
                            Alarm_ListItem item = (Alarm_ListItem) adapter.getItem(i);
                            if(item.getType() == 0){
                                //sender에게 거절 알림 보내기 request
                                AlarmRequest alarmRequest = new AlarmRequest(item.getType(), item.getPid(), item.getSender(), userName, 3, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(AlarmFragment.this.getActivity());
                                queue.add(alarmRequest);
                            }
                        }

                        AlarmRequest alarmRequest = new AlarmRequest(-1, -1, "", userName, 2, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(AlarmFragment.this.getActivity());
                        queue.add(alarmRequest);

//                        adapter.clearItem();
//                        adapter.notifyDataSetChanged();

                        getActivity().getSupportFragmentManager().popBackStack();
                        ((MainActivity) getActivity()).replaceFragment(AlarmFragment.newInstance());
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
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {}
            else {
                mJsonString = result;
                try {
                    showResult();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private void showResult() throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            adapter.clearItem();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String sender = item.getString(TAG_SENDER);
                String receiver = item.getString(TAG_RECEIVER);
                String type = item.getString(TAG_TYPE);
                String pid_str = item.getString(TAG_PID);
                String title = item.getString(TAG_TITLE);
                String deleteTitle = item.getString(TAG_DELETETITLE);

                if (userName.equals(receiver) && !sender.equals(receiver)) {
                    if (type.equals("5") || type.equals("6")) {
                        adapter.addItem(new Alarm_ListItem(sender, deleteTitle, Integer.parseInt(type), Integer.parseInt(pid_str)));
                    } else {
                        adapter.addItem(new Alarm_ListItem(sender, title, Integer.parseInt(type), Integer.parseInt(pid_str)));
                    }
                }
                listView.setAdapter(adapter);

            }
        }
        catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

}