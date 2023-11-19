package com.example.workoutaiassistant.data.network

import com.example.workoutaiassistant.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = BuildConfig.API_KEY
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .build()
        return chain.proceed(newRequest)
    }
}