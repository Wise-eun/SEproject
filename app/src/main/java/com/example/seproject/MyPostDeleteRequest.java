package com.example.seproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MyPostDeleteRequest  extends StringRequest {
    // 서버 URL설정 (PHP 파일 연동)
    final static private String URL = "http://steak2121.ivyro.net/postDelete.php";
    private Map<String, String> map;

    public MyPostDeleteRequest(int pid, String writer , Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("pid", pid + "");
        map.put("writer", writer);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}