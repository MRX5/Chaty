package com.example.chaty.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaty.utils.Resource
import com.google.firebase.messaging.FirebaseMessaging

class MainViewModel :ViewModel() {
    private var uploadState= MutableLiveData<Resource<String>>()
    private val repository=MainRepository()
    fun uploadToken(){
        uploadState=repository.uploadToken()
    }

    fun getUploadState()=uploadState

}