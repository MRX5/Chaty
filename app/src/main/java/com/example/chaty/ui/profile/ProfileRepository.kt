package com.example.chaty.ui.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.utils.Resource
import com.example.chaty.firebase.MyFirebase.mDatabase
import com.example.chaty.firebase.MyFirebase.mStorage
import com.example.chaty.model.User
import com.example.chaty.utils.Constants

class ProfileRepository {
    private val userLiveData=MutableLiveData<Resource<User>>()
    private val imageLiveData=MutableLiveData<Resource<String>>()
    fun getCurrentUser(): MutableLiveData<Resource<User>> {
        val ref = mDatabase.reference
        val uid = mAuth.currentUser?.uid.toString()
        ref.child(Constants.USERS)
            .child(uid)
            .get().addOnSuccessListener { user ->
                val currentUser = user.getValue(User::class.java)
                userLiveData.value = Resource.success(currentUser)
            }.addOnFailureListener {
                userLiveData.value = Resource.error(it.message.toString(), null)
            }
        return userLiveData
    }

    fun uploadImage(data: Uri?): MutableLiveData<Resource<String>> {
        imageLiveData.value=Resource.loading(null)
        data?.let {
            val uid = mAuth.currentUser?.uid.toString()
            val ref = mStorage.getReference("${Constants.IMAGES}/$uid")
            ref.putFile(data).addOnSuccessListener {
                ref.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val dbRef = mDatabase.reference
                        imageLiveData.value = Resource.success(task.result.toString())
                        dbRef.child(Constants.USERS)
                            .child(uid)
                            .child("userImageUrl")
                            .setValue(task.result.toString())
                    } else {
                        imageLiveData.value=Resource.error(task.exception?.message.toString(),null)
                    }
                }
            }
        }
        return imageLiveData
    }


}