package com.example.workoutaiassistant.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.workoutaiassistant.R
import com.example.workoutaiassistant.data.network.AiResponse
import com.example.workoutaiassistant.data.network.Role
import com.example.workoutaiassistant.databinding.FragmentHomeBinding
import com.example.workoutaiassistant.util.Util

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    private val rvChat get() = binding.rvChat
    private val rvWorkouts get() = binding.rvWorkouts
    private val rvWorkoutsAdapter get() = binding.rvWorkouts.adapter as RvWorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        rvChat.layoutManager = LinearLayoutManager(context)
        rvWorkouts.layoutManager = LinearLayoutManager(context)
        rvWorkouts.adapter = RvWorkoutAdapter()

        homeViewModel.conversation.observe(viewLifecycleOwner) {
            rvChat.adapter =
                RvChatAdapter(it.chats.filter { chat -> chat.role == Role.USER || chat.role == Role.ASSISTANT })
            rvChat.adapter?.itemCount?.let {it2 ->
                rvChat.smoothScrollToPosition(it2)
            }
        }

        binding.btnSend.setOnClickListener {
            val message = binding.textChat.text.toString()
            if (message.isNotEmpty()) {
                homeViewModel.sendUserMessage(message).observe(viewLifecycleOwner) {
                    handleAiResponse(it)
                }
                binding.textChat.text.clear()
            }
        }

        binding.btnExpandCollapse.text = "^" // TODO: Change to expand icon
        binding.btnExpandCollapse.setOnClickListener {
            expandOrCollapseChatInterface()
        }

        return binding.root
    }

    private fun expandOrCollapseChatInterface() {
        val constraintSet = ConstraintSet()
        val isExpanded = binding.chatInterface.top == 0 // Check if it's already expanded

        constraintSet.clone(binding.constraintLayout)

        if (isExpanded) {
            // Reset to initial constraints
            constraintSet.clear(
                R.id.chat_interface,
                ConstraintSet.TOP
            )
            binding.btnExpandCollapse.text = "^"
        } else {
            // Change to expanded constraints
            constraintSet.connect(
                R.id.chat_interface,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                0
            )
            binding.btnExpandCollapse.text = "v"  // TODO: Change to collapse icon
        }

        TransitionManager.beginDelayedTransition(binding.constraintLayout)
        constraintSet.applyTo(binding.constraintLayout)
    }

    private fun handleAiResponse(aiResponse: AiResponse) {
        // Parse expected json response message
        val responseMsg = aiResponse.choices[0].message.content
        val contentResponse: ContentResponse

        try {
            contentResponse = Util.toContentResponseJson(responseMsg)
            contentResponse.messages.forEach { msg ->
                homeViewModel.addHisChat(msg) // TODO: Request for only response, not multiple choices
            }
            contentResponse.workouts?.let {
                rvWorkoutsAdapter.setData(it)
            }
        } catch (e: Exception) { // TODO: Consider having a separate conversation with GPT that only has json responses for workouts
            homeViewModel.addHisChat(responseMsg)
            Log.w(
                this.javaClass.name,
                "Added raw response. Failed to parse json response: $responseMsg"
            )
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Provide initial instructions
        homeViewModel.sendSystemMessage(
            getCustomInstructions()
        ).observe(viewLifecycleOwner) {
            handleAiResponse(it)
        }
    }

    private fun getCustomInstructions(): String {
        val rawInstructions = Util.readFromRaw(requireContext(), R.raw.gpt_instructions)
        val workoutFormat = Util.readFromRaw(requireContext(), R.raw.imposed_response_format)
        return rawInstructions.replace("{workout_format}", workoutFormat)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}