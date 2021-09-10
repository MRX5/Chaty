package com.example.chaty.ui.people

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.firebase.MyFirebase.mDatabase
import com.example.chaty.model.User
import com.example.chaty.utils.Constants
import com.example.chaty.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlin.math.log

private const val TAG = "mostafa"
class PeopleRepository {

    val uid=mAuth.currentUser?.uid.toString()
    private var friendsLiveData=MutableLiveData<Resource<List<User>>>()

     fun getFriends():MutableLiveData<Resource<List<User>>>{

        val ref=mDatabase.reference
        ref.child(Constants.PEOPLE)
            .child(uid)
            .child(Constants.FRIENDS)
            .get().addOnSuccessListener { friends->
                val data=friends.children
                val friendsList= mutableListOf<String>()
                data.forEach { friend ->
                    friendsList.add(friend.key.toString())
                }
                getFriendInfo(friendsList)

            }
        return friendsLiveData
    }

     private fun getFriendInfo(friendsList:List<String>){
        val ref=mDatabase.reference
        val mFriends= mutableListOf<User>()
         ref.child(Constants.USERS)
             .get().addOnSuccessListener { data->

                 data.children.forEach {
                     val friend=it.getValue(User::class.java)
                     friend?.let {_friend->
                         if(friendsList.contains(_friend.userID)){
                             mFriends.add(_friend)
                         }
                     }
                 }
                 friendsLiveData.value= Resource.success(mFriends)
             }.addOnFailureListener {
                 friendsLiveData.value= Resource.error(it.message.toString(),null)
             }
    }

}