package com.example.workoutaiassistant.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutaiassistant.ui.home.model.Chat
import com.example.workoutaiassistant.ui.home.model.Sender

class HomeViewModel : ViewModel() {

    private val _textDay = MutableLiveData<String>().apply {
        value = "<  Today  >"
    }
    val textDay: LiveData<String> = _textDay

    private val _chats = MutableLiveData<List<Chat>>().apply {
        value = getChats()
    }
    val chats: LiveData<List<Chat>> = _chats

    private fun getChats(): List<Chat> {
        return listOf(
            Chat("Hi", Sender.YOU),
            Chat("Hello", Sender.HIM),
            Chat("How are you", Sender.YOU),
            Chat("I'm fine thank you", Sender.HIM),
            Chat("You're welcome", Sender.YOU)
        )
    }

}