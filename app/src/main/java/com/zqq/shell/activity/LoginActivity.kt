package com.zqq.shell.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import butterknife.BindView
import com.zqq.shell.R
import com.zqq.shell.api.imp.LoginApiImp
import com.zqq.shell.view.EditTextLayout
import io.reactivex.disposables.Disposable
import org.json.JSONObject


class LoginActivity :BaseActvity() {

    val loginApi = LoginApiImp()

    @BindView(R.id.el_username)
    lateinit var el_username: EditTextLayout

    @BindView(R.id.el_password)
    lateinit var el_password: EditTextLayout

    @BindView(R.id.btn_login)
    lateinit var btn_login: Button


    val permissions = arrayOf(
        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE
    )

    override fun initContentView(): Int = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        val needPermissions = ArrayList<String>()

        for (index in permissions.indices){
            val checkSelfPermission = ContextCompat.checkSelfPermission(
                applicationContext,
                permissions[index]
            )
            Log.i("zqqq", "checkSelfPermission>>$checkSelfPermission")
            if(checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                needPermissions.add(permissions[index])
            }
        }

        if(needPermissions.size>0){
            ActivityCompat.requestPermissions(this, permissions, 10001)  //请求权限
        }

        btn_login.setOnClickListener {
            login();
        }
    }

    private fun login() {

        val jsonDevice = JSONObject()
        jsonDevice.put("username", el_username.text)
        jsonDevice.put("password", el_password.text)

        loginApi.login(jsonDevice.toString(),object:CallBackObserver<String>(){
            override fun onSuccess(success: String) {
                Log.i("zqqq",success);
            }

            override fun onStartNet(d: Disposable?) {

            }

            override fun onFail(fail: String?) {

            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data == null) return
        if(requestCode == 10001){

        }
    }
}