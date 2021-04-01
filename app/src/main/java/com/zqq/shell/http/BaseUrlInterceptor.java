package com.zqq.shell.http;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseUrlInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        // 获取request
        Request request = chain.request();
        // 从request中获取原有的HttpUrl实例oldHttpUrl
        HttpUrl oldHttpUrl = request.url();
        // 获取request的创建者builder
        Request.Builder builder = request.newBuilder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")  ;
        // 从request中获取headers，通过给定的键url_name
//        List<String> headerValues = request.headers("url_name");
//        if (headerValues != null && headerValues.size() > 0)
//        {
//            // 如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
//            builder.removeHeader("url_name");
//            // 匹配获得新的BaseUrl
//            String headerValue = headerValues.get(0);
//            HttpUrl newBaseUrl = null;
//            if ("URL1".equals(headerValue))
//            {
//                newBaseUrl = HttpUrl.parse(HttpCreate.BASE_URL);
//            }
//            else if ("URL2".equals(headerValue))
//            {
//                newBaseUrl = HttpUrl.parse(HttpCreate.BASE_URL1);
//            }
//            else
//            {
//                newBaseUrl = oldHttpUrl;
//            }
//            LogUtils.i("newBaseUrl>>"+newBaseUrl);
//            // 重建新的HttpUrl，修改需要修改的url部分
//            HttpUrl newFullUrl = oldHttpUrl
//                    .newBuilder()
//                    // 更换网络协议
//                    .scheme(newBaseUrl.scheme())
//                    // 更换主机名
//                    .host(newBaseUrl.host())
//                    // 更换端口
//                    .port(newBaseUrl.port())
//                    .build();
//            // 重建这个request，通过builder.url(newFullUrl).build()；
//            // 然后返回一个response至此结束修改
//            return chain.proceed(builder.url(newFullUrl).build());
//        }
        return chain.proceed(request);
    }
}
