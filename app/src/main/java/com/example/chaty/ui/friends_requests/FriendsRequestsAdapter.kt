package com.example.chaty.ui.friends_requests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chaty.R
import com.example.chaty.listeners.FriendRequestListener
import com.example.chaty.model.Friend_Request
import com.example.chaty.model.User
import kotlinx.android.synthetic.main.friend_request_layout.view.*

class FriendsRequestsAdapter(var requests: List<Friend_Request>, val listener: FriendRequestListener) :
    RecyclerView.Adapter<FriendsRequestsAdapter.FriendsRequestsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FriendsRequestsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.friend_request_layout, parent, false)
        )

    override fun onBindViewHolder(holder: FriendsRequestsViewHolder, position: Int) {
        holder.bind(requests[position], listener)
    }

    override fun getItemCount()=requests.size

    class FriendsRequestsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(request: Friend_Request, listener: FriendRequestListener) {

            itemView.user_name.text=request.userName
            Glide.with(itemView.context).load(request.userImageUrl)
                .placeholder(R.drawable.default_user)
                .into(itemView.user_image)
            itemView.accept_btn.setOnClickListener {
                listener.onAcceptClick(request)
            }
            itemView.reject_btn.setOnClickListener {
                listener.onRejectClick(request)
            }
        }

    }

}