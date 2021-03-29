package com.zqq.shell.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.annotation.ColorRes;

import es.dmoral.toasty.Toasty;

public class ToastUtils {

    private static Toast toast;

    public static void showMessage(Context context, String msg){

        toast = Toasty.normal(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }


    public static void showMessage(Context context, String msg, Drawable icon){
        toast = Toasty.normal(context, msg, Toast.LENGTH_SHORT,icon);
        toast.show();
    }

    public static void showMessageSuccess(Context context, String msg){

        toast = Toasty.success(context, msg, Toast.LENGTH_SHORT, true);
        toast.show();
    }

    public static void showMessageError(Context context, String msg){
        toast = Toasty.error(context, msg, Toast.LENGTH_SHORT, true);
        toast.show();
    }

    /**
     * 创建自定义Toasts ：
     */
    private static void showCustom(Context context, String msg, Drawable icon,  int tintColorRes, int textColorRes, int duration,
                                   boolean withIcon, boolean shouldTint) {
        Toasty.custom(context,msg,
                icon, tintColorRes,textColorRes,Toast.LENGTH_SHORT,true,true).show();
    }

}