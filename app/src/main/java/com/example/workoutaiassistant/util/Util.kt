package com.example.workoutaiassistant.util

import android.content.Context
import android.widget.Toast
import com.example.workoutaiassistant.R
import com.example.workoutaiassistant.data.model.Conversation
import com.example.workoutaiassistant.data.network.GptChatModel
import com.google.gson.Gson
import java.io.BufferedReader

object Util {

    private val gson = Gson()
    fun parseJsonConversation(json: String): Conversation {
        return gson.fromJson(json, Conversation::class.java)
    }

    fun parseJsonGptReply(json: String): GptChatModel {
        return gson.fromJson(json, GptChatModel::class.java)
    }

    fun readFromRaw(context: Context, resourceId: Int): String {
        return context.resources.openRawResource(resourceId).use { inputStream ->
            BufferedReader(inputStream.reader()).use { bufferedReader ->
                bufferedReader.readText()
            }
        }
    }

    fun toJson(obj: Any): String {
        return gson.toJson(obj)
    }

    fun getSampleConversation(context: Context): Conversation {
        try {
            val convoString = readFromRaw(context, R.raw.convo_sample_1)
            val conversation = parseJsonConversation((convoString))
            return conversation
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

        return Conversation(mutableListOf())
    }


}