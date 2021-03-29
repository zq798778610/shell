package com.zqq.shell

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.os.Looper
import android.os.Process
import android.widget.Toast
import com.zqq.shell.activity.SplashActvity
import java.io.File
import java.io.FileWriter

class MyApplication : Application(),Thread.UncaughtExceptionHandler {

    private val activitys = ArrayList<Activity>()

    companion object{
        private var instance:MyApplication? = null
        fun instance() = instance!!

        //2、声明延迟初始化属性
//        private lateinit var instance:MyApplication
//        fun instance() = instance
    }

//    companion object{
//        private var instance:MyApplication by Delegates.notNull()
//        fun instance() = instance
//    }

    override fun onCreate() {
        super.onCreate()
        instance=this
        Thread.setDefaultUncaughtExceptionHandler(this)
    }


    fun addActivity(activity: Activity){
        activitys.add(activity)
    }

    fun removeActivity(activity: Activity){
        activitys.remove(activity)
    }

    fun removeAllActivity(){
        activitys.map {
            it.finish()
        }
    }

    fun getCurrentActivity():Activity = activitys.get(activitys.size - 1)

    /**
     * 全局未知异常处理
     */
    override fun uncaughtException(t: Thread, e: Throwable) {
        val b:Boolean  = handException(e)
    }

    private fun handException(e: Throwable): Boolean {
        if(e == null) return false
        //写入错误日志
        Thread{
            var out: FileWriter? = null

            try {
                Looper.prepare()
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG)
                    .show()
                val file = File(getExternalFilesDir(null), "error.txt")
                if(!file.exists()){
                    val createNewFile = file.createNewFile()
                }
                out = FileWriter(file, true)
                out.write("\r\n") //换行
                out.write(e.toString())
            }catch (e: Exception){

            }finally {
                if(out!=null){
                    out.close()
                }
            }
            try {
                Thread.sleep(5000)
                removeAllActivity()
                restartApp()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            Looper.loop()
        }.start()

        return true
    }

    /**
     * 重启app
     */
    private fun restartApp() {
        val intent = Intent(this, SplashActvity::class.java)
        @SuppressLint("WrongConstant") val restartIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK
        )
        //退出程序
        val mgr = getSystemService(ALARM_SERVICE) as AlarmManager
        mgr[AlarmManager.RTC, System.currentTimeMillis() + 1000] = restartIntent // 1秒钟后重启应用

        //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
        Process.killProcess(Process.myPid())
    }

}