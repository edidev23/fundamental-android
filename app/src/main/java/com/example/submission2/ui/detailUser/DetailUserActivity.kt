package com.example.submission2.ui.detailUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.R
import com.example.submission2.databinding.ActivityDetailUserBinding
import com.example.submission2.model.User
import com.example.submission2.model.UserDetailResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.page_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        detailViewModel.getDetailUsers(user.login)

        detailViewModel.detailUser.observe(this, { res -> setDetailUser(res)})
        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this, user.login)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setDetailUser(user: UserDetailResponse) {

        binding.txtName.text = user.name
        binding.txtUsername.text = getString(R.string.username_template, user.login)
        binding.txtLocation.text = user.location
        binding.txtCompany.text = user.company
        binding.txtRepository.text = user.reposUrl

        Glide.with(this)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(55, 55))
            .into(binding.imgPhoto)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }
}
