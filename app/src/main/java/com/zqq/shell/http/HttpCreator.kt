package com.zqq.shell.http

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

public class HttpCreator {
    companion object{
        val BASE_URL = "http://www.baidu.com"  //基础路径
        private const val timeOut:Long = 60 //30秒超时
        var baseUrlInterceptor: BaseUrlInterceptor = BaseUrlInterceptor()
        var okhttpClient: OkHttpClient

        var loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                //打印retrofit日志
                Log.i("RetrofitLog", "retrofitBack = $message")
            }
        })

        init {
            loggingInterceptor.level =HttpLoggingInterceptor.Level.BODY
            var builder:OkHttpClient.Builder =OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .addInterceptor(baseUrlInterceptor)
                .addInterceptor(loggingInterceptor)
            okhttpClient=builder.build();
        }

        @Volatile
        var retrofit: Retrofit? = null

        public fun getInstance():Retrofit{
            if (retrofit == null) {
                synchronized(this) {
                    if (retrofit == null) {
//                  Gson gson = new GsonBuilder().setLenient().create();
                    retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
//                           .addConverterFactory(GsonConverterFactory.create()) //配置转化库-->
//                           .addConverterFactory(GsonConverterFactory.create(gson)) //配置转化库 Gson解析失败，不报错崩溃
                            .addConverterFactory(ScalarsConverterFactory.create()) //配置转化库 Gson解析失败，不报错崩溃
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //配置回调库，采用RxJava
                            .client(okhttpClient)
                            .build()
                    }
                }
            }

            return retrofit!!
        }
    }
}