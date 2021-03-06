package com.example.chaty.ui.register.splash

import androidx.lifecycle.ViewModel


private const val TAG = "SplashViewModel"

class SplashViewModel : ViewModel() {

    private val repository = SplashRepository()

    fun checkCurrentUser(): Boolean {
        return repository.checkCurrentUser()
    }

}