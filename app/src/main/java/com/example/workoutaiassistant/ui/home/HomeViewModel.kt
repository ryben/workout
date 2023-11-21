package com.example.workoutaiassistant.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutaiassistant.data.model.Conversation
import com.example.workoutaiassistant.data.network.AiRequest
import com.example.workoutaiassistant.data.network.AiResponse
import com.example.workoutaiassistant.data.network.Chat
import com.example.workoutaiassistant.data.network.RetrofitClient
import com.example.workoutaiassistant.data.network.Role
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _textDay = MutableLiveData<String>().apply {
        value = "<  Today  >"
    }
    val textDay: LiveData<String> = _textDay

    private val _conversation = MutableLiveData<Conversation>().apply {
        value = Conversation(
            mutableListOf()
        )
    }
    val conversation: LiveData<Conversation> = _conversation

    fun addHisChat(chat: String) {
        val convo = _conversation.value
        convo?.chats?.add(Chat(Role.ASSISTANT, chat))
        _conversation.value = convo
    }

    fun addMyChat(chat: String) {
        val convo = _conversation.value
        convo?.chats?.add(Chat(Role.USER, chat))
        _conversation.value = convo
    }

    fun addSystemChat(chat: String) {
        val convo = _conversation.value
        convo?.chats?.add(Chat(Role.SYSTEM, chat))
        _conversation.value = convo
    }

    fun sendUserMessage(message: String): LiveData<AiResponse> {
        return sendMessage(message, Role.USER)
    }
    fun sendSystemMessage(message: String): LiveData<AiResponse> {
        return sendMessage(message, Role.SYSTEM)
    }

    private fun sendMessage(message: String, role: Role): LiveData<AiResponse> {
        when (role) {
            Role.USER -> addMyChat(message)
            Role.SYSTEM -> addSystemChat(message)
            Role.ASSISTANT -> addHisChat(message)
        }

        val responseLiveData: MutableLiveData<AiResponse> = MutableLiveData()
        val request =
            AiRequest(
                "gpt-4-1106-preview", conversation.value!!.chats
            )

        RetrofitClient.instance.getResponse(request)
            .enqueue(object : Callback<AiResponse> {
                override fun onResponse(
                    call: Call<AiResponse>,
                    response: Response<AiResponse>
                ) {
                    if (response.isSuccessful) {
                        responseLiveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<AiResponse>, t: Throwable) {
                    // Handle failure
                    Log.d(this.javaClass.simpleName, "Failed to get response from GPT")
                }
            })

        return responseLiveData
    }


}