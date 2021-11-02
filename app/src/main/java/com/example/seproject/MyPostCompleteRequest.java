package com.example.seproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MyPostCompleteRequest extends StringRequest {
    // 서버 URL설정 (PHP 파일 연동)
    final static private String URL = "http://steak2121.ivyro.net/postComplete.php";
    private Map<String, String> map;

    public MyPostCompleteRequest(int type, int pid,String userName, String writer , Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("type", type + "");
        map.put("pid", pid + "");
        map.put("userName", userName);
        map.put("writer", writer);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}