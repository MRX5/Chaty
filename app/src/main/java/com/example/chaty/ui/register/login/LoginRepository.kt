package com.example.chaty.ui.register.login

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.chaty.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class LoginRepository {
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val mDatabase: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
    private val firebaseMessaging: FirebaseMessaging by lazy {
        FirebaseMessaging.getInstance()
    }

    fun login(email: String, password: String): MutableLiveData<Resource<Unit>> {

        val mLiveData = MutableLiveData<Resource<Unit>>()
        mLiveData.value = Resource.loading(null)
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mLiveData.value = Resource.success(null)
            } else {
                mLiveData.value = Resource.error(task.exception?.message.toString(), null)
            }
        }
        return mLiveData
    }

    fun uploadToken() :MutableLiveData<Resource<Unit>>{
        val mLiveData=MutableLiveData<Resource<Unit>>()
        firebaseMessaging.token.addOnSuccessListener { token ->
            val ref = mDatabase.reference
            val uid = mAuth.currentUser?.uid.toString()
            ref.child("Users")
                .child(uid)
                .child("token")
                .setValue(token.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mLiveData.value= Resource.success(null)
                    }else{
                        mLiveData.value= Resource.error(task.exception?.message.toString(),null)
                    }
                }
        }
        return mLiveData
    }
}