package com.twinetree.juice.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.twinetree.juice.api.Url;
import com.twinetree.juice.datasets.User;

import java.util.HashMap;
import java.util.Map;

public class BearerRequest extends StringRequest {

    public BearerRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        String auth = "Bearer " + User.getSessionToken();
        headers.put("Authorization", auth);
        return headers;
    }
}
