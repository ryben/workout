package com.example.workoutaiassistant.data.network

import com.example.workoutaiassistant.data.model.OpenAIResponse
import com.example.workoutaiassistant.data.model.Prompt
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("v1/engines/text-davinci-003/completions")
    fun getResponse(@Body prompt: Prompt): Call<OpenAIResponse>
}

