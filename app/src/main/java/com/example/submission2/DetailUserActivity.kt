package com.example.submission2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.databinding.ActivityDetailUserBinding
import com.example.submission2.model.UserGithub

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.page_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<UserGithub>(EXTRA_USER) as UserGithub
        binding.txtName.text = user.name
        binding.txtUsername.text = getString(R.string.username_template, user.username)
        binding.txtFollower.text = user.follower
        binding.txtFollowing.text = user.following
        binding.txtCompany.text = user.company
        binding.txtRepository.text = user.repository
        binding.txtLocation.text = user.location

        Glide.with(this)
            .load(user.photo)
            .apply(RequestOptions().override(55, 55))
            .into(binding.imgPhoto)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}