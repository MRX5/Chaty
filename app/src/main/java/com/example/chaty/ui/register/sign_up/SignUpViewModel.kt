package com.example.chaty.ui.register.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaty.utils.Resource

class SignUpViewModel : ViewModel() {
    private val repo=SignUpRepository()
    private var authLiveData=MutableLiveData<Resource<Unit>>()
    private var databaseLiveData=MutableLiveData<Resource<Unit>>()

    fun createAccount(email: String, password: String) {
        authLiveData=repo.createAccount(email, password)
    }
    fun saveIntoDatabase(userName: String,email: String){
        databaseLiveData=repo.saveIntoDatabase(userName,email)
    }

    fun createAccountState()=authLiveData
    fun saveIntoDatabaseState()=databaseLiveData
}