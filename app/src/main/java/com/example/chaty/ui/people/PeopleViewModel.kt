package com.example.chaty.ui.people

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chaty.model.User
import com.example.chaty.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PeopleViewModel : ViewModel() {
    private var friendsLivedata=MutableLiveData<Resource<List<User>>>()
    private val repository=PeopleRepository()
    init {
        getFriends()
    }
    private fun getFriends(){
            friendsLivedata=repository.getFriends()
    }
    fun loadFriends()=friendsLivedata

}