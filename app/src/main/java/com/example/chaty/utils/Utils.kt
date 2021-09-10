package com.example.chaty.utils

import android.util.Log
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.model.Message

class Utils {
    companion object {
        fun getMessageType(message: Message): Int {
            val uid = mAuth.currentUser?.uid.toString()
            return if (message.sender == uid) 1 else 2
        }
        fun getChatID(senderID:String,receiverID:String)=
            if(senderID<receiverID)senderID+receiverID else receiverID+senderID
    }
}