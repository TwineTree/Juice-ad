package com.twinetree.juice.network;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.twinetree.juice.MyApplication;
import com.twinetree.juice.datasets.User;
import com.twinetree.juice.ui.activity.MainActivity;
import com.twinetree.juice.util.AuthUtil;

import org.json.JSONObject;

import java.util.Date;

public class BasicRequest {

    public static void execute(final Context context) {
        User user = new User(context);
        String url = "http://joos.azurewebsites.net/api/Account/ExternalLogin?provider=Google&accessToken="
                + User.getAccessToken() + "&id=" + user.getGoogleId();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            AuthUtil auth = new AuthUtil(context);

                            String accessToken = object.getJSONObject("result").getString("myAccessToken");
                            User.setSessionToken(accessToken);
                            auth.setTokenTime(new Date().toString());
                            context.startActivity(new Intent(context, MainActivity.class));

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MyApplication.queue.add(request);
    }
}
