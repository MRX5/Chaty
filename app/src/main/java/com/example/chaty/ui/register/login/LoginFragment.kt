package com.example.chaty.ui.register.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.chaty.R
import com.example.chaty.ui.main.MainActivity
import com.example.chaty.utils.Status
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.progress_dialog.*


class LoginFragment : Fragment() {

    private lateinit var navController: NavController
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        login_btn.setOnClickListener {
            val email = email_edittext.text.toString()
            val password = password_edittext.text.toString()

            val builder= AlertDialog.Builder(context)
            builder.setView(R.layout.progress_dialog)
            val dialog=builder.create()

            if (checkInformation(email, password)) {
                viewModel.login(email, password)
                viewModel.getLoginState().observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.SUCCESS -> {
                            dialog.dismiss()
                            launchMainActivity()
                        }
                        Status.LOADING -> {
                            dialog.show()
                        }
                        Status.ERROR -> {
                            dialog.dismiss()
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }
        signup_btn.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }


    private fun checkInformation(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            email_edittext.error = "Enter valid email"
            return false
        }
        if (password.isEmpty() || password.length < 8) {
            password_edittext.error = "Password length must be at least 8 char"
            return false
        }
        return true
    }

    private fun launchMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}