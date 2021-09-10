package com.example.chaty.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

object MyFirebase {

    val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    val mDatabase: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
    val mCaching= mDatabase.setPersistenceEnabled(true)

    val firebaseMessaging: FirebaseMessaging by lazy {
        FirebaseMessaging.getInstance()
    }
}