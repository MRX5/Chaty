package com.example.chaty.ui.conversation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.example.chaty.R
import com.example.chaty.listeners.OnMessageClickListener
import com.example.chaty.model.Message
import com.example.chaty.utils.Utils
import kotlinx.android.synthetic.main.received_msg_layout.view.*
import kotlinx.android.synthetic.main.sent_msg_layout.view.*

class ConversationAdapter( var messages: List<Message>) :
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
        var sameAsPrevious=false
        if(position>0) sameAsPrevious=messages[position].sender== messages[position-1].sender
        val atTheSameDay=Utils.checkConversationDate(messages,position)
        when (holder.itemViewType) {
            1 -> {
                (holder as ViewHolder1).bind(messages[position],sameAsPrevious,atTheSameDay)
            }
            else -> {
                (holder as ViewHolder2).bind(messages[position],sameAsPrevious,atTheSameDay)
            }
        }
    }

    override fun getItemCount()=messages.size

    override fun getItemViewType(position: Int): Int {
        return Utils.getMessageType(messages[position])
    }

    class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message,sameAsPrevious:Boolean,atTheSameDay:Boolean) {

            if(!sameAsPrevious){     // large space between messages
                val dp=Utils.convertPxToDp(itemView.context,20)
                itemView.updatePadding(top = dp)
            }else {                  // set as normal
                val dp=Utils.convertPxToDp(itemView.context,4)
                itemView.updatePadding(top = dp)
            }

            if(!atTheSameDay){
                itemView.sent_conversation_date.text=Utils.getChatTime(message.time)
                itemView.sent_conversation_date.visibility= VISIBLE
            }else itemView.sent_conversation_date.visibility= GONE

            itemView.sent_msg_text.text=message.body
            itemView.sent_msg_time.text=Utils.getMessageTime(message.time)
        }
    }

    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message,sameAsPrevious:Boolean,atTheSameDay:Boolean) {

            if(!sameAsPrevious){   // large space between messages
                val dp=Utils.convertPxToDp(itemView.context,20)
                itemView.updatePadding(top = dp)
            } else {                 // set as normal
                val dp=Utils.convertPxToDp(itemView.context,4)
                itemView.updatePadding(top = dp)
            }

            if(!atTheSameDay){
                itemView.received_conversation_date.text=Utils.getChatTime(message.time)
                itemView.received_conversation_date.visibility= VISIBLE
            }else itemView.received_conversation_date.visibility= GONE

            itemView.received_msg_text.text=message.body
            itemView.received_msg_time.text=Utils.getMessageTime(message.time)
        }
    }


}