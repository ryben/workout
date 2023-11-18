package com.example.workoutaiassistant.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.workoutaiassistant.databinding.RvItemWorkoutBinding

class RvWorkoutAdapter(private val workouts: List<String>) : RecyclerView.Adapter<RvWorkoutItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvWorkoutItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemWorkoutBinding.inflate(layoutInflater, parent, false)
        return RvWorkoutItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onBindViewHolder(holder: RvWorkoutItemViewHolder, position: Int) {
        holder.bind(workouts.get(position))
    }

}

class RvWorkoutItemViewHolder(private val binding: RvItemWorkoutBinding) : ViewHolder(binding.root) {
    fun bind(text: String) {
        binding.rvItemTextWorkout.text = text
    }
}