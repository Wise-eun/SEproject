package com.example.seproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostSignRequest extends StringRequest {
    // 서버 URL설정 (PHP 파일 연동)
    final static private  String URL = "http://steak2121.ivyro.net/postSign.php";
    private Map<String,String> map;

    public PostSignRequest(int what, int pid, String userName, String writer, String title, Response.Listener<String> listener){
        super(Method.POST,URL,listener, null);

        map = new HashMap<>();
        map.put("what",what+"");
        map.put("pid",pid+"");
        map.put("userName",userName);
        map.put("writer",writer);
        map.put("title",title);

    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
