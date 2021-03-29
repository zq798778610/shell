package com.zqq.shell.activity

import android.os.Bundle
import android.widget.EditText
import butterknife.BindView
import com.zqq.shell.R
import com.zqq.shell.view.EditTextLayout

class LoginActivity :BaseActvity() {

    @BindView(R.id.el_username)
    lateinit var el_username: EditTextLayout

    @BindView(R.id.el_password)
    lateinit var el_password: EditTextLayout


    override fun initContentView(): Int = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }
}