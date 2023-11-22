package com.yong.freshroute.util

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object KakaoLocalClient {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()

    private val gsonBuilder = GsonBuilder().setLenient().create()

    private val retrofitClient = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com/")
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
        .client(okHttpClient)
        .build()

    val kakaoLocalService: KakaoLocalAPI by lazy {
        retrofitClient.create(KakaoLocalAPI::class.java)
    }
}

object RouteApiClient {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()

    private val gsonBuilder = GsonBuilder().setLenient().create()

    private val retrofitClient = Retrofit.Builder()
        .baseUrl("https://freshroute.dev-lr.com/")
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
        .client(okHttpClient)
        .build()

    val RouteApiService: RouteApi by lazy {
        retrofitClient.create(RouteApi::class.java)
    }
}