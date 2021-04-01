package com.zqq.shell.api

import io.reactivex.Observer
import okhttp3.RequestBody

interface LoginApi {
    /**
     * 登录
     */
    fun login(info: String, observer: Observer<String>)
}