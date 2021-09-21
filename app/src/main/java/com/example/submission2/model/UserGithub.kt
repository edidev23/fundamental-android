package com.example.submission2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserGithub (
    val photo: String,
    val username: String,
    val name: String,
    val follower: String,
    val following: String,
    val company: String,
    val location: String,
    val repository: String
): Parcelable