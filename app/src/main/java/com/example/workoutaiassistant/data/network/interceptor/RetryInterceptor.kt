package com.example.workoutaiassistant.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.IOException

class RetryInterceptor(private val maxRetries: Int, private val retryDelayMillis: Long) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        var retryCount = 0

        do {
            try {
                return chain.proceed(request)
            } catch (e: IOException) {
                if (retryCount++ < maxRetries) {
                    println("Retrying request ${request.url} ($retryCount/$maxRetries)")
                    Thread.sleep(retryDelayMillis)
                    request = request.newBuilder().build()
                } else {
                    throw e
                }
            }
        } while (true)
    }
}
