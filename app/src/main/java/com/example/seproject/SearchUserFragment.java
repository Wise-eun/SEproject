package com.example.seproject;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class SearchUserFragment extends Fragment {

    public static SearchUserFragment newInstance() {
        return new SearchUserFragment();
    }
    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "userID";
    private static final String TAG_NAME = "userName";
    private String mJsonString;
    EditText search_et;
    ImageButton search_btn;
    public static String search_name;
    public static String search_id;
    TextView user_name_tv,no_user_tv;
    ViewGroup layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_user, container, false);

        search_et = (EditText)v.findViewById(R.id.search_et);
        search_btn = (ImageButton) v.findViewById(R.id.search_btn);
        user_name_tv = (TextView) v.findViewById(R.id.user_name_tv);
        no_user_tv = (TextView) v.findViewById(R.id.no_user_tv);
        //검색한 사용자를 클릭할수있는 버튼 공간(리니어레이아웃)
       layout = (ViewGroup) v.findViewById(R.id.linearLayout1);

        layout.setVisibility(View.INVISIBLE);
        no_user_tv.setVisibility(View.INVISIBLE);
        layout.setOnClickListener(new View.OnClickListener() //검색된 사용자를 클릭했을때
        { @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
//메세지 전송으로 화면이동
            //MsgDetailFragment.msg_you_name = null; //다른경로로 write할 경우를 사전 차단.

            ((MainActivity)getActivity()).replaceFragment(SendMsgFragment.newInstance()); //화면전환
            SendMsgFragment.where_in = 2;
        } });


//


        search_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //load data
                SearchUserFragment.GetData task = new SearchUserFragment.GetData();
                task.execute("http://steak2121.ivyro.net/loadUser.php");


            }
        });



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

            String serverURL = "http://steak2121.ivyro.net/loadUser.php";


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

                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);

search_name = search_et.getText().toString();
                if (name.equals(search_name)) // 검색하고자 하는 사용자의 이름이 서버에 있는지 확인
                {


                    no_user_tv.setVisibility(View.INVISIBLE);
                    search_name = String.copyValueOf(name.toCharArray());//메세지 보낼 사용자 닉네임 복사
                    search_id = String.copyValueOf(id.toCharArray());//메세지 보낼 사용자 아이디 복사
                    user_name_tv.setText(search_name);

                    layout.setVisibility(View.VISIBLE);
break;
                }
                else{
                    //검색한 사용자가 없습니다. setText하기.
                    layout.setVisibility(View.INVISIBLE);
                    no_user_tv.setVisibility(View.VISIBLE);
                }
                /*
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_NUM, num);
*/

            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }



}

