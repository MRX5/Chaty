package com.example.chaty.ui.register

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.chaty.R
import com.example.chaty.firebase.FirebaseService
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        FirebaseService.sharedPref=getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        //Firebase.database.setPersistenceEnabled(true)
    }
}