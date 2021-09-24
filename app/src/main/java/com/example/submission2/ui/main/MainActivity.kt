package com.example.submission2.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.ui.detailUser.DetailUserActivity
import com.example.submission2.R
import com.example.submission2.adapter.UserGithubAdapter
import com.example.submission2.databinding.ActivityMainBinding
import com.example.submission2.model.User
import com.example.submission2.model.UserResponse
import com.example.submission2.ui.detailUser.DetailUserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val detailViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listUser.observe(this, { user -> setUserGithub(user)})
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })
        binding.rvUsers.setHasFixedSize(true)


    }

    private fun setUserGithub(user: UserResponse) {

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUser = UserGithubAdapter(user.items)
        binding.rvUsers.adapter = listUser

        listUser.setOnItemClickCallback(object : UserGithubAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        detailViewModel.findFollowerUsers(user.login)
        detailViewModel.findFollowingUsers(user.login)

        val detailUserIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
        detailUserIntent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(detailUserIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}