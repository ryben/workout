package com.example.workoutaiassistant.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("v1/chat/completions")
    fun getResponse(@Body aiRequest: AiRequest): Call<AiResponse>
}


