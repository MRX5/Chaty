package com.example.chaty.ui.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chaty.R
import com.example.chaty.model.User
import com.example.chaty.listeners.OnItemClickListener
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.add_friend_layout.view.*

class AddFriendAdapter(var people: List<User>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<AddFriendAdapter.AddFriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendViewHolder {
        return AddFriendViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.add_friend_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddFriendViewHolder, position: Int) {
        holder.bind(people[position], listener)
    }

    override fun getItemCount() = people.size

    class AddFriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User, listener: OnItemClickListener) {
            itemView.user_name.text = user.userName
            Glide.with(itemView.context).load(user.userImageUrl)
                .placeholder(R.drawable.default_user)
                .into(itemView.user_image)
            itemView.add_friend_btn.setOnClickListener {
                listener.onItemClick(user)
            }
        }
    }
}