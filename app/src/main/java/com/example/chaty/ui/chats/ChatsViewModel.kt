package com.example.chaty.ui.chats

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaty.model.Chat
import com.example.chaty.utils.Resource

class ChatsViewModel: ViewModel() {

    private var chatsLiveData=MutableLiveData<Resource<List<Chat>>>()
    private val repository=ChatsRepository()
    init {
        getChats()
    }
    private fun getChats(){
        chatsLiveData=repository.getChats()
    }
    fun loadChats()=chatsLiveData
}