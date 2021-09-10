package com.example.chaty.ui.profile

import androidx.lifecycle.MutableLiveData
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.utils.Resource
import com.example.chaty.firebase.MyFirebase.mDatabase
import com.example.chaty.model.User
import com.example.chaty.utils.Constants

class ProfileRepository {
    private val mLiveData=MutableLiveData<Resource<User>>()
    fun getCurrentUser(): MutableLiveData<Resource<User>> {
        val ref= mDatabase.reference
        val uid= mAuth.currentUser?.uid.toString()
        ref.child(Constants.USERS)
            .child(uid)
            .get().addOnSuccessListener { user->
                val currentUser=user.getValue(User::class.java)
                mLiveData.value= Resource.success(currentUser)
            }.addOnFailureListener {
                mLiveData.value= Resource.error(it.message.toString(),null)
            }
        return mLiveData
    }
}