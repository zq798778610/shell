package com.zqq.shell.api.imp

import com.zqq.shell.api.LoginApi
import com.zqq.shell.http.HttpCreator
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Body
import retrofit2.http.POST

public class LoginApiImp() :LoginApi{

    val loginService: LoginService = HttpCreator.getInstance().create(LoginService::class.java)


    override fun login(info: String, observer: Observer<String>) {
        val requestBody =info.toRequestBody("application/json;charset=utf-8".toMediaType())

        val login = loginService.login(requestBody)
        login!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    }

    interface LoginService {
        /**
         * 登录接口
         * @return
         */
        @POST("/")
        fun login(@Body body: RequestBody?): Observable<String>?
    }

}