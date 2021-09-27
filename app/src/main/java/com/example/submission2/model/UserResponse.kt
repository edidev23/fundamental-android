package com.example.submission2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserResponse(
    val items: ArrayList<User>
)

@Parcelize
data class User(
    val login: String,
    val id: Int,
    val url: String,
    val followersUrl: String,
    val followingUrl:String,
    val avatarUrl: String,
    val htmlUrl: String,
    val score: String
): Parcelable
