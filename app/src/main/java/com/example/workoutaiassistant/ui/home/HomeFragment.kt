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
            recyclerView.adapter = RvChatAdapter(it.chats)
        }

        binding.btnSend.setOnClickListener {
            val message = binding.textChat.text.toString()
            if (message.isNotEmpty()) {
                homeViewModel.addMyChat(message)
                homeViewModel.sendMessage(message).observe(viewLifecycleOwner) {
                    homeViewModel.addHisChat(it.choices[0].text.trim())
                }
                binding.textChat.text.clear()
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.sendMessage(
            Util.readFromRaw(requireContext(), R.raw.gpt_instructions), Role.SYSTEM
        ).observe(viewLifecycleOwner) {
            homeViewModel.addHisChat(it.choices.get(0).text.trim())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}