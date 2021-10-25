package com.example.chaty.ui.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.chaty.R
import com.example.chaty.ui.register.RegisterActivity
import com.example.chaty.utils.Status
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.jar.Manifest


class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var startForeResult: ActivityResultLauncher<Intent>
    private lateinit var permissionResult: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)

        startForeResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    uploadImage(result.data)
                }
            }
        permissionResult=registerForActivityResult(ActivityResultContracts.RequestPermission()){result->
                if(result){
                    val intent = Intent().apply {
                        type = "image/*"
                        action = Intent.ACTION_GET_CONTENT
                    }
                    startForeResult.launch(intent)
                }else{
                        Toast.makeText(context,"Permission Denied",Toast.LENGTH_SHORT).show()
                }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserInfo()
        pickImage()
        setupLogoutButton()
        setupAdBanner()
    }

    private fun getUserInfo() {
        viewModel.getUserInfo().observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { user ->
                        profile_username.text = user.userName
                        if (user.userImageUrl != "default") {
                            context?.let { it1 ->
                                Glide.with(it1)
                                    .load(user.userImageUrl)
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                    .into(profile_user_image)
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun pickImage() {
        profile_user_image.setOnClickListener {
            permissionResult.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun uploadImage(data: Intent?) {

        val builder=AlertDialog.Builder(context)
        builder.setView(R.layout.progress_dialog)
        val dialog=builder.create()

        viewModel.uploadImageToFirebase(data?.data).observe(requireActivity(), {
            when (it.status) {
                Status.LOADING -> {
                    dialog.show()
                }
                Status.SUCCESS -> {
                    dialog.dismiss()
                    it.data?.let {  url->
                        context?.let { cntx ->
                            Glide.with(cntx)
                                .load(url)
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .into(profile_user_image)
                        }
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(activity,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun setupLogoutButton() {
        logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            goBackToRegistrationActivity()
        }
    }

    private fun setupAdBanner() {
        val adRequest=AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun goBackToRegistrationActivity() {
        val intent = Intent(activity, RegisterActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}