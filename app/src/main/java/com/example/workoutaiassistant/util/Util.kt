package com.example.workoutaiassistant.util

import android.content.Context
import com.example.workoutaiassistant.data.model.Conversation
import com.google.gson.Gson
import java.io.BufferedReader

object Util {

    private val gson = Gson()
    fun parseJsonConversation(json: String): Conversation {
        return gson.fromJson(json, Conversation::class.java)
    }

    fun readFromRaw(context: Context, resourceId: Int): String {
        return context.resources.openRawResource(resourceId).use { inputStream ->
            BufferedReader(inputStream.reader()).use { bufferedReader ->
                bufferedReader.readText()
            }
        }
    }
}