package com.example.chaty.ui.conversation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaty.model.Message
import com.example.chaty.model.User
import com.example.chaty.utils.Resource

class ConversationViewModel: ViewModel() {

    private var chatLiveData=MutableLiveData<Resource<List<Message>>>()
    private val repository=ConversationRepository()

    fun loadChat(userID: String) =repository.loadChat(userID)

    fun sendMessage(receiver: User, messageBody: String)=repository.sendMessage(receiver,messageBody)

}