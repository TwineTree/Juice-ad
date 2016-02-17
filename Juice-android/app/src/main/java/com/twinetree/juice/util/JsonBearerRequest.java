package com.twinetree.juice.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.twinetree.juice.api.Url;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonBearerRequest extends JsonObjectRequest {
    public JsonBearerRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        String auth = "Bearer " + Url.accessToken;
        headers.put("Authorization", auth);
        return headers;
    }
}
