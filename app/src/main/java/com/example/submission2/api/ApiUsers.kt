package com.example.submission2.api

import com.example.submission2.model.User
import com.example.submission2.model.UserDetailResponse
import com.example.submission2.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiUsers {

    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}