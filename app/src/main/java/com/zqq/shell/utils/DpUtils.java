package com.zqq.shell.utils;

import android.content.Context;
import android.util.TypedValue;

public class DpUtils {

//    public static int dp2px(Context context,float dp){
//        float density = context.getResources().getDisplayMetrics().density;
//        return (int) (dp/density+0.5f);
//    }
//
    public static float px2dp(Context context, float px){
        float density = context.getResources().getDisplayMetrics().density;
        return px/density+0.5f;
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }
}
