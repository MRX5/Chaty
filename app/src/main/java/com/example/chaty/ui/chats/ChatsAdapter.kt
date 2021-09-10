package com.example.chaty.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chaty.R
import com.example.chaty.listeners.OnItemClickListener
import com.example.chaty.model.Chat
import com.example.chaty.model.User

class ChatsAdapter(var chats: List<Chat>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        return ChatsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.bind(chats[position], listener)
    }

    override fun getItemCount() = chats.size

    class ChatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage = itemView.findViewById<ImageView>(R.id.chat_user_image)
        val userName_TV = itemView.findViewById<TextView>(R.id.chat_username)
        val lastMessage_TV = itemView.findViewById<TextView>(R.id.chat_last_message)

        fun bind(chat: Chat, listener: OnItemClickListener) {
            userName_TV.text = chat.userName
            lastMessage_TV.text = chat.lastMessage
            itemView.setOnClickListener {
                listener.onItemClick(User(userID = chat.userID,token = chat.token, userName = chat.userName))
            }

        }

    }
}