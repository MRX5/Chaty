package com.example.chaty.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.chaty.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class MainRepository {
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val mDatabase: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
    private val firebaseMessaging: FirebaseMessaging by lazy {
        FirebaseMessaging.getInstance()
    }

    fun uploadToken():MutableLiveData<Resource<String>>{
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