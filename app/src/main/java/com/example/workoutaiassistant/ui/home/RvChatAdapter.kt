package com.example.workoutaiassistant.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.workoutaiassistant.R
import com.example.workoutaiassistant.data.network.Chat
import com.example.workoutaiassistant.data.network.Role

class RvChatAdapter(private val chats: List<Chat>) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        private const val CHAT_YOU = 0
        private const val CHAT_HIM = 1
        private const val CHAT_OTHER = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CHAT_YOU -> {
                val view = layoutInflater.inflate(R.layout.rv_chat_you, parent, false)
                RvChatYouViewHolder(view)
            }

            CHAT_HIM -> {
                val view = layoutInflater.inflate(R.layout.rv_chat_him, parent, false)
                RvChatYouViewHolder(view)
            }

            else -> {
                throw Exception("Invalid view type")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (chats[position].role) {
            Role.USER -> CHAT_YOU
            Role.ASSISTANT -> CHAT_HIM
            else -> CHAT_OTHER
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is RvChatYouViewHolder -> holder.bind(chats.get(position))
            is RvChatHimViewHolder -> holder.bind(chats.get(position))
        }
    }
}

class RvChatYouViewHolder(itemView: View) : ViewHolder(itemView) {
    private val textChat = itemView.findViewById<TextView>(R.id.text_chat)
    fun bind(chat: Chat) {
        textChat.text = chat.content
    }
}

class RvChatHimViewHolder(itemView: View) : ViewHolder(itemView) {
    private val textChat = itemView.findViewById<TextView>(R.id.text_chat)
    fun bind(chat: Chat) {
        textChat.text = chat.content
    }
}
