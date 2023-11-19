package com.example.workoutaiassistant.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutaiassistant.data.model.Chat
import com.example.workoutaiassistant.data.model.Conversation
import com.example.workoutaiassistant.data.model.OpenAIResponse
import com.example.workoutaiassistant.data.model.Prompt
import com.example.workoutaiassistant.data.model.Sender
import com.example.workoutaiassistant.data.network.GptChatModelFactory
import com.example.workoutaiassistant.data.network.RetrofitClient
import com.example.workoutaiassistant.data.network.Role
import com.example.workoutaiassistant.util.Util
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

    fun setConversation(conversation: Conversation) {
        _conversation.value = conversation
    }

    fun addHisChat(chat: String) {
        val convo = _conversation.value
        convo?.chats?.add(Chat(chat, Sender.HIM, "12:00"))
        _conversation.value = convo
    }
    fun addMyChat(chat: String) {
        val convo = _conversation.value
        convo?.chats?.add(Chat(chat, Sender.YOU, "12:00"))
    }
    fun sendMessage(promptText: String, role: Role = Role.USER): LiveData<OpenAIResponse> {
        val responseLiveData: MutableLiveData<OpenAIResponse> = MutableLiveData()
        val prompt = Prompt(
            Util.toJson(GptChatModelFactory.create(
                promptText, role
            )), 1000) // 150 is an example token limit

        RetrofitClient.instance.getResponse(prompt)
            .enqueue(object : Callback<OpenAIResponse> {
                override fun onResponse(
                    call: Call<OpenAIResponse>,
                    response: Response<OpenAIResponse>
                ) {
                    if (response.isSuccessful) {
                        responseLiveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<OpenAIResponse>, t: Throwable) {
                    // Handle failure
                    Log.d("HomeViewModel", "Failed to get response from GPT")
                }
            })

        return responseLiveData
    }


}