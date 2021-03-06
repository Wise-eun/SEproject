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

    public static String msg_you_name;

    Msg_ListItemAdapter adapter;
    String userName;
    ListView listView;
    TextView msg_user_name_tv;
    ImageButton write_msg_btn;
    public static int where_in =0;
    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_SENDER = "sender";
    private static final String TAG_RECEIVER = "receiver";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_DATE = "date";
    private String mJsonString;
    View view;
    public static MsgDetailFragment newInstance() {
        return new MsgDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_detail, container, false);

        userName = MainActivity.userName;

        msg_user_name_tv = (TextView) view.findViewById(R.id.msg_user_name_tv);
        write_msg_btn = (ImageButton) view.findViewById(R.id.write_msg_btn);
        listView = (ListView) view.findViewById(R.id.msg_detail_listview);
        adapter = new Msg_ListItemAdapter();

        write_msg_btn.setOnClickListener(new View.OnClickListener() //
        { @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            //????????? ???????????? ????????????
            // SearchUserFragment.search_name = null; // write ???????????? ?????? ????????? ??????.
            SendMsgFragment.where_in = 1;
            ((MainActivity)getActivity()).replaceFragment(SendMsgFragment.newInstance()); //????????????

        } });

        if(where_in==2)//??????-???????????????- ?????? ???????????? ??????
        {msg_you_name = SendMsgFragment.receiver;
            msg_user_name_tv.setText(msg_you_name);}

        else  if(where_in==1)//msgList-???????????????- ?????? ???????????? ??????
        {msg_you_name = MsgListFragment.targetName;
            msg_user_name_tv.setText(msg_you_name);}

        MsgDetailFragment.GetData task = new MsgDetailFragment.GetData();
        task.execute("http://steak2121.ivyro.net/loadMessage.php");

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


                if (userName.equals(sender) && msg_you_name.equals(receiver)) // ?????? ????????? ????????? ID??? ???????????? & ??????????????? ?????? ???????????? ???????????? ??????
                {
                    adapter.addItem(new Msg_ListItem("?????? ?????????", content, date));
               }
                else if(userName.equals(receiver) && msg_you_name.equals(sender)){ // ??????????????? ????????? ID??? ???????????? & ??????????????? ?????? ???????????? ???????????? ??????
                    adapter.addItem(new Msg_ListItem("?????? ?????????", content, date));
                }
            }

            listView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

        return view;
    }



}
