package com.zqq.shell.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DpUtils {

    public static int getScreenWidth(Activity activity){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Activity activity){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /** * @return 屏幕像素宽度，px类型 */
    public static int getScreenWidth(Context context) {
        return context.getResources()
                .getDisplayMetrics().widthPixels;
    }

    /** * @return 屏幕像素高度，px类型 */
    public static int getScreenHeight(Context context) {
        return context.getResources()
                .getDisplayMetrics().heightPixels;
    }


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
