package com.example.chaty.ui.friends_requests

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaty.model.Friend_Request
import com.example.chaty.model.User
import com.example.chaty.utils.Resource

class FriendsRequestsViewModel : ViewModel() {
    private var requestsLiveData = MutableLiveData<Resource<List<Friend_Request>>>()
    private var acceptLiveData=MutableLiveData<Resource<Unit>>()
    private var rejectLiveData=MutableLiveData<Resource<Unit>>()
    private val repository = FriendsRequestsRepository()

    init {
        loadFriendsRequests()
    }

    private fun loadFriendsRequests() {
        requestsLiveData = repository.loadFriendsRequests()
    }

    fun acceptRequest(request: Friend_Request)=
        repository.acceptRequest(request)

    fun rejectRequest(request: Friend_Request)=
        repository.rejectRequest(request)

    fun getFriendsRequests() = requestsLiveData

}