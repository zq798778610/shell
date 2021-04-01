package com.zqq.shell.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class KeyboardUtils {

    /**
     * 隐藏软键盘
     * 如当前为收起变为弹出,若当前为弹出变为收起
     * @param context
     */
    public static void hideKeyBoard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        if(isOpen) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 强制隐藏软键盘
     */
    public static void hideKeyBoardBreak(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     * @param context
     * @param view 哪个控件要弹出键盘
     */
    public static void showKeyBoard(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        if(!isOpen) {
            imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        }
    }

    public static Boolean softInputShowed(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        return isOpen;
    }

    public static void showSoftInput( final View view){
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run()
            {
                InputMethodManager m = (InputMethodManager)
                        view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.showSoftInput(view,InputMethodManager.SHOW_FORCED);

            }
        }, 300);
    }

}
