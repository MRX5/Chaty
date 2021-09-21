package com.example.chaty.ui.conversation

import android.util.Log
import android.util.TimeUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.firebase.MyFirebase.mDatabase
import com.example.chaty.model.*
import com.example.chaty.network.RetrofitBuilder
import com.example.chaty.utils.Constants
import com.example.chaty.utils.Resource
import com.example.chaty.utils.Utils
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import kotlin.math.log

private const val TAG = "chatmostafa"

class ConversationRepository {
    private val uid = mAuth.currentUser?.uid.toString()

    fun loadChat(userID: String): MutableLiveData<Resource<List<Message>>> {
        val ref = mDatabase.reference
        val chatID = Utils.getChatID(uid, userID)
        val mLiveData = MutableLiveData<Resource<List<Message>>>()
        ref.child(Constants.CHAT_MESSAGES)
            .child(chatID)
            .child(Constants.MESSAGES)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messagesList = mutableListOf<Message>()
                    snapshot.children.forEach {
                        it.getValue(Message::class.java)?.let { msg ->
                            messagesList.add(msg)
                        }
                    }
                    markAsSeen(chatID)
                    mLiveData.value = Resource.success(messagesList)
                }

                override fun onCancelled(error: DatabaseError) {
                    mLiveData.value = Resource.error(error.message, null)
                }
            })

        return mLiveData
    }

    private fun markAsSeen(chatID: String) {
        val ref= mDatabase.
        getReference("${Constants.CHATS}/$uid/$chatID/${Constants.LAST_MESSAGE}")
        ref.child("seen")
            .setValue(true)
    }

    fun sendMessage(receiver: User, messageBody: String): MutableLiveData<Resource<Unit>> {
        val mLiveData = MutableLiveData<Resource<Unit>>()
        val ref = mDatabase.reference
        val chatID = Utils.getChatID(uid, receiver.userID)
        val messageID = System.currentTimeMillis().toString()
        ref.child(Constants.CHAT_MESSAGES)
            .child(chatID)
            .child(Constants.MESSAGES)
            .child(messageID)
            .setValue(Message(messageBody, messageID, uid, receiver.userID)).addOnSuccessListener {

                mLiveData.value = Resource.success(null)
                updateSenderChatLastMessage(receiver.userID,chatID, messageBody, messageID)
                updateReceiverChatLastMessage(receiver.userID, chatID, messageBody, messageID)
                sendNotification(receiver, messageBody)

            }.addOnFailureListener {
                mLiveData.value = Resource.error(it.message.toString(), null)
            }
        return mLiveData
    }

    private fun updateSenderChatLastMessage(
        receiverID: String,
        chatID: String,
        messageBody: String,
        messageID: String
    ) {
        val ref = mDatabase.reference
        ref.child(Constants.CHATS)
            .child(uid)
            .child(chatID)
            .child(Constants.LAST_MESSAGE)
            .setValue(Chat(userID = receiverID, lastMessage = messageBody, time = messageID,seen = true))
    }

    private fun updateReceiverChatLastMessage(
        receiverID: String,
        chatID: String,
        messageBody: String,
        messageID: String
    ) {
        val ref = mDatabase.reference
        ref.child(Constants.CHATS)
            .child(receiverID)
            .child(chatID)
            .child(Constants.LAST_MESSAGE)
            .setValue(Chat(userID = uid, lastMessage = messageBody, time = messageID,seen = false))
    }

    private fun sendNotification(receiver: User, messageBody: String) {
        val ref = mDatabase.getReference("Users/$uid")
        ref.get().addOnSuccessListener { data ->
            val sender = data.getValue(User::class.java)
            sender?.let {
                val notification = PushNotification(
                    NotificationData("${it.userName}-${it.userID}", messageBody), receiver.token
                )
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = RetrofitBuilder.api.postNotification(notification);
                        if (response.isSuccessful) {
                            Log.d(TAG, "Response: $response")
                        } else {
                            Log.d(TAG, response.errorBody().toString())
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, e.message.toString());
                    }
                }
            }
        }
    }



}