package com.example.chaty.ui.register.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chaty.utils.Resource

class LoginViewModel:ViewModel() {

    private var loginState=MutableLiveData<Resource<Unit>>()
    private val repository=LoginRepository()
    fun login(email:String,password:String){
        loginState=repository.login(email,password)
    }

    fun getLoginState()=loginState
}