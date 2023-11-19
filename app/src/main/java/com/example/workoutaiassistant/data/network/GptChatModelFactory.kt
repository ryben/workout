package com.example.workoutaiassistant.data.network

import com.example.workoutaiassistant.util.Util


object GptChatModelFactory {
    private val gpt_engine = "gpt-4-1106"
    fun create(message: String, role: Role): GptChatModel {
        return GptChatModel(gpt_engine, listOf(Message(role.label, message)))
    }

    fun toJson(gptChatModel: GptChatModel): String {
        return Util.toJson(gptChatModel)
    }
}