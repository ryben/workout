package com.example.workoutaiassistant.data.network

data class GptChatModel(val model: String, val messages: List<Message>)
data class Message(val role: String, val content: String)

enum class Role(val label: String) {
    USER("Human"),
    SYSTEM("System")
}
