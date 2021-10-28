package com.example.seproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostWriteRequest extends StringRequest {
    // 서버 URL설정 (PHP 파일 연동)
    final static private  String URL = "http://steak2121.ivyro.net/postWrite.php";
    private Map<String,String> map;

    public PostWriteRequest(int pid, String writer, String title,String deadline, int recruitment, String area, String content, String category,Response.Listener<String> listener){
        super(Method.POST,URL,listener, null);

        map = new HashMap<>();
        map.put("pid",pid+"");
        map.put("writer",writer);
        map.put("title",title);
        map.put("deadline",deadline);
        map.put("recruitment",recruitment+"");
        map.put("area",area);
        map.put("content",content);
        map.put("category",category);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}