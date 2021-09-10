package com.example.chaty.ui.register.splash

import androidx.lifecycle.MutableLiveData
import com.example.chaty.firebase.MyFirebase
import com.example.chaty.utils.Resource
import com.google.firebase.auth.FirebaseAuth

class SplashRepository {

     fun checkCurrentUser():Boolean{
        return MyFirebase.mAuth.currentUser!=null
    }
}