package com.zqq.shell.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open abstract class BaseActvity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = initContentView() //设置contentView
        setContentView(contentView)
        initView(savedInstanceState) //初始化view
        initData() //初始化数据
    }

    abstract fun initContentView():Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()
}