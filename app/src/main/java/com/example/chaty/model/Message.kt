package com.example.chaty.model

import android.content.BroadcastReceiver

data class Message(
    val body:String="",
    val time:String="",
    val sender:String="",
    val receiver:String=""
)
