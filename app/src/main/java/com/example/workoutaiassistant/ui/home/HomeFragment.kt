package com.example.workoutaiassistant.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutaiassistant.R
import com.example.workoutaiassistant.data.network.Role
import com.example.workoutaiassistant.databinding.FragmentHomeBinding
import com.example.workoutaiassistant.util.Util

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDay
        homeViewModel.textDay.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val recyclerView = binding.rvChat
        recyclerView.layoutManager = LinearLayoutManager(context)

        homeViewModel.conversation.observe(viewLifecycleOwner) {
            recyclerView.adapter =
                RvChatAdapter(it.chats.filter { chat -> chat.role == Role.USER || chat.role == Role.ASSISTANT })
        }

        binding.btnSend.setOnClickListener {
            val message = binding.textChat.text.toString()
            if (message.isNotEmpty()) {
                homeViewModel.sendUserMessage(message).observe(viewLifecycleOwner) {
                    homeViewModel.addHisChat(it.choices[0].message.content.trim())
                    recyclerView.smoothScrollToPosition((recyclerView.adapter?.itemCount ?: 0) - 1)
                }
                binding.textChat.text.clear()
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Provide initial instructions
        homeViewModel.sendSystemMessage(
            getCustomInstructions()
        ).observe(viewLifecycleOwner) {
            homeViewModel.addHisChat(it.choices[0].message.content.trim())
        }
    }

    fun getCustomInstructions(): String {
        val rawInstructions = Util.readFromRaw(requireContext(), R.raw.gpt_instructions)
        val workout_format =  Util.readFromRaw(requireContext(), R.raw.workout_format)
        return rawInstructions.replace("{workout_format}", workout_format)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}