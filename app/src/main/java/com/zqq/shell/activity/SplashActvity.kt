package com.zqq.shell.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.zqq.shell.R

class SplashActvity :BaseActvity() {
    override fun initContentView(): Int = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    override fun initData() {

    }

}