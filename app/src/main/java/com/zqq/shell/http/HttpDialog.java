package com.zqq.shell.http;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zqq.shell.R;


public class HttpDialog extends Dialog {
    public HttpDialog(@NonNull Context context) {
        this(context,0);
    }

    public HttpDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_http_request);

        initView();
        initData();
    }
    TextView tv;
    LinearLayout ll;
    private void initView() {
        ll = findViewById(R.id.ll);
        tv = findViewById(R.id.tv);
    }

    private void initData() {

    }

    public void setMessage(String msg){
        tv.setText(msg+"...");
    }
}
