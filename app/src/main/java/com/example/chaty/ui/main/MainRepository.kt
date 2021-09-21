package com.example.chaty.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.chaty.firebase.MyFirebase.firebaseMessaging
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.firebase.MyFirebase.mDatabase
import com.example.chaty.utils.Resource

class MainRepository {

    fun uploadToken(): MutableLiveData<Resource<String>> {
        val mLiveData= MutableLiveData<Resource<String>>()
        firebaseMessaging.token.addOnSuccessListener { token ->
            val ref = mDatabase.reference
            val uid = mAuth.currentUser?.uid.toString()
            ref.child("Users")
                .child(uid)
                .child("token")
                .setValue(token.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mLiveData.value= Resource.success(token)
                    }else{
                        mLiveData.value= Resource.error(task.exception?.message.toString(),null)
                    }
                }
        }
        return mLiveData
    }
}