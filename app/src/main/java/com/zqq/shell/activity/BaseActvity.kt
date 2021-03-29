package com.zqq.shell.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.zqq.shell.MyApplication
import com.zqq.shell.R
import com.zqq.shell.utils.StatusBarUtil

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

        ButterKnife.bind(this)  //butterknife

        //依赖 implementation 'com.readystatesoftware.systembartint:systembartint:1.0.3' //状态栏颜色改变
        StatusBarUtil.setStatusBarColor(this, R.color.main_color) //设置状态栏颜色

        app = MyApplication.instance()
        app.addActivity(this)

        initView(savedInstanceState) //初始化view
        initData() //初始化数据
    }

    abstract fun initContentView():Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        app.removeActivity(this)
    }


}