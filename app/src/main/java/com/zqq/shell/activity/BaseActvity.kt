package com.zqq.shell.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.zqq.shell.MyApplication
import com.zqq.shell.R
import com.zqq.shell.http.HttpDialog
import com.zqq.shell.utils.KeyboardUtils
import com.zqq.shell.utils.StatusBarUtil
import com.zqq.shell.utils.ToastUtils
import es.dmoral.toasty.Toasty
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.json.JSONException
import org.json.JSONObject

open abstract class BaseActvity :AppCompatActivity() {

    var msg = "请求中"
    var unDismiss = false //是否隐藏请求对话框
    public lateinit var app:MyApplication
    lateinit var dialog: HttpDialog
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

        Toasty.Config.reset()
        Toasty.Config.getInstance()
            .tintIcon(true) // optional (apply textColor also to the icon)
            //                .setToastTypeface(@NonNull Typeface typeface) // optional
            .setTextSize(22) // optional
            .allowQueue(true) // optional (prevents several Toastys from queuing)
            .apply() // required


        dialog = HttpDialog(this, R.style.http_dialog) //请求对话框
        dialog.create()

        initView(savedInstanceState) //初始化view
        initData() //初始化数据
    }

    abstract fun initContentView():Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()


    open fun checkInternet(): Boolean {
        var result =false
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.run {
                getNetworkCapabilities(activeNetwork).run {
                    result  = hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                             hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                             hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                }
            }
        } else {
            connectivityManager?.run {
                activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }


    /**
     * 定义网络请求回调
     */
    private val compositeDisposable = CompositeDisposable()

    inner abstract class CallBackObserver<T> : Observer<T> {
        private var disposable: Disposable? = null
        override fun onSubscribe(d: Disposable) { //执行开始操作，比如显示请求对话框
            KeyboardUtils.hideKeyBoard(this@BaseActvity)
            val checkInternet: Boolean = checkInternet()
            if (!checkInternet) {
                ToastUtils.showMessage(this@BaseActvity, "您的设备未联网,请检查！")
                d.dispose() //无网络时时解除订阅
                return
            }
            if (dialog != null) {
                dialog.show()
                dialog.setMessage(msg)
                if (unDismiss) {
                    dialog.setCancelable(false)
                    dialog.setCanceledOnTouchOutside(false)
                }
            }
            disposable = d
            compositeDisposable.add(d)
            onStartNet(d)
        }

        override fun onNext(o: T) { //执行结果分类操作，比如错误提示
            try {
                val jsonObject = JSONObject(o.toString())
                Log.i("zqq", "返回数据>$jsonObject")
                val aBoolean = jsonObject.getBoolean("result")
                if (aBoolean) {
                    onSuccess(jsonObject.toString() as T)
                } else {
                    onFail(jsonObject.getString("strMsg"))
                    ToastUtils.showMessage(this@BaseActvity, jsonObject.getString("strMsg"))
                    if ("登录超时" == jsonObject.getString("strMsg")) {
                        app.removeAllActivity()
                        startActivity(Intent(this@BaseActvity, LoginActivity::class.java))
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                onFail("请求超时，请重试！")
                ToastUtils.showMessage(this@BaseActvity, "请求超时，请重试！")
            }
        }

        override fun onError(e: Throwable) { //网络请求错误提示
            Log.i("zqq", "网络请求》》》onError>>$e")
            if (e.toString().contains("SocketTimeoutException")) {
                onFail("请求超时，请重试！")
                ToastUtils.showMessage(this@BaseActvity, "请求超时，请重试！")
            } else {
                ToastUtils.showMessage(this@BaseActvity, "请求失败，请重试！")
            }
            onFail("错误！")
            if (dialog != null) {
                dialog.dismiss()
            }
            disposable!!.dispose() //错误时时解除订阅
        }

        override fun onComplete() {
            if (dialog != null) {
                dialog.dismiss()
            }
            disposable!!.dispose() //完成时解除订阅
        }

        abstract fun onSuccess(success: T)
        abstract fun onStartNet(d: Disposable?)
        abstract fun onFail(fail: String?)
    }


    override fun onDestroy() {
        super.onDestroy()
        app.removeActivity(this)
        compositeDisposable.clear() //界面销毁时，必须解除所有订阅
    }

    /**
     * 设置请求时对话框提示语
     */
    open fun setDialogMsg(msg: String?) {
        this.msg = msg!!
    }

    /**
     * 设置对话框不可关闭
     */
    open fun unDismiss(unDismiss: Boolean?) {
        this.unDismiss = unDismiss!!
    }
}