package com.example.workoutaiassistant.ui.home

data class ContentResponse(val messages: List<String>, val workouts: List<Workout>?)
data class Workout(val name: String, val repetitions: Int, val sets: Int)
