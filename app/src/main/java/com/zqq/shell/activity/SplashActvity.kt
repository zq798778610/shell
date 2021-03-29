package com.zqq.shell.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.zqq.shell.R

class SplashActvity :BaseActvity() {
    override fun initContentView(): Int = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
        val currentActivity = app.getCurrentActivity()
        Log.i("zqqqq",currentActivity.localClassName)

       startActivity(Intent(this,MainActivity::class.java))
    }

    override fun initData() {

    }

}