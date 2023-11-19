package com.example.workoutaiassistant.data.model

data class Prompt(val prompt: String, val max_tokens: Int)
data class OpenAIResponse(val choices: List<Choice>)
data class Choice(val text: String)