package com.example.workoutaiassistant.data.network

import com.example.workoutaiassistant.data.network.interceptor.ApiInterceptor
import com.example.workoutaiassistant.data.network.interceptor.RetryInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.openai.com/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ApiInterceptor())
                    .addInterceptor(RetryInterceptor(3, 1000))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}