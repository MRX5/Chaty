package com.example.chaty.ui.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chaty.R
import com.example.chaty.model.User
import com.example.chaty.listeners.OnItemClickListener
import com.google.android.material.button.MaterialButton

class AddFriendAdapter(var people: List<User>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<AddFriendAdapter.AddFriendViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendViewHolder {
        return AddFriendViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.add_friend_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddFriendViewHolder, position: Int) {
        holder.bind(people[position],listener)
    }

    override fun getItemCount() = people.size

    class AddFriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userImage=itemView.findViewById<ImageView>(R.id.user_image)
        private val userName=itemView.findViewById<TextView>(R.id.user_name)
        private val addFriendBtn =itemView.findViewById<MaterialButton>(R.id.add_friend_btn)
        fun bind(user: User,listener: OnItemClickListener) {
            userName.text=user.userName
            addFriendBtn.setOnClickListener {
                listener.onItemClick(user)
            }
        }

    }
}