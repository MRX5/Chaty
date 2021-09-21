package com.example.chaty.ui.chats

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chaty.R
import com.example.chaty.listeners.OnItemClickListener
import com.example.chaty.model.Chat
import com.example.chaty.model.User
import com.example.chaty.utils.Utils
import kotlinx.android.synthetic.main.chat_layout.view.*

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

        fun bind(chat: Chat, listener: OnItemClickListener) {
            itemView.chat_username.text = chat.userName                          // set user name
            itemView.chat_last_message.text = chat.lastMessage                    // set chat message
            //---------------
            if(!chat.seen){
                itemView.chat_username.setTypeface(null,Typeface.BOLD)
                itemView.chat_last_message.setTextColor(itemView.context.getColor(R.color.black))
                itemView.chat_last_message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
                itemView.chat_last_message.setTypeface(null,Typeface.BOLD)
            }
            //---------------
            Utils.getChatTime(chat.time).let {                 //set chat time
                itemView.message_time_tv.text = it
            }
            //---------------
            if (chat.seen) {                                   // set seen notification
                itemView.unread_messages_notify.visibility = GONE
            }
            //---------------
            Glide.with(itemView.context).load(chat.userImageUrl)  //set user image
                .placeholder(itemView.context?.let { it1 ->
                    AppCompatResources.getDrawable(it1, R.drawable.default_user) })
                .into(itemView.chat_user_image)
            //---------------
            itemView.setOnClickListener {
                listener.onItemClick(
                    User(
                        userID = chat.userID,
                        userImageUrl=chat.userImageUrl,
                        token = chat.token,
                        userName = chat.userName
                    )
                )
            }
        }
    }
}