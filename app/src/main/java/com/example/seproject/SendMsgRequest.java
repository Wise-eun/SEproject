package com.example.seproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SendMsgRequest extends StringRequest {
    // 서버 URL설정 (PHP 파일 연동)
    final static private  String URL = "http://steak2121.ivyro.net/msgSend.php";
    private Map<String,String> map;

    public SendMsgRequest(String sender, String receiver,String content, Response.Listener<String> listener){
        super(Method.POST,URL,listener, null);

        map = new HashMap<>();
        map.put("sender",sender);
        map.put("receiver",receiver);
        map.put("content",content);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
