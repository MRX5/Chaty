package com.example.chaty.ui.register.sign_up

import androidx.lifecycle.MutableLiveData
import com.example.chaty.model.User
import com.example.chaty.utils.Constants
import com.example.chaty.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SignUpRepository {
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val mDatabase: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }
    private val firebaseMessaging: FirebaseMessaging by lazy {
        FirebaseMessaging.getInstance()
    }

    fun createAccount(email: String, password: String): MutableLiveData<Resource<Unit>> {
        val mLiveData = MutableLiveData<Resource<Unit>>()
        mLiveData.value = Resource.loading(null)
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mLiveData.value = Resource.success(null)
            } else {
                mLiveData.value = Resource.error(task.exception?.message.toString(), null)
            }
        }
        return mLiveData
    }

    fun saveIntoDatabase(userName: String, email: String): MutableLiveData<Resource<Unit>> {
        val ref = mDatabase.reference
        val uid = mAuth.currentUser?.uid.toString()
        val mLiveData = MutableLiveData<Resource<Unit>>()

        ref.child(Constants.USERS)
            .child(uid)
            .setValue(User(uid,userName,"", email, ""))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mLiveData.value = Resource.success(null)
                } else {
                    mLiveData.value = Resource.error(task.exception?.message.toString(), null)
                }
            }
        return mLiveData
    }

}