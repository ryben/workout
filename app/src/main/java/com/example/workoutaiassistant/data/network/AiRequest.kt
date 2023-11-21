package com.example.workoutaiassistant.data.network

import com.google.gson.annotations.SerializedName

data class AiRequest(val model: String, val messages: List<Chat>)
data class Chat(val role: Role, val content: String)

enum class Role {
    @SerializedName("user")
    USER,

    @SerializedName("system")
    SYSTEM,

    @SerializedName("assistant")
    ASSISTANT
}
