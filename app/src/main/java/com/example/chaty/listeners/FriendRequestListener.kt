package com.example.chaty.listeners

import com.example.chaty.model.Friend_Request
import com.example.chaty.model.User

interface FriendRequestListener {
    fun onAcceptClick(request: Friend_Request)
    fun onRejectClick(request: Friend_Request)
}