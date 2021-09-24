package com.example.submission2.ui.detailUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.api.ApiConfig
import com.example.submission2.model.User
import com.example.submission2.model.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    private val _detailUser = MutableLiveData<UserDetailResponse>()
    val detailUser: LiveData<UserDetailResponse> = _detailUser

    private val _listFollower = MutableLiveData<ArrayList<User>>()
    val listFollower: LiveData<ArrayList<User>> = _listFollower
    private val _listFollowing = MutableLiveData<ArrayList<User>>()
    val listFollowing: LiveData<ArrayList<User>> = _listFollowing
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DetailUserViewModel"
    }

    init {
        findFollowerUsers("edikode")
        findFollowingUsers("edikode")
    }

    fun getDetailUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure not success: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findFollowerUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersUser(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure not success: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findFollowingUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowingUser(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure not success: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}