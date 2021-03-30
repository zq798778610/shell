package com.zqq.shell.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import butterknife.BindView
import com.zqq.shell.R
import com.zqq.shell.view.EditTextLayout


class LoginActivity :BaseActvity() {

    @BindView(R.id.el_username)
    lateinit var el_username: EditTextLayout

    @BindView(R.id.el_password)
    lateinit var el_password: EditTextLayout


    val permissions = arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE)

    override fun initContentView(): Int = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        val needPermissions = ArrayList<String>()

        for (index in permissions.indices){
            val checkSelfPermission = ContextCompat.checkSelfPermission(applicationContext, permissions[index])
            Log.i("zqqq","checkSelfPermission>>$checkSelfPermission")
            if(checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                needPermissions.add(permissions[index])
            }
        }

        if(needPermissions.size>0){
            ActivityCompat.requestPermissions(this, permissions, 10001)  //请求权限
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data == null) return
        if(requestCode == 10001){

        }
    }
}