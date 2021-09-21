package com.example.chaty.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaty.model.User
import com.example.chaty.utils.Resource

class ProfileViewModel: ViewModel() {
    private val repository=ProfileRepository()
    private var mLiveData=MutableLiveData<Resource<User>>()
    init {
        getCurrentUser()
    }
    private fun getCurrentUser(){mLiveData=repository.getCurrentUser()}
    fun getUserInfo()=mLiveData
    fun uploadImageToFirebase(data: Uri?) = repository.uploadImage(data)
}