package com.example.workoutaiassistant.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _textDay = MutableLiveData<String>().apply {
        value = "<  Today  >"
    }
    val textDay: LiveData<String> = _textDay
}