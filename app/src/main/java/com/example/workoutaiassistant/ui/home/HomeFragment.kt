package com.example.workoutaiassistant.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutaiassistant.R
import com.example.workoutaiassistant.data.network.Role
import com.example.workoutaiassistant.databinding.FragmentHomeBinding
import com.example.workoutaiassistant.util.Util
import com.google.android.material.bottomsheet.BottomSheetBehavior

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


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

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        // Configure the initial and expanded states
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = 200 // Set your desired peek height

        // Optional: Listen for state changes
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to sliding
            }
        })


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