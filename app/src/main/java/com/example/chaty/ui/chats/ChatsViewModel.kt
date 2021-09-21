package com.example.chaty.ui.chats

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaty.model.Chat
import com.example.chaty.model.User
import com.example.chaty.utils.Resource

class ChatsViewModel: ViewModel() {

    private var chatsLiveData=MutableLiveData<Resource<List<Chat>>>()
    private var currentUserLiveData=MutableLiveData<Resource<User>>()
    private val repository=ChatsRepository()
    init {
        getCurrentUser()
        getChats()
    }
    private fun getChats(){
        chatsLiveData=repository.getChats()
    }
    private fun getCurrentUser()
    {
        currentUserLiveData=repository.getCurrentUser()
    }
    fun loadChats()=chatsLiveData
    fun currentUser()=currentUserLiveData
}