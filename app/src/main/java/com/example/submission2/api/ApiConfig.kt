package com.example.submission2.api

import com.example.submission2.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private lateinit var client: OkHttpClient
        private var token = BuildConfig.KEY

        fun getApiService(): ApiUsers {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor =
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                client = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("Authorization", "token $token").build()
                        chain.proceed(request)
                    }
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else {
                client = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("Authorization", "token $token").build()
                        chain.proceed(request)
                    }
                    .build()
            }

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiUsers::class.java)
        }
    }
}