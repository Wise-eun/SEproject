package com.example.seproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {




    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Bundle bundle_toFragment = new Bundle();
    public static String userID;
    public static String userName;
    private static String TAG = "phptest_LoadActivity";
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_NAME = "userName";
    private static final String TAG_ID = "userID";
    private String mJsonString;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    WorkFragment workFragment = new WorkFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    SendMsgFragment sendMsgFragment = new SendMsgFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.flFragment, homeFragment.newInstance()).commit();

        Bundle bundle =   getIntent().getExtras();
        userID = bundle.getString("userID");
        bundle_toFragment.putString("userID", userID);

        String from = getIntent().getStringExtra("from");

        if(from != null){
            if(from.equals("profile")){
                replaceFragment(sendMsgFragment);

            }
        }
        else{
            replaceFragment(homeFragment);
        }


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new ItemSelectedListener());


        MainActivity.GetData task = new MainActivity.GetData();

        task.execute("http://steak2121.ivyro.net/loadUser.php");

    }


    class ItemSelectedListener implements NavigationBarView.OnItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            int id = item.getItemId();

            if (id == R.id.menu_home) {
                transaction.replace(R.id.flFragment, homeFragment).commitAllowingStateLoss();
                return true;
            } else if (id == R.id.menu_work) {
                transaction.replace(R.id.flFragment, workFragment).commitAllowingStateLoss();
                return true;
            } else if (id == R.id.menu_profile) {
                profileFragment.setArguments(bundle_toFragment);
                transaction.replace(R.id.flFragment, profileFragment).commitAllowingStateLoss();

                return true;
            }
            return false;
        }
    }

    public void replaceFragment(Fragment fragment)
    {
        fragment.setArguments(bundle_toFragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment).addToBackStack(null).commit();
    }

    public void bundlePutString(String key, String val){
        bundle_toFragment.putString(key, val);
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


                if (id.equals(userID)) // 현재 사용자 ID와 서버에있는 정보중 ID가 같은것들의 정보만 가져옴
                {


                    userName = String.copyValueOf(name.toCharArray());//사용자 닉네임 복사


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






