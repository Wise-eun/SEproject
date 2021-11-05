package com.example.seproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AlarmRequest  extends StringRequest {
    // 서버 URL설정 (PHP 파일 연동)


    final static private  String URL = "http://steak2121.ivyro.net/alarmSign.php";
    private Map<String,String> map;

    public AlarmRequest(int type, int pid, String sender, String receiver, int kinds, Response.Listener<String> listener){
        super(Method.POST,URL,listener, null);

        //kinds : 0은 수락, 1은 거절, 2는 모두 삭제
        map = new HashMap<>();
        map.put("type",type+"");
        map.put("pid",pid+"");
        map.put("sender",sender);
        map.put("receiver",receiver);
        map.put("kinds", kinds+"");

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
