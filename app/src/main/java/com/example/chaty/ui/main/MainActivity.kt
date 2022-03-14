package com.example.chaty.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.chaty.R
import com.example.chaty.firebase.MyFirebase
import com.example.chaty.ui.main.MainViewModel
import com.example.chaty.utils.Status
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}
        viewModel.updateUserStatus()
        uploadToken()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val hostFragment=supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController=hostFragment.navController
        bottom_navigation.setupWithNavController(navController)
    }

    private fun uploadToken() {
        viewModel.uploadToken()
        viewModel.getUploadState().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(TAG, "uploadToken: ${it.data}")
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}