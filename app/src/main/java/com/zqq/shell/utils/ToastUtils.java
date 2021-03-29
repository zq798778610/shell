package com.zqq.shell.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ToastUtils {

    private static Toast toast;

    public static void showMessage(Context context, String msg){

        toast = Toasty.normal(context, msg, Toast.LENGTH_SHORT);
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
}