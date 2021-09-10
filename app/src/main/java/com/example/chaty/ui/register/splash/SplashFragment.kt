package com.example.chaty.ui.register.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.chaty.R
import com.example.chaty.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_splash.*

private const val TAG = "SplashFragmentmostafa"
class SplashFragment : Fragment() {
    private val viewModel:SplashViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

       val userSignedIn=viewModel.checkCurrentUser()
        if(userSignedIn){
            launchMainActivity()
         }
        else{
            navController.navigate(R.id.action_splashFragment_to_signInFragment)
        }
    }

    private fun launchMainActivity() {
        val intent=Intent(activity,MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}