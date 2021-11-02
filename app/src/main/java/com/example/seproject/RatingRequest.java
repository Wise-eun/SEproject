package com.example.seproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RatingRequest extends StringRequest {
    final static private  String URL = "http://steak2121.ivyro.net/rating.php";
    private Map<String,String> map;

    public RatingRequest(String userName, Float rating, Response.Listener<String> listener){
        super(Method.POST,URL,listener, null);

        map = new HashMap<>();
        map.put("userName",userName);
        map.put("rating",rating+"");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}