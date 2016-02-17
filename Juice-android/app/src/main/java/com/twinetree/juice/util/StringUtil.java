package com.twinetree.juice.util;

public class StringUtil {

    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }
}
