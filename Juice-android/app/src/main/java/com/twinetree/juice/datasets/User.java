package com.twinetree.juice.datasets;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    private final String BASE = "USER-";
    private final String USER_TAG = "USER";
    private final String USER_GOOGLE_ACCOUNT_NAME_TAG = BASE + "GOOGLE-ACCOUNT-NAME";
    private final String AVATAR_URL_TAG = BASE + "AVATAR-URL";
    private final String EMAIL_TAG = BASE + "EMAIL";
    private final String ID_TAG = BASE + "ID";
    private final String IS_USER_LOGIN_TAG = BASE + "IS-USER-LOGIN";

    private static String accessToken;
    private static String sessionToken;

    private SharedPreferences preferences;

    public User(Context context) {
        preferences = context.getSharedPreferences(USER_TAG, Context.MODE_PRIVATE);
    }

    //  GETTERS
    public boolean isUserLogin() {
        return preferences.getBoolean(IS_USER_LOGIN_TAG, false);
    }

    public static String getSessionToken() {
        return sessionToken;
    }

    public String getId() {
        return preferences.getString(ID_TAG, "");
    }

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
    public void setUserLogin(Boolean isLoggedIn) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_USER_LOGIN_TAG, isLoggedIn);
        editor.apply();
    }

    public static void setSessionToken(String mSessionToken) {
        sessionToken = mSessionToken;
    }

    public void setId(String id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID_TAG, id);
        editor.apply();
    }

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
