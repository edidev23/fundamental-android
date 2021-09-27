package com.example.submission2.model

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: String,
)
