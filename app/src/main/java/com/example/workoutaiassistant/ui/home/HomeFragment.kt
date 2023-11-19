package com.example.workoutaiassistant.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutaiassistant.R
import com.example.workoutaiassistant.databinding.FragmentHomeBinding
import com.example.workoutaiassistant.util.Util

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

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

        applyDummyChats(homeViewModel)

        return root
    }

    fun applyDummyChats(homeViewModel: HomeViewModel) {
        try {
            val convoString = Util.readFromRaw(requireContext(), R.raw.convo_sample_1)
            val conversation = Util.parseJsonConversation((convoString))
            homeViewModel.updateConversation(conversation)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}