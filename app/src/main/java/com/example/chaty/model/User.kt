package com.example.chaty.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val userID: String = "",
    val userName: String = "",
    val userImageUrl: String = "",
    val email: String = "",
    val token: String = "",
    val status: String = ""
) : Parcelable
