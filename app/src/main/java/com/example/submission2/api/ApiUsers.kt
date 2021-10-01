package com.example.submission2.api

import com.example.submission2.model.User
import com.example.submission2.model.UserDetailResponse
import com.example.submission2.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiUsers {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}