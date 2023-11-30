package com.example.workoutaiassistant.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.workoutaiassistant.databinding.RvItemWorkoutBinding

class RvWorkoutAdapter(private var workouts: List<Workout> = emptyList()) : RecyclerView.Adapter<RvWorkoutItemViewHolder>() {
    fun setData(workouts: List<Workout>) {
        this.workouts = workouts
        notifyDataSetChanged() // TODO("Optimize")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvWorkoutItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemWorkoutBinding.inflate(layoutInflater, parent, false)
        return RvWorkoutItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onBindViewHolder(holder: RvWorkoutItemViewHolder, position: Int) {
        holder.bind(workouts[position])
    }
}

class RvWorkoutItemViewHolder(private val binding: RvItemWorkoutBinding) : ViewHolder(binding.root) {
    fun bind(workout: Workout) {
        binding.rvItemTextWorkout.text = workout.name
        binding.rvItemTextReps.text = "${workout.repetitions} repetitions"
        binding.rvItemTextSets.text = "${workout.sets} sets"
    }
}