package com.twinetree.juice.datasets;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    private final String BASE = "USER-";
    private final String USER_TAG = "USER";
    private final String USER_GOOGLE_ACCOUNT_NAME_TAG = BASE + "GOOGLE-ACCOUNT-NAME";

    private SharedPreferences preferences;

    public User(Context context) {
        preferences = context.getSharedPreferences(USER_TAG, Context.MODE_PRIVATE);
    }

    public String getAccountName() {
        return preferences.getString(USER_GOOGLE_ACCOUNT_NAME_TAG, "");
    }

    public void setAccountName(String accountName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_GOOGLE_ACCOUNT_NAME_TAG, accountName);
        editor.apply();
    }
}
