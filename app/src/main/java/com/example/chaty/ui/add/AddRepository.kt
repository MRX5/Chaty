package com.example.chaty.ui.add

import androidx.lifecycle.MutableLiveData
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.firebase.MyFirebase.mDatabase
import com.example.chaty.model.Friend_Request
import com.example.chaty.model.User
import com.example.chaty.utils.Constants
import com.example.chaty.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddRepository {

    val uid=mAuth.currentUser?.uid.toString()

    private var mLiveData = MutableLiveData<Resource<List<User>>>()
    private var addLiveData = MutableLiveData<Resource<Unit>>()

    fun getPeople(): MutableLiveData<Resource<List<User>>> {
        val ref = mDatabase.reference
        ref.child(Constants.PEOPLE)
            .child(uid)
            .child(Constants.FRIENDS)
            .get()
            .addOnSuccessListener { friends ->
                val friendsList = mutableListOf<String>()
                friends.children.forEach { friend ->
                    friendsList.add(friend.key.toString())
                }
                friendsList.add(uid)
                getNotFriends(friendsList)
            }.addOnFailureListener { error ->
                mLiveData.value = Resource.error(error.message.toString(), null)
            }
        return mLiveData
    }

    private fun getNotFriends(friends: List<String>) {
        val ref = mDatabase.reference
        val usersList = mutableListOf<User>()
        ref.child(Constants.USERS)
            .limitToFirst(100)
            .get()
            .addOnSuccessListener { users ->
                users.children.forEach { child ->
                    val user = child.getValue(User::class.java)
                    user?.let { usersList.add(it) }
                }
                usersList.removeAll {
                    it.userID in friends
                }
                mLiveData.value = Resource.success(usersList)
            }
    }

    fun addFriend(user: User): MutableLiveData<Resource<Unit>> {
        val ref = mDatabase.reference
        ref.child(Constants.USERS)
            .child(uid)
            .get().addOnSuccessListener {
                val currentUser = it.getValue(User::class.java)
                currentUser?.let {
                    addToFriendsRequests(user.userID,currentUser)
                }
            }
        return addLiveData
    }

    private fun addToFriendsRequests(friendID:String,user: User) {
        val ref=mDatabase.reference
        ref.child(Constants.PEOPLE)
            .child(friendID)
            .child(Constants.FRIENDS_REQUESTS)
            .child(uid)
            .setValue(Friend_Request(user.userID, user.userName, user.userImageUrl))
            .addOnSuccessListener {
                addLiveData.value = Resource.success(null)
            }.addOnFailureListener {
                addLiveData.value = Resource.error(it.message.toString(), null)
            }
    }

}