package com.zqq.shell.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.zqq.shell.MyApplication

open abstract class BaseActvity :AppCompatActivity() {

    public lateinit var app:MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //设备不休眠
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        val contentView = initContentView() //设置contentView
        setContentView(contentView)

        app = MyApplication.instance()
        app.addActivity(this)

        initView(savedInstanceState) //初始化view
        initData() //初始化数据
    }

    abstract fun initContentView():Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()
}