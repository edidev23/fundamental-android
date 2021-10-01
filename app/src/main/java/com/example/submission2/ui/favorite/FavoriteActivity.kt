package com.example.submission2.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.database.Favorite
import com.example.submission2.databinding.ActivityFavoriteBinding
import com.example.submission2.helper.ViewModelFactory
import com.example.submission2.ui.detailFavorite.DetailFavoriteActivity
import com.example.submission2.ui.detailUser.DetailUserActivity.Companion.EXTRA_USER

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.favorite)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        favoriteViewModel.getAllFavorite().observe(this, { favList ->
            if (favList != null) {
                adapter.setListFavorite(favList)
            }
        })
        adapter = FavoriteAdapter()

        binding?.rvUsers?.layoutManager = LinearLayoutManager(this)
        binding?.rvUsers?.setHasFixedSize(true)
        binding?.rvUsers?.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Favorite) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: Favorite) {
        val favoriteUser = Intent(this@FavoriteActivity, DetailFavoriteActivity::class.java)
        favoriteUser.putExtra(EXTRA_USER, user)
        startActivity(favoriteUser)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}