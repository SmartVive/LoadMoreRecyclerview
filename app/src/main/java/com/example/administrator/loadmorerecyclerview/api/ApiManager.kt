package com.example.administrator.loadmorerecyclerview.api

import android.app.Application
import com.example.administrator.loadmorerecyclerview.MyApplication
import com.example.administrator.loadmorerecyclerview.util.NetWorkUtil
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class ApiManager private constructor() {
    private var api: Api? = null

    companion object {
        @Volatile
        private var apiManager: ApiManager? = null
        private val REWRITE_CACHE_CONTROL_INTERCEPTOR = Interceptor {
            val originalResponse = it.proceed(it.request())
            if (NetWorkUtil.isNetWorkAvailable(MyApplication.getContext())) {
                val maxAge = 60 // 在线缓存在1分钟内可读取
                originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28 // 离线时缓存保存4周
                originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
            }
        }
        private val httpCacheDirectory = File(MyApplication.getContext().cacheDir, "cache")
        private const val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
        private val cache = Cache(httpCacheDirectory, cacheSize)
        private val client: OkHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build();

        fun getInstance(): ApiManager {
            if (apiManager == null) {
                synchronized(ApiManager::class.java) {
                    if (apiManager == null) {
                        apiManager = ApiManager()
                    }
                }
            }
            return apiManager!!
        }


    }

    fun getTopServer(): Api {
        if (api == null) {
            api = Retrofit.Builder()
                    .baseUrl("http://api.douban.com/v2/movie/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
                    .create(Api::class.java)
        }
        return api!!
    }
}