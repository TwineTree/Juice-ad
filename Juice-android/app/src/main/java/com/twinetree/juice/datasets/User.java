package com.twinetree.juice.datasets;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    private final String BASE = "USER-";
    private final String USER_TAG = "USER";
    private final String USER_GOOGLE_ACCOUNT_NAME_TAG = BASE + "GOOGLE-ACCOUNT-NAME";
    private final String AVATAR_URL_TAG = BASE + "AVATAR-URL";
    private final String EMAIL_TAG = BASE + "EMAIL";

    private static String accessToken;

    private SharedPreferences preferences;

    public User(Context context) {
        preferences = context.getSharedPreferences(USER_TAG, Context.MODE_PRIVATE);
    }

    //  GETTERS
    public static String getAccessToken() {
        return accessToken;
    }

    public String getEmail() {
        return preferences.getString(EMAIL_TAG, "");
    }

    public String getAvatarUrl() {
        return preferences.getString(AVATAR_URL_TAG, "");
    }

    public String getAccountName() {
        return preferences.getString(USER_GOOGLE_ACCOUNT_NAME_TAG, "");
    }

    //  SETTERS
    public static void setAccessToken(String mAccessToken) {
        accessToken = mAccessToken;
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL_TAG, email);
        editor.apply();
    }

    public void setAvatarUrl(String avatarUrl) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AVATAR_URL_TAG, avatarUrl);
        editor.apply();
    }

    public void setAccountName(String accountName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_GOOGLE_ACCOUNT_NAME_TAG, accountName);
        editor.apply();
    }

}
