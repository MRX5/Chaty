package com.example.chaty.ui.register.sign_up

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.chaty.R
import com.example.chaty.ui.main.MainActivity
import com.example.chaty.utils.Status
import kotlinx.android.synthetic.main.fragment_sign_up.*

private const val TAG = "SignUpFragment"
class SignUpFragment : Fragment() {

    private val viewModel:SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signup_btn2.setOnClickListener {
            val userName = signup_username.text.toString()
            val email = signup_email.text.toString()
            val password = signup_password.text.toString()

            val builder= AlertDialog.Builder(context)
            builder.setView(R.layout.progress_dialog)
            val dialog=builder.create()

            if (checkInformation(userName, email, password)) {
                viewModel.createAccount(email, password)
                viewModel.createAccountState().observe(viewLifecycleOwner,{
                    when(it.status){
                        Status.SUCCESS->{
                            dialog.dismiss()
                            saveUserIntoDatabase(userName, email)
                        }
                        Status.LOADING->{
                            dialog.show()
                        }
                        Status.ERROR->{
                            dialog.dismiss()
                            Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }
    }

    private fun saveUserIntoDatabase(userName: String, email: String) {
        viewModel.saveIntoDatabase(userName, email)
        viewModel.saveIntoDatabaseState().observe(viewLifecycleOwner,{
            when(it.status){
                Status.SUCCESS->{
                    launchMainActivity()
                }
                Status.LOADING->{
                }
                Status.ERROR->{
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun launchMainActivity() {
        val intent=Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun checkInformation(userName: String, email: String, password: String): Boolean {
        if (userName.isEmpty()) {
            signup_username.error = "Enter valid username"
            return false
        }
        if (email.isEmpty()) {
            signup_email.error = "Enter valid email"
            return false
        }
        if (password.isEmpty() || password.length < 8) {
            signup_password.error = "Password length must be at least 8 char"
            return false
        }
        return true
    }


}