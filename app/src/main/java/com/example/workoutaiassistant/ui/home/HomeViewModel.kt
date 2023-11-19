package com.example.workoutaiassistant.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutaiassistant.ui.home.model.Conversation

class HomeViewModel : ViewModel() {

    private val _textDay = MutableLiveData<String>().apply {
        value = "<  Today  >"
    }
    val textDay: LiveData<String> = _textDay

    private val _conversation = MutableLiveData<Conversation>()
    val conversation: LiveData<Conversation> = _conversation

    fun updateConversation(conversation: Conversation) {
        _conversation.value = conversation
    }

}