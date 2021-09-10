package com.example.chaty.ui.friends_requests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chaty.R
import com.example.chaty.listeners.FriendRequestListener
import com.example.chaty.model.Friend_Request
import com.example.chaty.model.User

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
        private val userImage = itemView.findViewById<ImageView>(R.id.user_image)
        private val userName = itemView.findViewById<TextView>(R.id.user_name)
        private val acceptBtn = itemView.findViewById<ImageButton>(R.id.accept_btn)
        private val rejectBtn = itemView.findViewById<ImageButton>(R.id.reject_btn)

        fun bind(request: Friend_Request, listener: FriendRequestListener) {
            userName.text=request.userName
            acceptBtn.setOnClickListener {
                listener.onAcceptClick(request)
            }
            rejectBtn.setOnClickListener {
                listener.onRejectClick(request)
            }
        }

    }

}