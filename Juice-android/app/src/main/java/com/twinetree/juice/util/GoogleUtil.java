package com.twinetree.juice.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.Scopes;
import com.twinetree.juice.datasets.User;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class GoogleUtil extends Activity {

    private static final String TAG = "RetrieveAccessToken";
    private static final int REQ_SIGN_IN_REQUIRED = 55664;

    private Context context;
    private String accessToken;
    private User user;

    public GoogleUtil(Context context) {
        this.context = context;
        user = new User(context);
    }

    public void getAccessToken() {
        //try {
            //new RetrieveTokenTask().execute(user.getAccountName());
            /*return accessToken;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";*/


        new TestTask().execute("");
    }

    private class TestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accessToken = "";
            try {
                URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo");
                // get Access Token with Scopes.PLUS_PROFILE
                String sAccessToken;
                sAccessToken = GoogleAuthUtil.getToken(
                        context,
                        user.getAccountName() + "",
                        "oauth2:"
                                + Scopes.PLUS_LOGIN + " "
                                + "https://www.googleapis.com/auth/plus.profile.emails.read");
                accessToken = sAccessToken;
            } catch (UserRecoverableAuthException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Intent recover = e.getIntent();
                startActivityForResult(recover, 125);
                return "";
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (GoogleAuthException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return accessToken;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("qifbiqef", s);
        }
    }

    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(context, accountName, scopes);
                Log.i("fcq", "qwf");
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), REQ_SIGN_IN_REQUIRED);
            } catch (GoogleAuthException e) {
                Log.e(TAG, e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            accessToken = s;
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    }
}
