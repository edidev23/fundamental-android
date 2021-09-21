package com.example.submission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.adapter.UserGithubAdapter
import com.example.submission2.databinding.ActivityMainBinding
import com.example.submission2.model.UserGithub
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listUser = ArrayList<UserGithub>()

    private lateinit var dataUsername: Array<String>
    private lateinit var dataName: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataFollower: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataPhoto: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)
        listUser.addAll(getListUser())

        showRecyclerList()

    }

    private fun showRecyclerList() {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUser = UserGithubAdapter(listUser)
        binding.rvUsers.adapter = listUser

        listUser.setOnItemClickCallback(object : UserGithubAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserGithub) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: UserGithub) {
        val detailUserIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
        detailUserIntent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(detailUserIntent) }

    fun getListUser(): ArrayList<UserGithub> {
        dataUsername = resources.getStringArray(R.array.username)
        dataName = resources.getStringArray(R.array.name)
        dataCompany = resources.getStringArray(R.array.company)
        dataLocation = resources.getStringArray(R.array.location)
        dataFollower = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataRepository = resources.getStringArray(R.array.repository)
        dataPhoto = resources.getStringArray(R.array.data_photo)

        val listUsers = ArrayList<UserGithub>()
        for (position in dataName.indices) {
            val user = UserGithub(
                dataPhoto[position],
                dataUsername[position],
                dataName[position],
                dataFollower[position],
                dataFollowing[position],
                dataCompany[position],
                dataLocation[position],
                dataRepository[position]
            )
            listUsers.add(user)
        }
        return listUsers
    }
}