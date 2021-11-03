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

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MsgListFragment extends Fragment {

    ListView listView;
    Msg_ListItemAdapter adapter;

    View view;
    String userID,userName;
    public static String targetID, targetName;
    ImageButton write_msg_btn;
    ArrayList<String> msg_users = new ArrayList<String>();
    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_SENDER = "sender";
    private static final String TAG_RECEIVER = "receiver";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_DATE = "date";
    private String mJsonString;

    public static MsgListFragment newInstance() {
        return new MsgListFragment();
    }
//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_list, container, false);
         listView = (ListView) view.findViewById(R.id.msg_list_listview);
        adapter = new Msg_ListItemAdapter();
        userID = MainActivity.userID;
        userName = MainActivity.userName;

        write_msg_btn = (ImageButton) view.findViewById(R.id.write_msg_btn);



        write_msg_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(SearchUserFragment.newInstance());

            }
        });



        MsgListFragment.GetData task = new MsgListFragment.GetData();
        task.execute("http://steak2121.ivyro.net/loadMessage.php");

        listView.setAdapter(adapter);
        return view;
    }



    ////data Load

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

            String serverURL = "http://steak2121.ivyro.net/loadMessage.php";


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

                String sender = item.getString(TAG_SENDER);
                String receiver = item.getString(TAG_RECEIVER);
                String content = item.getString(TAG_CONTENT);
                String date = item.getString(TAG_DATE);




                if (userName.equals(sender) || userName.equals(receiver)) // 보낸사람 또는 받는사람이 사용자일 경우
                {

                    if(userName.equals(receiver))// 받는사람이 사용자 일 경우
                    {
                        String you = sender;
                        if(msg_users.contains(you))//전에 check한 상대방일 경우
                        {}
                        else { //check하지않은 상대방일 경우 추가
                            msg_users.add(sender);
                            adapter.addItem(new Msg_ListItem(sender, content, date));
                        }
                    }
                    else if(userName.equals(sender)) //보낸사람이 사용자일경우
                    {
                        String you = receiver;
                        if(msg_users.contains(you))//전에 check한 상대방일 경우
                        {}
                        else { //check하지않은 상대방일 경우 추가
                            msg_users.add(receiver);
                            adapter.addItem(new Msg_ListItem(receiver, content, date));
                        }
                    }

                }


            }
            msg_users = new ArrayList<String>();
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Msg_ListItem item = (Msg_ListItem) adapter.getItem(position);

                    ((MainActivity)getActivity()).replaceFragment(MsgDetailFragment.newInstance()); //화면전환
                    MsgDetailFragment.where_in = 1;
                    targetName = item.getType();
                }
            });
        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }


    }



}