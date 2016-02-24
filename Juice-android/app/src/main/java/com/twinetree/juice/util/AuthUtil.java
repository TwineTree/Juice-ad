package com.twinetree.juice.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthUtil {

    private static final String BASE = "AUTH_";
    private static final String AUTH_TIME_TAG = BASE + "AUTH-TIME";

    private static SharedPreferences preferences;

    public AuthUtil(Context context) {
        preferences = context.getSharedPreferences(BASE, context.MODE_PRIVATE);
    }

    public static String getTokenTime() {
        return preferences.getString(AUTH_TIME_TAG, "");
    }

    public static void setTokenTime(String time) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AUTH_TIME_TAG, time);
        editor.apply();
    }
}
