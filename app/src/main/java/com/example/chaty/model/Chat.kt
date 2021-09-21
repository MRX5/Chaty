package com.example.chaty.model

data class Chat(
    val userID:String="",
    val userImageUrl:String="",
    val userName:String="",
    val token:String="",
    val lastMessage:String?="",
    val time:String="",
    val seen:Boolean=false
)
