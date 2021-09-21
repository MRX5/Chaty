package com.example.chaty.ui.conversation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chaty.R
import com.example.chaty.model.Message
import com.example.chaty.utils.Utils
import kotlinx.android.synthetic.main.received_msg_layout.view.*
import kotlinx.android.synthetic.main.sent_msg_layout.view.*

class ConversationAdapter(var messages: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> ViewHolder1(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.sent_msg_layout, parent, false)
            )
            else -> ViewHolder2(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.received_msg_layout, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            1 -> {
                (holder as ViewHolder1).bind(messages[position])
            }
            else -> {
                (holder as ViewHolder2).bind(messages[position])
            }
        }
    }

    override fun getItemCount()=messages.size

    override fun getItemViewType(position: Int): Int {
        return Utils.getMessageType(messages[position])
    }

    class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message) {
            itemView.sent_msg_text.text=message.body
        }
    }

    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message) {
            itemView.received_msg_text.text=message.body
        }
    }

}