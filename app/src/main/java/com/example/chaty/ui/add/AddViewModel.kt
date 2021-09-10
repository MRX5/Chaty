package com.example.chaty.ui.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaty.model.User
import com.example.chaty.utils.Resource

class AddViewModel : ViewModel() {

    private var peopleLiveData = MutableLiveData<Resource<List<User>>>()
    private var addLiveData = MutableLiveData<Resource<Unit>>()

    private val repository = AddRepository()

    init {
        getPeople()
    }

    private fun getPeople() {
        peopleLiveData = repository.getPeople()
    }

    fun addFriend(user: User) {
        addLiveData=repository.addFriend(user)
    }

    fun getPeopleLiveData() = peopleLiveData
    fun getAddFriendState()=addLiveData

}