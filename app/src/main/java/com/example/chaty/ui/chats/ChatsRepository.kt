package com.example.chaty.ui.chats

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chaty.firebase.MyFirebase
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.firebase.MyFirebase.mDatabase
import com.example.chaty.model.Chat
import com.example.chaty.model.User
import com.example.chaty.utils.Constants
import com.example.chaty.utils.Resource
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatsRepository {

    private val uid = mAuth.currentUser?.uid.toString()
    private val mLiveData = MutableLiveData<Resource<List<Chat>>>()
    private val userLiveData=MutableLiveData<Resource<User>>()

    fun getChats(): MutableLiveData<Resource<List<Chat>>> {
        mLiveData.value= Resource.loading(null)
        val ref = mDatabase.reference
        ref.child(Constants.CHATS)
            .child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val mp = mutableMapOf<String, Chat>()
                    snapshot.children.forEach { child ->
                        child.child(Constants.LAST_MESSAGE).getValue(Chat::class.java)?.let { chat ->
                            mp[chat.userID] = chat
                        }
                    }
                    getChatsInformation(mp)
                }

                override fun onCancelled(error: DatabaseError) {
                    mLiveData.value = Resource.error(error.message, null)

                }

            })
        return mLiveData
    }

    private fun getChatsInformation(mp: MutableMap<String, Chat>) {
        val ref = mDatabase.reference
        val mChats = mutableListOf<Chat>()
        ref.child(Constants.USERS)
            .get().addOnSuccessListener {
                it.children.forEach { user ->
                    val currentUser = user.getValue(User::class.java)
                    currentUser?.let { _user ->
                        if (mp.contains(_user.userID)) {
                            mChats.add(
                                Chat(
                                    _user.userID,
                                    _user.userImageUrl,
                                    _user.userName,
                                    _user.token,
                                    mp[_user.userID]?.lastMessage,
                                    mp[_user.userID]!!.time,
                                    mp[_user.userID]!!.seen
                                )
                            )
                        }
                    }
                }
                mChats.sortByDescending { chat -> chat.time }
                mLiveData.value = Resource.success(mChats)
            }.addOnFailureListener {
                mLiveData.value = Resource.error(it.message.toString(), null)
            }
    }

    fun getCurrentUser(): MutableLiveData<Resource<User>> {
        val ref= mDatabase.reference
        ref.child(Constants.USERS)
            .child(uid)
            .get().addOnSuccessListener { snapShot->
                val user=snapShot.getValue(User::class.java)
                userLiveData.value= Resource.success(user)
            }.addOnFailureListener {
                userLiveData.value= Resource.error(it.message.toString(),null)
            }
        return userLiveData
    }

}