package com.example.workoutaiassistant.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.workoutaiassistant.databinding.RvItemWorkoutBinding

class RvAdapter(private val workouts: List<String>) : RecyclerView.Adapter<RvItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemWorkoutBinding.inflate(layoutInflater, parent, false)
        return RvItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onBindViewHolder(holder: RvItemViewHolder, position: Int) {
        holder.bind(workouts.get(position))
    }

}

class RvItemViewHolder(private val binding: RvItemWorkoutBinding) : ViewHolder(binding.root) {
    fun bind(text: String) {
        binding.rvItemTextWorkout.text = text
    }
}