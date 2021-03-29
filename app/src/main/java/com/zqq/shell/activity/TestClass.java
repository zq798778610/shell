package com.zqq.shell.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zqq.shell.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class TestClass extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.et_username, R.id.et_password})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.et_username:
                break;
            case R.id.et_password:
                break;
        }
    }
}
