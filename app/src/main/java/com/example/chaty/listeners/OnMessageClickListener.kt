package com.example.chaty.listeners

interface OnMessageClickListener {
    fun onMessageClick(messageState:Boolean)  // true: sent , false: received
}