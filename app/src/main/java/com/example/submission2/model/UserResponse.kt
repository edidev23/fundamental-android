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
    val followers_url: String,
    val following_url:String,
    val avatar_url: String,
    val html_url: String,
    val score: String
): Parcelable
