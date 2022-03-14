package com.example.chaty.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chaty.firebase.MyFirebase.firebaseMessaging
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.firebase.MyFirebase.mDatabase
import com.example.chaty.utils.Constants
import com.example.chaty.utils.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

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

    fun updateUserStatus(){
        val uid = mAuth.currentUser?.uid.toString()
        val ref= mDatabase.getReference("${Constants.USERS}/$uid/status")
        val connectedRef= FirebaseDatabase.getInstance().getReference(".info/connected")

        connectedRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected=snapshot.getValue(Boolean::class.java)
                if(connected == true){
                    ref.onDisconnect().setValue("offline")
                    ref.setValue("online")
                }else{
                    ref.setValue("offline")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}