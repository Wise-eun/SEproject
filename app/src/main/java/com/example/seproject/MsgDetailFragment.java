package com.example.seproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MsgDetailFragment extends Fragment {

    public static MsgDetailFragment newInstance() {
        return new MsgDetailFragment();
    }
TextView msg_user_name_tv;
    public static String msg_you_name;

    Msg_ListItemAdapter adapter;
    String userName;
    ListView listView;
    ImageButton write_msg_btn;
public static int where_in =0;
    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_SENDER = "sender";
    private static final String TAG_RECEIVER = "receiver";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_DATE = "date";
    private String mJsonString;
View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.message_detail, container, false);



userName = MainActivity.userName;

        msg_user_name_tv = (TextView)v.findViewById(R.id.msg_user_name_tv);
        write_msg_btn = (ImageButton) v.findViewById(R.id.write_msg_btn);
        listView = (ListView) v.findViewById(R.id.msg_detail_listview);
        adapter = new Msg_ListItemAdapter();

        write_msg_btn.setOnClickListener(new View.OnClickListener() //
        { @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
//메세지 전송으로 화면이동
           // SearchUserFragment.search_name = null; // write 들어가는 경로 다른거 차단.
            SendMsgFragment.where_in = 1;
            ((MainActivity)getActivity()).replaceFragment(SendMsgFragment.newInstance()); //화면전환

        } });

        if(where_in==2)//검색-메세지전송- 에서 들어왔을 경우
        {msg_you_name = SendMsgFragment.receiver;
            msg_user_name_tv.setText(msg_you_name);}

        else  if(where_in==1)//msgList-메세지전송- 에서 들어왔을 경우
        {msg_you_name = MsgListFragment.targetName;
            msg_user_name_tv.setText(msg_you_name);}



        MsgDetailFragment.GetData task = new MsgDetailFragment.GetData();
        task.execute("http://steak2121.ivyro.net/loadMessage.php");


        return v;
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




    private View showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String sender = item.getString(TAG_SENDER);
                String receiver = item.getString(TAG_RECEIVER);
                String content = item.getString(TAG_CONTENT);
                String date = item.getString(TAG_DATE);




                if (userName.equals(sender) && msg_you_name.equals(receiver)) // 보낸 사람과 본인의 ID가 같을경우 & 받는사람이 해당 메세지의 상대방일 경우
                {


                    adapter.addItem(new Msg_ListItem("보낸 메시지", content, date));

               }
                else if(userName.equals(receiver) && msg_you_name.equals(sender)){ // 받는사람과 본인의 ID가 같을경우 & 보낸사람이 해당 메세지의 상대방일 경우
                    adapter.addItem(new Msg_ListItem("받은 메시지", content, date));

                }

            }


            listView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

        return v;
    }



}
