package com.example.chaty.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.chaty.R
import com.example.chaty.firebase.MyFirebase
import com.example.chaty.ui.add.AddViewModel
import com.example.chaty.ui.register.RegisterActivity
import com.example.chaty.utils.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserName().observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    username_profile.text = it.data?.userName
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            launchMainActivity()
        }
    }

    private fun launchMainActivity() {
        val intent = Intent(activity, RegisterActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}