package com.twinetree.juice.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DimensionUtil {

    private Context context;
    private DisplayMetrics displayMetrics;

    public DimensionUtil(Context context) {
        this.context = context;
        displayMetrics = context.getResources().getDisplayMetrics();
    }

    //  in dp
    public float getScreenWidth() {
        return displayMetrics.widthPixels / displayMetrics.density;
    }

    //  in dp
    public float getScreenHeight() {
        return displayMetrics.heightPixels / displayMetrics.density;
    }

    public float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                displayMetrics);
    }
}
