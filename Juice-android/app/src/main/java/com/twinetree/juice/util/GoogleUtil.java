package com.twinetree.juice.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.twinetree.juice.R;
import com.twinetree.juice.ui.activity.MainActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutionException;

public class GoogleUtil {

    private static String authToken;
    private static Context context;

    private final String TAG = "fkbqef";

    public String getAuthToken(Context c) {
        context = c;
        GoogleApiClient apiClient = getApiClient(context);
        try {
            new GoogleAuthTokenTask().execute(Plus.AccountApi.getAccountName(apiClient)).get();
            return authToken;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static GoogleApiClient getApiClient(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getResources().getString(R.string.google_api_client), Context.MODE_PRIVATE);
        String fileName = preferences.getString(context.getResources().getString(R.string.google_api_client_file), "");

        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            GoogleApiClient apiClient = (GoogleApiClient) is.readObject();
            is.close();
            fis.close();
            return apiClient;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private class GoogleAuthTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(context, accountName, scopes);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (UserRecoverableAuthException e) {
                Log.e(TAG, e.getMessage());
            } catch (GoogleAuthException e) {
                Log.e(TAG, e.getMessage());
            }
            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            authToken = s;
            Log.i(TAG, s);
        }
    }
}
