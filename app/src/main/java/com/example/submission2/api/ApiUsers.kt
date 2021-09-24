package com.example.submission2.api

import com.example.submission2.model.User
import com.example.submission2.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiUsers {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

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