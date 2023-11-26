package com.example.workoutaiassistant.util

import android.content.Context
import com.example.workoutaiassistant.ui.home.ContentResponse
import com.google.gson.Gson
import java.io.BufferedReader

object Util {

    private val gson = Gson()

    fun readFromRaw(context: Context, resourceId: Int): String {
        return context.resources.openRawResource(resourceId).use { inputStream ->
            BufferedReader(inputStream.reader()).use { bufferedReader ->
                bufferedReader.readText()
            }
        }
    }

    fun toContentResponseJson(response: String): ContentResponse {
        return gson.fromJson(response, ContentResponse::class.java)
    }
}