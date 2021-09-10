package com.example.chaty.ui.friends_requests

import androidx.lifecycle.MutableLiveData
import com.example.chaty.model.User
import com.example.chaty.utils.Resource
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.firebase.MyFirebase.mDatabase
import com.example.chaty.model.Friend_Request
import com.example.chaty.utils.Constants

class FriendsRequestsRepository {
    var mRequestLiveData = MutableLiveData<Resource<Unit>>()

    fun loadFriendsRequests(): MutableLiveData<Resource<List<Friend_Request>>> {
        val mLiveData = MutableLiveData<Resource<List<Friend_Request>>>()
        val ref = mDatabase.reference
        val uid = mAuth.currentUser?.uid.toString()
        ref.child(Constants.PEOPLE)
            .child(uid)
            .child(Constants.FRIENDS_REQUESTS)
            .get().addOnSuccessListener { response ->
                val requests = response.children
                val friends_requests = mutableListOf<Friend_Request>()
                requests.forEach { request ->
                    val value = request.getValue(Friend_Request::class.java)
                    value?.let {
                        friends_requests.add(it)
                    }
                }
                mLiveData.value = Resource.success(friends_requests)
            }.addOnFailureListener {
                mLiveData.value = Resource.error(it.message.toString(), null)
            }
        return mLiveData
    }

    fun acceptRequest(request: Friend_Request): MutableLiveData<Resource<Unit>> {
        val ref = mDatabase.reference
        val uid = mAuth.currentUser?.uid.toString()
        ref.child(Constants.PEOPLE)
            .child(uid)
            .child(Constants.FRIENDS_REQUESTS)
            .child(request.userID)
            .removeValue().addOnSuccessListener {
                addToFriends(request.userID)
                addToMyFriends(request.userID)
            }.addOnFailureListener {
                mRequestLiveData.value = Resource.error(it.message.toString(), null)
            }
        return mRequestLiveData
    }

    private fun addToFriends(userID: String) {
        val ref = mDatabase.reference
        val uid = mAuth.currentUser?.uid.toString()
        ref.child(Constants.PEOPLE)
            .child(uid)
            .child(Constants.FRIENDS)
            .child(userID)
            .setValue("").addOnSuccessListener {
                mRequestLiveData.value = Resource.success(null)
            }.addOnFailureListener {
                mRequestLiveData.value = Resource.error(it.message.toString(), null)
            }
    }

    private fun addToMyFriends(userID: String) {
        val ref = mDatabase.reference
        val uid = mAuth.currentUser?.uid.toString()
        ref.child(Constants.PEOPLE)
            .child(userID)
            .child(Constants.FRIENDS)
            .child(uid)
            .setValue("").addOnSuccessListener {
                mRequestLiveData.value = Resource.success(null)
            }.addOnFailureListener {
                mRequestLiveData.value = Resource.error(it.message.toString(), null)
            }
    }


    fun rejectRequest(request: Friend_Request): MutableLiveData<Resource<Unit>> {
        val rejectLiveData=MutableLiveData<Resource<Unit>>()
        val ref= mDatabase.reference
        val uid=mAuth.currentUser?.uid.toString()
        ref.child(Constants.PEOPLE)
            .child(uid)
            .child(Constants.FRIENDS_REQUESTS)
            .child(request.userID)
            .removeValue().addOnSuccessListener {
                rejectLiveData.value=Resource.success(null)
            }.addOnFailureListener {
                rejectLiveData.value=Resource.error(it.message.toString(),null)
            }
        return rejectLiveData
    }
}